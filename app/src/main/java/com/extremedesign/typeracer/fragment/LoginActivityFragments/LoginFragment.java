package com.extremedesign.typeracer.fragment.LoginActivityFragments;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.extremedesign.typeracer.FirebaseRepo;
import com.extremedesign.typeracer.R;
import com.extremedesign.typeracer.Utils;
import com.extremedesign.typeracer.activity.MainActivity;
import com.extremedesign.typeracer.model.User;
import com.extremedesign.typeracer.model.UserInfo;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {
    private int canResetPassword;
    private EditText inputEmail, inputPassword;
    private Button btnSignIn, btnResetPassword;
    private LoginButton loginButton;
    private ProgressBar progressBar;
    private FirebaseAuth auth;
    private CallbackManager mCallbackManager;

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View itemView= inflater.inflate(R.layout.fragment_login, container, false);
        inputEmail = itemView.findViewById(R.id.login_email);
        inputPassword=itemView.findViewById(R.id.login_password);
        btnSignIn = itemView.findViewById(R.id.login_button);
        btnResetPassword=itemView.findViewById(R.id.btn_reset_password);
        loginButton=itemView.findViewById(R.id.log_in_facebook);
        progressBar=itemView.findViewById(R.id.login_progressBar);
        canResetPassword=0;
        auth = FirebaseRepo.getAuthINSTANCE();

        listenForFacebookLogin();
        listenForNormalLogin();
        listenForgottenPassword();
        return itemView;
    }
    private void userLogIn() {
        Intent i = new Intent(getContext(), MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }

    private void checkUserStatus(){
        progressBar.setVisibility(View.VISIBLE);
        FirebaseUser user=auth.getCurrentUser();
        if (user != null) {
            final String user_id=user.getUid();
            String name=user.getDisplayName();
            String email=user.getEmail();
            String photo=String.valueOf(user.getPhotoUrl());
            boolean isEmailValid=user.isEmailVerified();
            final User player=new User(user_id,new UserInfo(name,email,photo,isEmailValid));
            FirebaseRepo.setCurrentUser(player);
            final DatabaseReference db=FirebaseRepo.getDatabaseInstance();
            db.child("users").child(user_id).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.getValue()==null) {
                        db.child("users").child(user_id).setValue(player.getUserInfo());
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            userLogIn();
        }
        progressBar.setVisibility(View.GONE);

    }
    private void listenForgottenPassword(){
        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String emailAddress = inputEmail.getText().toString().trim();
                if(Utils.isEmailValid(emailAddress)){
                    inputEmail.setError("Please enter a valid email address");
                }
                else if(canResetPassword!=0) {
                    Snackbar.make(btnResetPassword,"We Already sent you an Email, try again in "+canResetPassword+" seconds",Snackbar.LENGTH_LONG).show();
                }
                else {
                    canResetPassword=30;
                    auth.sendPasswordResetEmail(emailAddress)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
//                                    if (task.isSuccessful()) {
//                                    }
                                    Snackbar.make(btnResetPassword,"Password reset email was sent to "+emailAddress,Snackbar.LENGTH_LONG).show();

                                }
                            });
                    new Thread() {
                        @Override
                        public void run() {
                            try {
                                while(canResetPassword!=0) {
                                    Thread.sleep(1000);
                                    canResetPassword--;
                                }
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }.start();
                }
            }
        });
    }
    private void listenForNormalLogin() {
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(getContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                //create user
                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                progressBar.setVisibility(View.GONE);
                                if (!task.isSuccessful()) {
                                    // there was an error
//                                    if (password.length() < 6) {
//                                        inputPassword.setError(getString(R.string.minimum_password));
//                                    }
//                                    else {
                                    Toast.makeText(getContext(), getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
//                                    }
                                } else {
//                                    Intent intent = new Intent(getContext(), MainActivity.class);
//                                    startActivity(intent);
//                                    finish();
                                    checkUserStatus();
                                }
                            }
                        });

            }
        });
    }
    private void listenForFacebookLogin() {
        mCallbackManager = CallbackManager.Factory.create();
        loginButton.setReadPermissions("email", "public_profile");
        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
//                Log.d(TAG, "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
//                Log.d(TAG, "facebook:onCancel");
                // ...
            }

            @Override
            public void onError(FacebookException error) {
//                Log.d(TAG, "facebook:onError", error);
                // ...
            }
        });
// ...

    }
    private void handleFacebookAccessToken(AccessToken token) {
//        Log.d(TAG, "handleFacebookAccessToken:" + token);
        progressBar.setVisibility(View.VISIBLE);
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        auth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
//                            Log.d(TAG, "signInWithCredential:success");
//                            FirebaseUser user = mAuth.getCurrentUser();
                            checkUserStatus();
//                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
//                            Log.w(TAG, "signInWithCredential:failure", task.getException());
//                            Toast.makeText(FacebookLoginActivity.this, "Authentication failed.",
//                                    Toast.LENGTH_SHORT).show();
//                            updateUI(null);
                        }

                        // ...
                    }
                });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result back to the Facebook SDK
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }


}


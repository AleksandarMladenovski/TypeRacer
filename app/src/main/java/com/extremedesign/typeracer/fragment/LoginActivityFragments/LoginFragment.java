package com.extremedesign.typeracer.fragment.LoginActivityFragments;


import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.extremedesign.typeracer.FirebaseRepo;
import com.extremedesign.typeracer.R;
import com.extremedesign.typeracer.Utils;
import com.extremedesign.typeracer.activity.MainActivity;
import com.extremedesign.typeracer.listener.DisplayCloseListener;
import com.extremedesign.typeracer.listener.IOnBackPressed;
import com.extremedesign.typeracer.listener.JobWorker;
import com.extremedesign.typeracer.model.User;
import com.extremedesign.typeracer.model.UserInfo;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import static com.extremedesign.typeracer.Utils.RC_SIGN_IN;
import static com.firebase.ui.auth.AuthUI.TAG;

public class LoginFragment extends Fragment {
    private EditText inputEmail, inputPassword;
    private Button btnSignIn, btnResetPassword;
    private ImageView btnPassVisibility;
    private SignInButton googleSignIn;
    private LoginButton loginButton;
    private ProgressBar progressBar;
    private FirebaseAuth auth;
    private CallbackManager mCallbackManager;
    private DisplayCloseListener listener;
    private TextView tv_incorrect_msg;
    public LoginFragment() {
        // Required empty public constructor
    }

    public LoginFragment(DisplayCloseListener listener) {
        // Required empty public constructor
        this.listener=listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View itemView= inflater.inflate(R.layout.fragment_login, container, false);


        //textView
        tv_incorrect_msg=itemView.findViewById(R.id.login_incorrect_msg);

        //editText
        inputEmail = itemView.findViewById(R.id.login_email);
        inputPassword=itemView.findViewById(R.id.login_password);

        //buttons
        btnSignIn = itemView.findViewById(R.id.login_button);
        btnResetPassword=itemView.findViewById(R.id.btn_reset_password);

        //imageView
        btnPassVisibility=itemView.findViewById(R.id.login_pass_visibility);

        //cardView

        //google sign in button
        googleSignIn=itemView.findViewById(R.id.log_in_google);
        googleSignIn.setSize(SignInButton.SIZE_STANDARD);

        //facebook sign in button
        loginButton=itemView.findViewById(R.id.log_in_facebook);

        progressBar=itemView.findViewById(R.id.login_progressBar);

        //firebase auth
        auth = FirebaseRepo.getAuthINSTANCE();


        listenForgottenPassword();
        listenForFacebookLogin();
        listenForNormalLogin();
        listenForGoogleLogin();
        listenForPassVisibility();
        return itemView;
    }

    private void listenForPassVisibility() {
        btnPassVisibility.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Drawable currentDrawable = btnPassVisibility.getDrawable();
               if(currentDrawable.getConstantState().equals(getResources().getDrawable(R.drawable.ic_visibility_off_black_24dp).getConstantState())){
                   btnPassVisibility.setImageDrawable(getResources().getDrawable(R.drawable.ic_visibility_black_24dp));
                   inputPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
               }
               else{
                   btnPassVisibility.setImageDrawable(getResources().getDrawable(R.drawable.ic_visibility_off_black_24dp));
                   inputPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
               }
            }
        });
    }


    private void listenForgottenPassword(){
        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.closeDisplay();
            }
        });
    }

    private void listenForNormalLogin() {
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();
                isSendSuccessful(true);

                if (TextUtils.isEmpty(email)) {
                    isSendSuccessful(false);
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    isSendSuccessful(false);
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(onNormalLoginCompleteListener());

            }
        });
    }

    private void listenForFacebookLogin() {
        mCallbackManager = CallbackManager.Factory.create();
        loginButton.setReadPermissions("email", "public_profile");
        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
            }

            @Override
            public void onError(FacebookException error) {
            }
        });
    }

    private void handleFacebookAccessToken(AccessToken token) {
        progressBar.setVisibility(View.VISIBLE);
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        auth.signInWithCredential(credential)
                .addOnCompleteListener(onLoginCompleteListener());
    }

    private void listenForGoogleLogin() {
        googleSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GoogleSignInClient mGoogleSignInClient= FirebaseRepo.getGoogleSignInClient(getContext());
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        progressBar.setVisibility(View.VISIBLE);
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(onLoginCompleteListener());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                // ...
            }
        }
        else {
            mCallbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }



    private void userLogIn() {
        Intent i = new Intent(getContext(), MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }
    private OnCompleteListener <AuthResult>  onNormalLoginCompleteListener() {
        return new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    isSendSuccessful(true);
                    FirebaseRepo.createCurrentUser();
                    if(task.getResult().getAdditionalUserInfo().isNewUser()){
                        FirebaseRepo.saveUserToFirebaseDatabase();
                    }
                    userLogIn();
                } else {
                    isSendSuccessful(false);
                }
                progressBar.setVisibility(View.GONE);
            }
        };
    }

    private OnCompleteListener<AuthResult>  onLoginCompleteListener(){
        return new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    FirebaseRepo.createCurrentUser();
                    if(task.getResult().getAdditionalUserInfo().isNewUser()){
                        FirebaseRepo.saveUserToFirebaseDatabase();
                    }
                    userLogIn();
                } else {

                }
                progressBar.setVisibility(View.GONE);
            }
        };
    }

    private void isSendSuccessful(boolean attempt){
        if(attempt){
            inputEmail.setBackground(getResources().getDrawable(R.drawable.et_custom_26a69a_color));
            inputPassword.setBackground(getResources().getDrawable(R.drawable.et_custom_26a69a_color));

            tv_incorrect_msg.setVisibility(View.GONE);
        }
        else{
            inputEmail.setBackground(getResources().getDrawable(R.drawable.et_custom_red_color));
            inputPassword.setBackground(getResources().getDrawable(R.drawable.et_custom_red_color));

            tv_incorrect_msg.setVisibility(View.VISIBLE);
        }
    }

}


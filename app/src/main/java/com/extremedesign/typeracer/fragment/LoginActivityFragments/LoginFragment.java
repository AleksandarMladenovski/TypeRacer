package com.extremedesign.typeracer.fragment.LoginActivityFragments;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.extremedesign.typeracer.DataRepository.RepositoryInstance;
import com.extremedesign.typeracer.FirebaseRepo;
import com.extremedesign.typeracer.R;
import com.extremedesign.typeracer.activity.MainActivity;
import com.extremedesign.typeracer.fragment.UI.EmailEditTextFragment;
import com.extremedesign.typeracer.fragment.UI.PasswordEditTextFragment;
import com.extremedesign.typeracer.listener.DisplayCloseListener;
import com.extremedesign.typeracer.listener.JobWorker;
import com.extremedesign.typeracer.listener.ProfileImageListener;
import com.extremedesign.typeracer.model.ProfileImage;
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
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.core.Repo;

import java.util.List;

import static com.extremedesign.typeracer.Utils.RC_SIGN_IN;

public class LoginFragment extends Fragment {
    private Button btnSignIn, btnResetPassword;
    private SignInButton googleSignIn;
    private LoginButton loginButton;
    private ProgressBar progressBar;
    private FirebaseAuth auth;
    private CallbackManager mCallbackManager;
    private DisplayCloseListener listener;
    private TextView tv_incorrect_msg;
    private EmailEditTextFragment emailEditTextFragment;
    private PasswordEditTextFragment passwordEditTextFragment;
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


        //email fragment
        emailEditTextFragment=new EmailEditTextFragment();
        getFragmentManager().beginTransaction().replace(R.id.login_email_frame_layout,emailEditTextFragment).commit();

        //password fragment
        passwordEditTextFragment=new PasswordEditTextFragment();
        getFragmentManager().beginTransaction().replace(R.id.login_password_frame_layout,passwordEditTextFragment).commit();

        //buttons
        btnSignIn = itemView.findViewById(R.id.login_button);
        btnResetPassword=itemView.findViewById(R.id.btn_reset_password);


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
        return itemView;
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
                isSendSuccessful(true);
                if (emailEditTextFragment.isEmailAddressValid() && passwordEditTextFragment.isPasswordValid()) {
//                    isSendSuccessful(true);
                    progressBar.setVisibility(View.VISIBLE);

                    auth.signInWithEmailAndPassword(emailEditTextFragment.getEmailAddress(), passwordEditTextFragment.getPassword())
                            .addOnCompleteListener(onNormalLoginCompleteListener());
                }

                else  {
                    isSendSuccessful(false);
                }
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
                    if(task.getResult().getAdditionalUserInfo().isNewUser()){
                        RepositoryInstance.getTypeRacerRepository(getContext()).getImageByName("baloon.jpg", new ProfileImageListener() {
                            @Override
                            public void getImages(List<ProfileImage> images) {
                                if(images.size()!=0){
                                    FirebaseRepo.createNewCurrentUser(images.get(0).getImageUrl());
                                    FirebaseRepo.saveUserToFirebaseDatabase();
                                }
                            }
                        });
                    }
                    else{
                        FirebaseRepo.createCurrentUser();
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
            emailEditTextFragment.isSendSuccessful(true);
            passwordEditTextFragment.isSendSuccessful(true);
            tv_incorrect_msg.setVisibility(View.GONE);
        }
        else{
            emailEditTextFragment.isSendSuccessful(false);
            passwordEditTextFragment.isSendSuccessful(false);
            tv_incorrect_msg.setVisibility(View.VISIBLE);
        }
    }

}


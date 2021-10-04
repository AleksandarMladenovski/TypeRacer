package com.extremedesign.typeracer.fragment.login_fragments;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.extremedesign.typeracer.activity.MainActivity;
import com.extremedesign.typeracer.data_repository.repository_typeracer.RepositoryViewModel;
import com.extremedesign.typeracer.FirebaseRepo;
import com.extremedesign.typeracer.R;
import com.extremedesign.typeracer.fragment.UI.EmailEditTextFragment;
import com.extremedesign.typeracer.fragment.UI.PasswordEditTextFragment;
import com.extremedesign.typeracer.listener.DisplayCloseListener;
import com.extremedesign.typeracer.model.User;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.Arrays;

import static com.extremedesign.typeracer.utils.Utils.RC_SIGN_IN;

public class LoginFragment extends Fragment {
    private Button btnSignIn, btnResetPassword;
    private ImageView option_facebook_login,option_google_login,option_yahoo_login,option_twitter_login;
    private ProgressBar progressBar;
    private RepositoryViewModel repositoryViewModel;
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

        repositoryViewModel=ViewModelProviders.of(this).get(RepositoryViewModel.class);
        repositoryViewModel.getCurrentUser().observe(getViewLifecycleOwner(), new Observer<User>() {
            @Override
            public void onChanged(User user) {
                if(user!=null){
                    repositoryViewModel.getCurrentUser().removeObservers(LoginFragment.this);
                    progressBar.setVisibility(View.GONE);
                    userLogIn();
                }
            }

        });
        //textView
        tv_incorrect_msg=itemView.findViewById(R.id.login_incorrect_msg);

        //mcallbackmanager
        mCallbackManager = CallbackManager.Factory.create();

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
        option_google_login=itemView.findViewById(R.id.option_log_in_google);

        //yahoo sign in button
        option_yahoo_login=itemView.findViewById(R.id.option_log_in_yahoo);

        //twitter sing in button
        option_twitter_login=itemView.findViewById(R.id.option_log_in_twitter);

        progressBar=itemView.findViewById(R.id.login_progressBar);

        option_facebook_login=itemView.findViewById(R.id.option_log_in_facebook);

        listenForgottenPassword();
        listenForFacebookLogin();
        listenForNormalLogin();
        listenForGoogleLogin();
        listenForYahooLogin();
        listenForTwitterLogin();
        return itemView;
    }

    private void listenForTwitterLogin() {
        option_twitter_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                repositoryViewModel.logInWithProvider("twitter.com",onLoginCompleteListener(),getActivity());

            }
        });


    }

    private void listenForYahooLogin() {
        option_yahoo_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                repositoryViewModel.logInWithProvider("yahoo.com",onLoginCompleteListener(),getActivity());
            }
        });
    }

    private void userLogIn() {
        Intent i = new Intent(getContext(), MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
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

                    //TODO
                    repositoryViewModel.getFirebaseRepo().getAuthINSTANCE().signInWithEmailAndPassword(emailEditTextFragment.getEmailAddress(), passwordEditTextFragment.getPassword())
                            .addOnCompleteListener(onNormalLoginCompleteListener());
                }

                else  {
                    isSendSuccessful(false);
                }
            }
        });
    }

    private void listenForGoogleLogin() {
        option_google_login.setOnClickListener(new View.OnClickListener() {
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
        repositoryViewModel.getFirebaseRepo().getAuthINSTANCE().signInWithCredential(credential)
                .addOnCompleteListener(onLoginCompleteListener());
    }


    private void facebookLogin(){
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList( "email", "public_profile"));
        LoginManager.getInstance().registerCallback(mCallbackManager,
                new FacebookCallback<LoginResult>()
                {
                    @Override
                    public void onSuccess(LoginResult loginResult)
                    {
                        handleFacebookAccessToken(loginResult.getAccessToken());
                    }

                    @Override
                    public void onCancel()
                    {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception)
                    {
                        // App code
                    }
                });
    }
    private void listenForFacebookLogin() {
        option_facebook_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                facebookLogin();
            }
        });
    }

    private void handleFacebookAccessToken(AccessToken token) {
        progressBar.setVisibility(View.VISIBLE);
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        repositoryViewModel.getFirebaseRepo().getAuthINSTANCE().signInWithCredential(credential)
                .addOnCompleteListener(onLoginCompleteListener());
    }

    private OnCompleteListener <AuthResult>  onNormalLoginCompleteListener() {
        return new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    isSendSuccessful(true);
                    repositoryViewModel.getFirebaseRepo().createCurrentUser(false);

//                    userLogIn();
                } else {
                    isSendSuccessful(false);
                    progressBar.setVisibility(View.GONE);
                }

            }
        };
    }

    private OnCompleteListener<AuthResult>  onLoginCompleteListener(){
        return new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    if(task.getResult().getAdditionalUserInfo().isNewUser()){
                        repositoryViewModel.getFirebaseRepo().createCurrentUser(false);
                                    //FirebaseRepo.saveUserToFirebaseDatabase();
                    }
                    else{
                        repositoryViewModel.getFirebaseRepo().createCurrentUser(true);

                    }
//                    userLogIn();
                } else {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getContext(),"Login is not successful",Toast.LENGTH_LONG);
                }

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

}


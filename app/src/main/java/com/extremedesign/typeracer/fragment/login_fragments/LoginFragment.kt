package com.extremedesign.typeracer.fragment.login_fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment
import com.extremedesign.typeracer.FirebaseRepo
import com.extremedesign.typeracer.R
import com.extremedesign.typeracer.activity.MainActivity
import com.extremedesign.typeracer.data_repository.repository_typeracer.RepositoryViewModel
import com.extremedesign.typeracer.fragment.UI.EmailEditTextFragment
import com.extremedesign.typeracer.fragment.UI.PasswordEditTextFragment
import com.extremedesign.typeracer.listener.DisplayCloseListener
import com.extremedesign.typeracer.utils.Utils
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.GoogleAuthProvider
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*

class LoginFragment : Fragment() {
    private var btnSignIn: Button? = null
    private var btnResetPassword: Button? = null
    private var option_facebook_login: ImageView? = null
    private var option_google_login: ImageView? = null
    private var option_yahoo_login: ImageView? = null
    private var option_twitter_login: ImageView? = null
    private var progressBar: ProgressBar? = null
    private val repositoryViewModel: RepositoryViewModel by viewModel()
    private var mCallbackManager: CallbackManager? = null
    private var listener: DisplayCloseListener? = null
    private var tv_incorrect_msg: TextView? = null
    private var emailEditTextFragment: EmailEditTextFragment? = null
    private var passwordEditTextFragment: PasswordEditTextFragment? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val itemView = inflater.inflate(R.layout.fragment_login, container, false)
        repositoryViewModel.currentUser.observe(viewLifecycleOwner,
            { user ->
                if (user != null) {
                    repositoryViewModel!!.currentUser.removeObservers(this@LoginFragment)
                    progressBar!!.visibility = View.GONE
                    userLogIn()
                }
            })
        //textView
        tv_incorrect_msg = itemView.findViewById(R.id.login_incorrect_msg)

        //mcallbackmanager
        mCallbackManager = CallbackManager.Factory.create()

        //email fragment
        emailEditTextFragment = EmailEditTextFragment()
        childFragmentManager.beginTransaction().replace(
            R.id.login_email_frame_layout,
            emailEditTextFragment!!
        ).commit()

        //password fragment
        passwordEditTextFragment = PasswordEditTextFragment()
        requireFragmentManager().beginTransaction().replace(
            R.id.login_password_frame_layout,
            passwordEditTextFragment!!
        ).commit()

        //buttons
        btnSignIn = itemView.findViewById(R.id.login_button)
        btnResetPassword = itemView.findViewById(R.id.btn_reset_password)


        //cardView

        //google sign in button
        option_google_login = itemView.findViewById(R.id.option_log_in_google)

        //yahoo sign in button
        option_yahoo_login = itemView.findViewById(R.id.option_log_in_yahoo)

        //twitter sing in button
        option_twitter_login = itemView.findViewById(R.id.option_log_in_twitter)
        progressBar = itemView.findViewById(R.id.login_progressBar)
        option_facebook_login = itemView.findViewById(R.id.option_log_in_facebook)
        listenForgottenPassword()
        listenForFacebookLogin()
        listenForNormalLogin()
        listenForGoogleLogin()
        listenForYahooLogin()
        listenForTwitterLogin()
        return itemView
    }

    private fun listenForTwitterLogin() {
        option_twitter_login!!.setOnClickListener {
            progressBar!!.visibility = View.VISIBLE
            repositoryViewModel!!.logInWithProvider(
                "twitter.com", onLoginCompleteListener(),
                activity
            )
        }
    }

    private fun listenForYahooLogin() {
        option_yahoo_login!!.setOnClickListener {
            progressBar!!.visibility = View.VISIBLE
            repositoryViewModel!!.logInWithProvider(
                "yahoo.com", onLoginCompleteListener(),
                activity
            )
        }
    }

    private fun userLogIn() {
        val i = Intent(context, MainActivity::class.java)
        i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(i)
    }

    private fun listenForgottenPassword() {
        btnResetPassword!!.setOnClickListener { listener!!.closeDisplay() }
    }

    private fun listenForNormalLogin() {
        btnSignIn!!.setOnClickListener {
            isSendSuccessful(true)
            if (emailEditTextFragment!!.isEmailAddressValid && passwordEditTextFragment!!.isPasswordValid) {
                //                    isSendSuccessful(true);
                progressBar!!.visibility = View.VISIBLE

                //TODO
                repositoryViewModel!!.firebaseRepo.authINSTANCE.signInWithEmailAndPassword(
                    emailEditTextFragment!!.emailAddress, passwordEditTextFragment!!.password
                )
                    .addOnCompleteListener(onNormalLoginCompleteListener())
            } else {
                isSendSuccessful(false)
            }
        }
    }

    private fun listenForGoogleLogin() {
        option_google_login!!.setOnClickListener {
            val mGoogleSignInClient = FirebaseRepo.getGoogleSignInClient(
                context
            )
            val signInIntent = mGoogleSignInClient.signInIntent
            startActivityForResult(
                signInIntent,
                Utils.RC_SIGN_IN
            )
        }
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        progressBar!!.visibility = View.VISIBLE
        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        repositoryViewModel!!.firebaseRepo.authINSTANCE.signInWithCredential(credential)
            .addOnCompleteListener(onLoginCompleteListener())
    }

    private fun facebookLogin() {
        LoginManager.getInstance()
            .logInWithReadPermissions(this, Arrays.asList("email", "public_profile"))
        LoginManager.getInstance().registerCallback(mCallbackManager,
            object : FacebookCallback<LoginResult> {
                override fun onSuccess(loginResult: LoginResult) {
                    handleFacebookAccessToken(loginResult.accessToken)
                }

                override fun onCancel() {
                    // App code
                }

                override fun onError(exception: FacebookException) {
                    // App code
                }
            })
    }

    private fun listenForFacebookLogin() {
        option_facebook_login!!.setOnClickListener { facebookLogin() }
    }

    private fun handleFacebookAccessToken(token: AccessToken) {
        progressBar!!.visibility = View.VISIBLE
        val credential = FacebookAuthProvider.getCredential(token.token)
        repositoryViewModel!!.firebaseRepo.authINSTANCE.signInWithCredential(credential)
            .addOnCompleteListener(onLoginCompleteListener())
    }

    private fun onNormalLoginCompleteListener(): OnCompleteListener<AuthResult> {
        return OnCompleteListener { task ->
            if (task.isSuccessful) {
                isSendSuccessful(true)
                repositoryViewModel!!.firebaseRepo.createCurrentUser(false)

                //                    userLogIn();
            } else {
                isSendSuccessful(false)
                progressBar!!.visibility = View.GONE
            }
        }
    }

    private fun onLoginCompleteListener(): OnCompleteListener<AuthResult> {
        return OnCompleteListener { task ->
            if (task.isSuccessful) {
                if (task.result.additionalUserInfo!!.isNewUser) {
                    repositoryViewModel!!.firebaseRepo.createCurrentUser(false)
                    //FirebaseRepo.saveUserToFirebaseDatabase();
                } else {
                    repositoryViewModel!!.firebaseRepo.createCurrentUser(true)
                }
                //                    userLogIn();
            } else {
                progressBar!!.visibility = View.GONE
                Toast.makeText(context, "Login is not successful", Toast.LENGTH_LONG)
            }
        }
    }

    private fun isSendSuccessful(attempt: Boolean) {
        if (attempt) {
            emailEditTextFragment!!.isSendSuccessful(true)
            passwordEditTextFragment!!.isSendSuccessful(true)
            tv_incorrect_msg!!.visibility = View.GONE
        } else {
            emailEditTextFragment!!.isSendSuccessful(false)
            passwordEditTextFragment!!.isSendSuccessful(false)
            tv_incorrect_msg!!.visibility = View.VISIBLE
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Utils.RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                // ...
            }
        } else {
            mCallbackManager!!.onActivityResult(requestCode, resultCode, data)
        }
    }
}
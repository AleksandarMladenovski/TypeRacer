package com.example.typeracer.data_repository

import android.app.Activity
import android.content.Context
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.example.typeracer.utils.Utils.Companion.RC_SIGN_IN
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import android.R

import com.google.android.gms.auth.api.identity.GetSignInIntentRequest




class Firebase {
    companion object {
        private val firebaseAuth = FirebaseAuth.getInstance()
        fun getFirebaseAuth() = firebaseAuth

//        fun startGoogleSignIn(activity: Activity,fragment : Fragment) {
//            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestIdToken(fragment.getString(R.string.default_web_client_id))
//                .requestEmail()
//                .build()
//
//            val request = GetSignInIntentRequest.builder()
//                .setServerClientId(activity.getString(R.string.server_client_id))
//                .build()
//            val googleSignInClient = GoogleSignIn.getClient(activity, gso)
//            val intent = googleSignInClient.signInIntent
//            activity.startActivityForResult(intent, RC_SIGN_IN)
//        }
    }
}
package com.example.typeracer.data_repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage


class FirebaseNetwork {
    companion object {
        private val firebaseAuth = FirebaseAuth.getInstance()
        private val storageInstance = Firebase.storage
        fun getFirebaseAuth() = firebaseAuth
        fun getFirebaseStorage() = storageInstance
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
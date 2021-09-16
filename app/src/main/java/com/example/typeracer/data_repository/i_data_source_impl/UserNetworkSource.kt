package com.example.typeracer.data_repository.i_data_source_impl

import android.app.Activity
import com.example.typeracer.data_repository.FirebaseNetwork
import com.example.typeracer.data_repository.callback.BooleanCallback
import com.example.typeracer.data_repository.callback.UserCallback
import com.example.typeracer.data_repository.i_data_source.UserDataSource
import com.example.typeracer.utils.addons.toUser
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.OAuthProvider


class UserNetworkSource : UserDataSource {
    override fun loginWithProvider(providerName: String, activity: Activity, callback: UserCallback) {
        val provider = OAuthProvider.newBuilder(providerName)
        provider.addCustomParameter("prompt", "login")
        val firebaseAuth = FirebaseNetwork.getFirebaseAuth()
        val pendingResultTask: Task<AuthResult>? = firebaseAuth.pendingAuthResult
        if (pendingResultTask != null) {
            // There's something already here! Finish the sign-in for your user.
            pendingResultTask.addOnSuccessListener(
                OnSuccessListener {
                    val firebaseUser = FirebaseNetwork.getFirebaseAuth().currentUser
                    if (firebaseUser != null) {
                        callback.onSuccess(firebaseUser.toUser())
                    } else {
                        callback.onFailure("Something went wrong!")
                    }
                })
                .addOnFailureListener {
                    callback.onFailure("Could not create User")
                }
        } else {
            firebaseAuth
                .startActivityForSignInWithProvider( activity, provider.build())
                .addOnSuccessListener {
                    val firebaseUser = FirebaseNetwork.getFirebaseAuth().currentUser
                    if (firebaseUser != null) {
                        callback.onSuccess(firebaseUser.toUser())
                    } else {
                        callback.onFailure("Something went wrong!")
                    }
                }
                .addOnFailureListener {
                    callback.onFailure("Could not create User")
                }
        }
    }

    override fun createUserByBasic(
        email: String,
        name: String,
        password: String,
        callback: UserCallback
    ) {
        FirebaseNetwork.getFirebaseAuth().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val firebaseUser = FirebaseNetwork.getFirebaseAuth().currentUser
                    if (firebaseUser != null) {
                        callback.onSuccess(firebaseUser.toUser())
                    } else {
                        callback.onFailure("Something went wrong!")
                    }
                } else {
                    callback.onFailure("Could not create User")
                }
            }
    }

    override fun loginByBasic(email: String, password: String, callback: UserCallback) {
        FirebaseNetwork.getFirebaseAuth().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val firebaseUser = FirebaseNetwork.getFirebaseAuth().currentUser
                    if (firebaseUser != null) {
                        callback.onSuccess(firebaseUser.toUser())
                    } else {
                        callback.onFailure("Incorrect Credentials")
                    }
                } else {
                    callback.onFailure("Something went wrong")
                }
            }
    }

    override fun resetUserPassword(email: String, callback: BooleanCallback) {
        FirebaseNetwork.getFirebaseAuth().sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    callback.onSuccess(true)
                } else {
                    callback.onFailure("Something went wrong")
                }
            }
    }

    override fun logOutUser() {
        FirebaseNetwork.getFirebaseAuth().signOut()
    }


}
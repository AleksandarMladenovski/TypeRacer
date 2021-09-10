package com.example.typeracer.data_repository.i_data_source_impl

import android.app.Activity
import com.example.typeracer.data_repository.Firebase
import com.example.typeracer.data_repository.callback.UserCallback
import com.example.typeracer.data_repository.i_data_source.UserDataSource
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult

class UserNetworkSource : UserDataSource {
    override fun loginWithProvider(provider: String, activity: Activity, callback: UserCallback) {

    }

    override fun createUserByBasic(
        email: String,
        name: String,
        password: String,
        callback: UserCallback
    ) {
        Firebase.getFirebaseAuth().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if(task.isSuccessful){
//                    callback.onSuccess()
                    Firebase.getFirebaseAuth().currentUser
                }
                else{
                    callback.onFailure("Could not create User")
                }
            }
    }
}
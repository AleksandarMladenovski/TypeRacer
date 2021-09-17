package com.example.typeracer.data_repository.i_data_source_impl

import android.app.Activity
import com.example.typeracer.data_repository.FirebaseNetwork
import com.example.typeracer.data_repository.callback.BooleanCallback
import com.example.typeracer.data_repository.callback.UserCallback
import com.example.typeracer.data_repository.i_data_source.UserDataSource
import com.example.typeracer.data_repository.model.User
import com.example.typeracer.utils.addons.toUser
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.OAuthProvider


class UserNetworkSource : UserDataSource {
    override fun loginWithProvider(
        providerName: String,
        activity: Activity,
        callback: UserCallback
    ) {
        val provider = OAuthProvider.newBuilder(providerName)
        provider.addCustomParameter("prompt", "login")
        val firebaseAuth = FirebaseNetwork.getFirebaseAuth()
        val pendingResultTask: Task<AuthResult>? = firebaseAuth.pendingAuthResult
        if (pendingResultTask != null) {
            // There's something already here! Finish the sign-in for your user.
            pendingResultTask.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    createUser(null, task.result, callback)
                } else {
                    callback.onFailure("Could not create User")
                }
            }
        } else {
            firebaseAuth
                .startActivityForSignInWithProvider(activity, provider.build())
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        createUser(null, task.result, callback)
                    } else {
                        callback.onFailure("Could not create User")
                    }
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
                    createUser(name, task.result, callback)
                } else {
                    callback.onFailure("Could not create User")
                }
            }
    }

    override fun loginByBasic(email: String, password: String, callback: UserCallback) {
        FirebaseNetwork.getFirebaseAuth().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    createUser(null, task.result, callback)
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

    private fun createUser(newName: String?, result: AuthResult, userCallback: UserCallback) {
        val basicUser = result.user?.toUser(newName)
        if (result.additionalUserInfo!!.isNewUser) {
            updateUserData(basicUser!!.uid,basicUser.convertToFirebaseDatabaseUser(), object : BooleanCallback{
                override fun onSuccess(value: Boolean) {
                    userCallback.onSuccess(basicUser)
                }

                override fun onFailure(error: String) {
                    userCallback.onFailure(error)
                }

            })
        } else {
            FirebaseNetwork.getFirebaseDatabase().reference.child("users").child(basicUser!!.uid)
                .get().addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val carImage = task.result.child("car").value as String
                        val photoImage = task.result.child("photo").value as String
                        val name = task.result.child("name").value as String
                        basicUser.name = name
                        basicUser.carName = carImage
                        basicUser.photoName = photoImage
                        userCallback.onSuccess(basicUser)
                    } else {
                        userCallback.onFailure("Error getting user")
                    }
                }
        }
    }

    fun getCurrentUser(userCallback: UserCallback) {
        val basicUser = FirebaseNetwork.getFirebaseAuth().currentUser?.toUser(null)
        FirebaseNetwork.getFirebaseDatabase().reference.child("users").child(basicUser!!.uid)
            .get().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val carImage = task.result.child("car").value as String
                    val photoImage = task.result.child("photo").value as String
                    val name = task.result.child("name").value as String
                    basicUser.name = name
                    basicUser.carName = carImage
                    basicUser.photoName = photoImage
                    userCallback.onSuccess(basicUser)
                } else {
                    userCallback.onFailure("Error getting user")
                }
            }
    }

    override fun logOutUser() {
        FirebaseNetwork.getFirebaseAuth().signOut()
    }

    override fun updateUserData(
        id: String,
        user: User.FirebaseDatabaseUser,
        callback: BooleanCallback
    ) {
        FirebaseNetwork.getFirebaseDatabase().reference.child("users").child(id).setValue(user)
            .addOnCompleteListener { task ->
                if(task.isSuccessful){
                    callback.onSuccess(true)
                }else{
                    callback.onFailure("Error")
                }
            }
    }

}
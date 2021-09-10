package com.example.typeracer.data_repository.model

import com.google.firebase.auth.FirebaseUser

data class User(
        var uid: String,
        val name: String,
        val email: String,
        var photoName: String,
        val emailVerified: Boolean = false
){
        fun mapFirebaseUserToUser(firebaseUser: FirebaseUser){

        }
}
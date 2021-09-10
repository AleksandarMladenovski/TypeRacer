package com.example.typeracer.data_repository

import com.google.firebase.auth.FirebaseAuth

class Firebase {
    companion object{
        private val firebaseAuth= FirebaseAuth.getInstance()

        fun getFirebaseAuth() = firebaseAuth
    }
}
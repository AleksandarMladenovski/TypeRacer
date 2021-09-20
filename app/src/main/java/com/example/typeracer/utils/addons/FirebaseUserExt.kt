package com.example.typeracer.utils.addons

import com.example.typeracer.data_repository.model.User
import com.google.firebase.auth.FirebaseUser

fun FirebaseUser.toUser(name: String?,images: Pair<String,String>?) = User(
    this.uid,
    name ?: "Player",
    this.email ?: "player.email@example.com",
    images?.first?: "https://firebasestorage.googleapis.com/v0/b/typeracer-adf54.appspot.com/o/images%2Fprofile%2Fdefault10.png?alt=media&token=29ac7cb7-ca7f-44ec-be02-83d20ce27413",
    images?.second?: "https://firebasestorage.googleapis.com/v0/b/typeracer-adf54.appspot.com/o/images%2Fprofile%2Fdefault10.png?alt=media&token=29ac7cb7-ca7f-44ec-be02-83d20ce27413",
    this.isEmailVerified
)

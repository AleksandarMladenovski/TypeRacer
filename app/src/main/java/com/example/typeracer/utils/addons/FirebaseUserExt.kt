package com.example.typeracer.utils.addons

import com.example.typeracer.data_repository.model.User
import com.google.firebase.auth.FirebaseUser

fun FirebaseUser.toUser() = User(
    this.uid,
    this.displayName ?: "Player",
    this.email ?: "player.email@example.com",
    "default1",
    "default1",
    this.isEmailVerified
)

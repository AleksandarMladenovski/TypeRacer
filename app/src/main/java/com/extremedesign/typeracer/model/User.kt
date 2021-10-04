package com.extremedesign.typeracer.model

data class User(
        var uid: String,
        val name: String,
        val email: String,
        var photoName: String,
        val emailVerified: Boolean = false
)
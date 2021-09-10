package com.example.typeracer.data_repository.model

class UserInfo(
        var name: String,
        val email: String,
        val photoName: String,
        val emailVerified: Boolean = false
) {
    override fun toString(): String {
        return "$name $email $photoName $emailVerified"
    }
}

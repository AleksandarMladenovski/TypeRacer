package com.example.typeracer.data_repository.model

data class User(
    val uid: String,
    var name: String,
    val email: String,
    var photoName: String,
    var carName: String,
    var emailVerified: Boolean = false
) {
    fun convertToFirebaseDatabaseUser(): FirebaseDatabaseUser {
        return FirebaseDatabaseUser(name,photoName,carName,"")
    }

    data class FirebaseDatabaseUser(
        val name: String,
        val photo: String,
        val car: String,
        val roomId: String
    )
}
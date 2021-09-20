package com.example.typeracer.data_repository.model

data class GamePlayer(
    val id: String,
    val name: String,
    val photo: String,
    val car: String,
    var wordCount: Int
)
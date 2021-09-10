package com.example.typeracer.data_repository.response

data class ResponseData<E>(
    val data : E,
    val errorMessage: String,
    val progress: String,
    val status: ResponseStatus
)

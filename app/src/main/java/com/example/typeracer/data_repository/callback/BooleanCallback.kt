package com.example.typeracer.data_repository.callback

interface BooleanCallback {
    fun onSuccess(value: Boolean)
    fun onFailure(error: String)
}
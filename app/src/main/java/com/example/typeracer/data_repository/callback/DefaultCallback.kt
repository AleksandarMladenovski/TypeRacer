package com.example.typeracer.data_repository.callback

interface DefaultCallback<E> {
    fun onSuccess(data: E)
    fun onFailure(error: String)
}
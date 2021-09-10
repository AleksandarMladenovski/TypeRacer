package com.example.typeracer.data_repository.callback

import com.example.typeracer.data_repository.model.User

interface UserCallback {
    fun onSuccess(user: User)
    fun onFailure(error: String)
}
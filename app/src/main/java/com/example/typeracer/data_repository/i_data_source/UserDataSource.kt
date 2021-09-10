package com.example.typeracer.data_repository.i_data_source

import android.app.Activity
import com.example.typeracer.data_repository.callback.UserCallback

interface UserDataSource {
    fun loginWithProvider(provider: String, activity: Activity, callback: UserCallback)
    fun createUserByBasic(email: String, name: String, password: String, callback: UserCallback)
}
package com.example.typeracer.data_repository.i_data_source

import android.app.Activity
import com.example.typeracer.data_repository.callback.BooleanCallback
import com.example.typeracer.data_repository.callback.UserCallback
import com.example.typeracer.data_repository.model.User

interface UserDataSource {
    fun loginWithProvider(providerName: String, activity: Activity, callback: UserCallback)
    fun createUserByBasic(email: String, name: String, password: String, callback: UserCallback)
    fun loginByBasic(email: String, password: String, callback: UserCallback)
    fun resetUserPassword(email: String, callback: BooleanCallback)
    fun updateUserData(id: String, user: User.FirebaseDatabaseUser, callback: BooleanCallback)
    fun logOutUser()
}
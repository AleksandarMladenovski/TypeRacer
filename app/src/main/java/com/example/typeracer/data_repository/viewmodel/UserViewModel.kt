package com.example.typeracer.data_repository.viewmodel

import android.app.Activity
import androidx.lifecycle.ViewModel
import com.example.typeracer.data_repository.model.User
import com.example.typeracer.data_repository.repository.StorageRepository
import com.example.typeracer.data_repository.repository.UserRepository

class UserViewModel(
    private val repository: UserRepository,
    private val storageRepository: StorageRepository
) : ViewModel() {

    fun loginWithProvider(provider: String, activity: Activity) =
        repository.loginWithProvider(provider, storageRepository.getBasicImages(), activity)

    fun createUserByBasic(email: String, name: String, password: String) =
        repository.createUserByBasic(email, name, password,storageRepository.getBasicImages())

    fun loginUserByBasic(email: String, password: String) =
        repository.loginUserByBasic(email, password)

    fun getCurrentUser() = repository.getUser()

    fun resetUserPassword(email: String) = repository.resetUserPassword(email)

    fun logOutUser() = repository.logOutUser()

    fun updateUserName(id: String, user: User.FirebaseDatabaseUser) =
        repository.updateUserName(id, user)

}
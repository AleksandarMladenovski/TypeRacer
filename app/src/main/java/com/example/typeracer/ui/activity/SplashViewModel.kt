package com.example.typeracer.ui.activity

import androidx.lifecycle.ViewModel
import com.example.typeracer.data_repository.repository.StorageRepository
import com.example.typeracer.data_repository.repository.UserRepository

class SplashViewModel(
    private val userRepository: UserRepository,
    private val storageRepository: StorageRepository
) : ViewModel() {
    fun isUserLoggedIn() = userRepository.isUserLoggedIn()
    fun getAllImages() = storageRepository.getAllImages()
}
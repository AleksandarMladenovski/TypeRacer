package com.example.typeracer.ui.settings.viewmodel

import androidx.lifecycle.ViewModel
import com.example.typeracer.data_repository.repository.UserRepository

class SettingsViewModel(private val userRepository: UserRepository) : ViewModel() {
    fun userLogOut() = userRepository.logOutUser()
    fun getCurrentUser() = userRepository.getUser()

}
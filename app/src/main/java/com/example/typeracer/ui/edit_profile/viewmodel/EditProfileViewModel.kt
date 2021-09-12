package com.example.typeracer.ui.edit_profile.viewmodel

import androidx.lifecycle.ViewModel
import com.example.typeracer.data_repository.repository.UserRepository

class EditProfileViewModel(private val userRepository: UserRepository) : ViewModel() {
    fun getCurrentUser() = userRepository.getUser()
}
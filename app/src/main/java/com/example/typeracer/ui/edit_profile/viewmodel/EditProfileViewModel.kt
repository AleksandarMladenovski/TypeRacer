package com.example.typeracer.ui.edit_profile.viewmodel

import androidx.lifecycle.ViewModel
import com.example.typeracer.data_repository.repository.StorageRepository
import com.example.typeracer.data_repository.repository.UserRepository

class EditProfileViewModel(private val userRepository: UserRepository, private val storageRepository: StorageRepository) : ViewModel() {
    fun getCurrentUser() = userRepository.getUser()

    fun getProfileImages() = storageRepository.images
}
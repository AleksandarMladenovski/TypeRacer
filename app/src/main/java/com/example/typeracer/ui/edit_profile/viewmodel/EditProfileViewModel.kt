package com.example.typeracer.ui.edit_profile.viewmodel

import androidx.lifecycle.ViewModel
import com.example.typeracer.data_repository.model.User
import com.example.typeracer.data_repository.repository.StorageRepository
import com.example.typeracer.data_repository.repository.UserRepository

class EditProfileViewModel(private val userRepository: UserRepository, private val storageRepository: StorageRepository) : ViewModel() {
    fun getCurrentUser() = userRepository.getUser()

    fun updateUser(id:String,user: User.FirebaseDatabaseUser) = userRepository.updateUserName(id,user)

    fun getProfileImages() = storageRepository.images
}
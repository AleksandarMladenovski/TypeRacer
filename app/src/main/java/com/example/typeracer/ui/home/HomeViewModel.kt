package com.example.typeracer.ui.home

import androidx.lifecycle.ViewModel
import com.example.typeracer.data_repository.repository.UserRepository

class HomeViewModel(private val userRepository: UserRepository) : ViewModel() {
    fun getCurrentUser() = userRepository.getUser()

}
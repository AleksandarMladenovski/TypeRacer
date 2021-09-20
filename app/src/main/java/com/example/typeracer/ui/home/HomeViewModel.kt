package com.example.typeracer.ui.home

import androidx.lifecycle.ViewModel
import com.example.typeracer.data_repository.repository.GameRepository
import com.example.typeracer.data_repository.repository.UserRepository

class HomeViewModel(private val repository: UserRepository, private val gameRepository: GameRepository) : ViewModel() {
    fun getCurrentUser() = repository.getUser()
    fun joinQueue() = gameRepository.joinQueue()
    fun removeFromQueue() = gameRepository.removeFromQueue()
}
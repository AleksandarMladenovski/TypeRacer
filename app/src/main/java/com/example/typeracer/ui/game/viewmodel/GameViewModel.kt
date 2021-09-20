package com.example.typeracer.ui.game.viewmodel

import androidx.lifecycle.ViewModel
import com.example.typeracer.data_repository.repository.GameRepository

class GameViewModel(private val gameRepository: GameRepository) : ViewModel() {
    fun getAllPlayers() = gameRepository.getAllPlayers()
    fun getGameText() = gameRepository.getTypeSentence()
    fun uploadPlayerChanges(id: String, wordCount: Int) = gameRepository.uploadPlayerChanges(id,wordCount)
}
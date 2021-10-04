package com.extremedesign.typeracer.listener

import com.extremedesign.typeracer.model.FriendlyPlayer

interface PlayersReadyListener {
    fun onPlayersReady(playerList: List<FriendlyPlayer?>?)
}
package com.example.typeracer.data_repository.i_data_source

import com.example.typeracer.data_repository.callback.DefaultCallback

interface GameDataSource {
    fun getGameId(callback: DefaultCallback<String>)
    fun getAllPlayersId(roomId: String, callback: DefaultCallback<List<String>>)
}

package com.example.typeracer.data_repository.i_data_source

import com.example.typeracer.data_repository.callback.DefaultCallback

interface GameDataSource {
    fun getGameId(callback: DefaultCallback<String>)
    fun getAllPlayersId(roomId: String, callback: DefaultCallback<List<String>>)
    fun getSentence(roomId: String, callback: DefaultCallback<String>)
    fun uploadPlayerChanges(id: String, roomId: String, wordCount: Int)
    fun getRealtimeGamePlayers(data: String, defaultCallback: DefaultCallback<List<Pair<String,Int>>>)
}

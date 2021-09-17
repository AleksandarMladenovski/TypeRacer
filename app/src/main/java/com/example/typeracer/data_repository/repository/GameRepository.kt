package com.example.typeracer.data_repository.repository

import androidx.lifecycle.MutableLiveData
import com.example.typeracer.data_repository.model.GamePlayer
import com.example.typeracer.data_repository.model.TypeRacerImages
import com.example.typeracer.data_repository.response.ResponseData

class GameRepository {

    private val players: MutableLiveData<ResponseData<GamePlayer>> by lazy { MutableLiveData<ResponseData<GamePlayer>>() }

    fun joinQueue(){

    }


}
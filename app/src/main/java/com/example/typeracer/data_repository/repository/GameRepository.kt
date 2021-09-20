package com.example.typeracer.data_repository.repository

import androidx.lifecycle.MutableLiveData
import com.example.typeracer.data_repository.callback.BooleanCallback
import com.example.typeracer.data_repository.callback.DefaultCallback
import com.example.typeracer.data_repository.i_data_source_impl.GameNetworkSource
import com.example.typeracer.data_repository.i_data_source_impl.QueueNetworkSource
import com.example.typeracer.data_repository.i_data_source_impl.UserNetworkSource
import com.example.typeracer.data_repository.model.GamePlayer
import com.example.typeracer.data_repository.response.ResponseData
import com.example.typeracer.data_repository.response.ResponseStatus

class GameRepository {

    private val players: MutableLiveData<ResponseData<List<GamePlayer>>> by lazy { MutableLiveData<ResponseData<List<GamePlayer>>>() }
    private val gameId: MutableLiveData<ResponseData<String>> by lazy { MutableLiveData<ResponseData<String>>() }
    private val sentence: MutableLiveData<ResponseData<String>> by lazy { MutableLiveData<ResponseData<String>>() }
    private val gameNetworkSource = GameNetworkSource()
    private val queueNetworkSource = QueueNetworkSource()
    private val userNetworkSource = UserNetworkSource()
    fun joinQueue(): MutableLiveData<ResponseData<String>> {
        queueNetworkSource.joinQueue(object : BooleanCallback {
            override fun onSuccess(value: Boolean) {
                gameNetworkSource.getGameId(object : DefaultCallback<String> {
                    override fun onSuccess(data: String) {
                        if (data.isNotEmpty()) {
                            gameId.postValue(ResponseData(data, "", "", ResponseStatus.Success))
                        }
                    }

                    override fun onFailure(error: String) {
                        gameId.postValue(ResponseData("", error, "", ResponseStatus.Failure))
                    }

                })
            }

            override fun onFailure(error: String) {
                gameId.postValue(ResponseData(error, error, "", ResponseStatus.Failure))
            }

        })
        return gameId
    }

    fun removeFromQueue(): MutableLiveData<ResponseData<Boolean>> {
        val observable: MutableLiveData<ResponseData<Boolean>> by lazy { MutableLiveData<ResponseData<Boolean>>() }
        queueNetworkSource.removeFromQueue(object : BooleanCallback {
            override fun onSuccess(value: Boolean) {
                observable.postValue(ResponseData(true, "", "", ResponseStatus.Success))
            }

            override fun onFailure(error: String) {
                observable.postValue(ResponseData(false, error, "", ResponseStatus.Failure))
            }

        })
        return observable
    }

    fun getAllPlayers(): MutableLiveData<ResponseData<List<GamePlayer>>> {
            gameId.value?.let {
                gameNetworkSource.getRealtimeGamePlayers(it.data, object : DefaultCallback<List<Pair<String,Int>>>{
                    override fun onSuccess(data: List<Pair<String, Int>>) {
                        for(value in data){
                            players.value!!.data.find { it.id == value.first }!!.wordCount=value.second
                        }
                        players.postValue(players.value)
                    }

                    override fun onFailure(error: String) {
                        TODO("Not yet implemented")
                    }
                })
            }
        return players

    }

    fun getTypeSentence(): MutableLiveData<ResponseData<String>> {
        if (sentence.value == null) {
            gameId.value?.let {
                gameNetworkSource.getSentence(it.data, object : DefaultCallback<String> {
                    override fun onSuccess(data: String) {
                        sentence.postValue(ResponseData(data, "", "", ResponseStatus.Success))
                    }

                    override fun onFailure(error: String) {
                        TODO("Not yet implemented")
                    }

                })
            }
        }
        return sentence
    }

    fun prepareData(): MutableLiveData<ResponseData<Boolean>> {
        val observable: MutableLiveData<ResponseData<Boolean>> by lazy { MutableLiveData<ResponseData<Boolean>>() }
        gameId.value?.let {
            gameNetworkSource.getAllPlayersId(it.data, object : DefaultCallback<List<String>> {
                override fun onSuccess(data: List<String>) {
                    userNetworkSource.getPlayersInfo(data,
                        object : DefaultCallback<List<GamePlayer>> {
                            override fun onSuccess(data: List<GamePlayer>) {
                                players.postValue(
                                    ResponseData(
                                        data,
                                        "",
                                        "",
                                        ResponseStatus.Success
                                    )
                                )
                                gameNetworkSource.getSentence(it.data,
                                    object : DefaultCallback<String> {
                                        override fun onSuccess(data: String) {
                                            sentence.postValue(
                                                ResponseData(
                                                    data,
                                                    "",
                                                    "",
                                                    ResponseStatus.Success
                                                )
                                            )
                                            observable.postValue(
                                                ResponseData(
                                                    true,
                                                    "",
                                                    "",
                                                    ResponseStatus.Success
                                                )
                                            )
                                        }

                                        override fun onFailure(error: String) {
                                            observable.postValue(
                                                ResponseData(
                                                    false,
                                                    error,
                                                    "",
                                                    ResponseStatus.Failure
                                                )
                                            )
                                        }

                                    })
                            }

                            override fun onFailure(error: String) {
                                players.postValue(
                                    ResponseData(
                                        listOf(),
                                        error,
                                        "",
                                        ResponseStatus.Failure
                                    )
                                )
                            }
                        })
                }

                override fun onFailure(error: String) {
                    TODO("Not yet implemented")
                }

            })
        }
        return observable
    }

    fun uploadPlayerChanges(id: String, wordCount: Int) {
        gameNetworkSource.uploadPlayerChanges(id, gameId.value!!.data, wordCount)
    }
}
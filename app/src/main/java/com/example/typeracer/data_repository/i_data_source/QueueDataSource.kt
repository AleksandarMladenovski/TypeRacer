package com.example.typeracer.data_repository.i_data_source

import com.example.typeracer.data_repository.callback.BooleanCallback

interface QueueDataSource {
    fun joinQueue(callback: BooleanCallback)
    fun removeFromQueue(callback: BooleanCallback)
}
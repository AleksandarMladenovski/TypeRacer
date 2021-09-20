package com.example.typeracer.data_repository.i_data_source

import com.example.typeracer.data_repository.callback.DefaultCallback
import com.example.typeracer.data_repository.model.TypeRacerImages

interface StorageDataSource {
    fun getAllImages(callback: DefaultCallback<TypeRacerImages>)
}
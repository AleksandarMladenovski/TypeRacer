package com.example.typeracer.data_repository.i_data_source

import android.net.Uri
import com.example.typeracer.data_repository.callback.DefaultCallback

interface StorageDataSource {
    fun getAllProfileImages(callback : DefaultCallback<MutableList<Uri>>)
    fun getAllCarImages(callback : DefaultCallback<MutableList<Uri>>)
}
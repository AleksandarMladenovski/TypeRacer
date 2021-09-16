package com.example.typeracer.data_repository.repository

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import com.example.typeracer.data_repository.callback.DefaultCallback
import com.example.typeracer.data_repository.i_data_source_impl.StorageNetworkSource

class StorageRepository {
    val profiles: MutableLiveData<MutableList<Uri>> by lazy { MutableLiveData<MutableList<Uri>>() }
    val cars: MutableLiveData<MutableList<Uri>> by lazy { MutableLiveData<MutableList<Uri>>() }
    private val storageNetworkSource = StorageNetworkSource()

    fun getAllProfileImages(): MutableLiveData<MutableList<Uri>> {
        if (profiles.value == null) {
            storageNetworkSource.getAllProfileImages(object : DefaultCallback<MutableList<Uri>> {
                override fun onSuccess(data: MutableList<Uri>) {
                    profiles.postValue(data)
                }

                val list = mutableListOf<String>()
                override fun onFailure(error: String) {
                }

            })
        }
        return profiles
    }
}
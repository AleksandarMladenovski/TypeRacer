package com.example.typeracer.data_repository.repository

import androidx.lifecycle.MutableLiveData
import com.example.typeracer.data_repository.callback.DefaultCallback
import com.example.typeracer.data_repository.i_data_source_impl.StorageNetworkSource
import com.example.typeracer.data_repository.model.TypeRacerImages
import com.example.typeracer.data_repository.response.ResponseData
import com.example.typeracer.data_repository.response.ResponseStatus

class StorageRepository {
    private var images: TypeRacerImages? = null
    private val storageNetworkSource = StorageNetworkSource()

    fun getAllImages(): MutableLiveData<ResponseData<TypeRacerImages?>> {
        val observable: MutableLiveData<ResponseData<TypeRacerImages?>> by lazy { MutableLiveData<ResponseData<TypeRacerImages?>>() }
        if (images == null) {
            storageNetworkSource.getAllImages(object : DefaultCallback<TypeRacerImages> {

                override fun onSuccess(data: TypeRacerImages) {
                    images = data
                    observable.postValue(ResponseData(data, "", "", ResponseStatus.Success))
                }

                override fun onFailure(error: String) {
                    observable.postValue(ResponseData(null, error, "", ResponseStatus.Failure))
                }

            })
        }
        return observable
    }

    fun getBasicImages(): Pair<String, String> {
        return Pair(
            images!!.profiles[0].toString(), images!!.cars[0].toString()
        )

    }
//    fun getImageByName(): Uri {
//
//    }
}
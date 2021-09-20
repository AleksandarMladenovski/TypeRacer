package com.example.typeracer.data_repository.i_data_source_impl

import android.net.Uri
import com.example.typeracer.data_repository.FirebaseNetwork
import com.example.typeracer.data_repository.callback.DefaultCallback
import com.example.typeracer.data_repository.i_data_source.StorageDataSource
import com.example.typeracer.data_repository.model.TypeRacerImages

class StorageNetworkSource : StorageDataSource {
    private fun getAllProfileImages(callback: DefaultCallback<TypeRacerImages>) {
        val profiles = mutableListOf<Uri>()
        FirebaseNetwork.getFirebaseStorage().reference.child("images/profile").listAll()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    var counter = 0
                    for (image in it.result.items) {
                        image.downloadUrl.addOnCompleteListener { imageUri ->
                            if (imageUri.isSuccessful) {
                                profiles.add(imageUri.result)
                                counter++
                                if (counter == it.result.items.size) {
                                    getAllCarImages(profiles, callback)
                                }
                            } else {
                                callback.onFailure("Something went wrong!")
                            }
                        }
                    }
                } else {
                    callback.onFailure("Something went wrong!")
                }
            }
    }

    private fun getAllCarImages(
        profiles: MutableList<Uri>,
        callback: DefaultCallback<TypeRacerImages>
    ) {
        val cars = mutableListOf<Uri>()
        FirebaseNetwork.getFirebaseStorage().reference.child("images/car").listAll()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    var counter = 0
                    for (image in it.result.items) {
                        image.downloadUrl.addOnCompleteListener { imageUri ->
                            if (imageUri.isSuccessful) {
                                cars.add(imageUri.result)
                                counter++
                                if (counter == it.result.items.size) {
                                    callback.onSuccess(TypeRacerImages(cars, profiles))
                                }
                            } else {
                                callback.onFailure("Something went wrong!")
                            }
                        }
                    }
                } else {
                    callback.onFailure("Something went wrong!")
                }
            }
    }

    override fun getAllImages(callback: DefaultCallback<TypeRacerImages>) {
        getAllProfileImages(callback)
    }
}
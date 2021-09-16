package com.example.typeracer.data_repository.i_data_source_impl

import android.net.Uri
import com.example.typeracer.data_repository.FirebaseNetwork
import com.example.typeracer.data_repository.callback.DefaultCallback
import com.example.typeracer.data_repository.i_data_source.StorageDataSource

class StorageNetworkSource : StorageDataSource {
    override fun getAllProfileImages(callback: DefaultCallback<MutableList<Uri>>) {
        val list = mutableListOf<Uri>()
        FirebaseNetwork.getFirebaseStorage().reference.child("images/profile").listAll()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    var counter = 0
                    for (image in it.result.items) {
                        image.downloadUrl.addOnCompleteListener { imageUri ->
                            if (imageUri.isSuccessful) {
                                list.add(imageUri.result)
                                counter++
                                if (counter == it.result.items.size) {
                                    callback.onSuccess(list)
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

    override fun getAllCarImages(callback: DefaultCallback<MutableList<Uri>>) {
        val list = mutableListOf<Uri>()
        FirebaseNetwork.getFirebaseStorage().reference.child("images/car").listAll()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    var counter = 0
                    for (image in it.result.items) {
                        image.downloadUrl.addOnCompleteListener { imageUri ->
                            if (imageUri.isSuccessful) {
                                list.add(imageUri.result)
                                counter++
                                if (counter == it.result.items.size) {
                                    callback.onSuccess(list)
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
}
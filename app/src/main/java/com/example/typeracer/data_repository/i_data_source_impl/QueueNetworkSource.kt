package com.example.typeracer.data_repository.i_data_source_impl

import com.example.typeracer.data_repository.FirebaseNetwork
import com.example.typeracer.data_repository.callback.BooleanCallback
import com.example.typeracer.data_repository.i_data_source.QueueDataSource

class QueueNetworkSource : QueueDataSource {
    override fun joinQueue(callback: BooleanCallback) {
        FirebaseNetwork.getFirebaseFunctions().getHttpsCallable("addToQueue").call()
            .continueWith { task ->
                if (task.isSuccessful) {
                    callback.onSuccess(true)
                } else {
                    callback.onFailure(task.exception?.localizedMessage ?: "Error")
                }
            }
    }

    override fun removeFromQueue(callback: BooleanCallback) {
        FirebaseNetwork.getFirebaseFunctions().getHttpsCallable("removeFromQueue").call(hashMapOf("id" to "id"))
            .continueWith { task ->
                if (task.isSuccessful) {
                    callback.onSuccess(true)
                } else {
                    callback.onFailure(task.exception?.localizedMessage ?: "Error")
                }
            }
    }
}
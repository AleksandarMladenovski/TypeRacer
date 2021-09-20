package com.example.typeracer.data_repository.i_data_source_impl

import com.example.typeracer.data_repository.FirebaseNetwork
import com.example.typeracer.data_repository.callback.DefaultCallback
import com.example.typeracer.data_repository.i_data_source.GameDataSource
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class GameNetworkSource : GameDataSource {

    override fun getGameId(callback: DefaultCallback<String>) {
        FirebaseNetwork.getFirebaseDatabase().reference.child("users")
            .child(FirebaseNetwork.getFirebaseAuth().currentUser!!.uid).addValueEventListener(object :
                ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    callback.onSuccess(dataSnapshot.child("roomId").value as String)
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    callback.onFailure(databaseError.message)
                }
            })
    }


    override fun getAllPlayersId(roomId: String, callback: DefaultCallback<List<String>>) {
        FirebaseNetwork.getFirebaseDatabase().reference.child("rooms").child(roomId).get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val list = mutableListOf<String>()
                    task.result.children.forEach { user ->
                        list.add(user.key!!)
                    }
                    callback.onSuccess(list)
                }else{
                    callback.onFailure(task.exception.toString())
                }
            }
    }
}
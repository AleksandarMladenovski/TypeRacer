package com.example.typeracer.data_repository.repository

import android.app.Activity
import androidx.lifecycle.MutableLiveData
import com.example.typeracer.data_repository.callback.UserCallback
import com.example.typeracer.data_repository.i_data_source_impl.UserNetworkSource
import com.example.typeracer.data_repository.model.User
import com.example.typeracer.data_repository.response.ResponseData
import com.example.typeracer.data_repository.response.ResponseStatus

class UserRepository {
    private val currentUser: MutableLiveData<User> by lazy { MutableLiveData<User>() }
    private val userNetworkSource = UserNetworkSource()
    fun loginWithProvider(
        provider: String,
        activity: Activity
    ): MutableLiveData<ResponseData<Boolean>> {
        val observable: MutableLiveData<ResponseData<Boolean>> by lazy { MutableLiveData<ResponseData<Boolean>>() }
        userNetworkSource.loginWithProvider(provider, activity, object : UserCallback {
            override fun onSuccess(user: User) {
                currentUser.postValue(user)
                observable.postValue(ResponseData(true, "error", "", ResponseStatus.Success))
            }

            override fun onFailure(error: String) {
                observable.postValue(ResponseData(false, error, "", ResponseStatus.Failure))
            }

        })
        return observable
    }

    fun createUserByBasic(
        email: String,
        name: String,
        password: String
    ): MutableLiveData<ResponseData<Boolean>> {
        val observable: MutableLiveData<ResponseData<Boolean>> by lazy { MutableLiveData<ResponseData<Boolean>>() }
        userNetworkSource.createUserByBasic(email, name, password, object : UserCallback {
            override fun onSuccess(user: User) {
                currentUser.postValue(user)
                observable.postValue(ResponseData(true, "error", "", ResponseStatus.Success))
            }

            override fun onFailure(error: String) {
                observable.postValue(ResponseData(false, error, "", ResponseStatus.Failure))
            }

        })
        return observable
    }
}
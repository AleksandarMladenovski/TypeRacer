package com.extremedesign.typeracer.data_repository.i_database_implementations

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.extremedesign.typeracer.listener.JobWorker
import com.extremedesign.typeracer.listener.ProfileImageListener
import com.extremedesign.typeracer.model.ProfileImage
import com.extremedesign.typeracer.model.User

class NetworkDataSource : IDataSource {
    override fun insertUser(user: User?) {}
    override fun getCurrentUser(uid: String?): LiveData<User?>? {
        val observable: MutableLiveData<User> by lazy { MutableLiveData<User>() }
        return observable
    }
    override fun insertDataProfileImage(profileImage: ProfileImage?, listener: JobWorker?) {}
    override fun insertDataProfileImages(profileImages: List<ProfileImage?>?) {}
    override fun getDataProfileImages(listener: ProfileImageListener?) {}
    override fun getProfileImageByName(name: String?, listener: ProfileImageListener?) {}
}
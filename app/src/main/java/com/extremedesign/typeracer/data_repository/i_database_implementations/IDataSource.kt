package com.extremedesign.typeracer.data_repository.i_database_implementations

import androidx.lifecycle.LiveData
import com.extremedesign.typeracer.listener.JobWorker
import com.extremedesign.typeracer.listener.ProfileImageListener
import com.extremedesign.typeracer.model.ProfileImage
import com.extremedesign.typeracer.model.User

interface IDataSource {
    fun insertUser(user: User?)
    fun getCurrentUser(uid: String?): LiveData<User?>?
    fun insertDataProfileImage(profileImage: ProfileImage?, listener: JobWorker?)
    fun insertDataProfileImages(profileImages: List<ProfileImage?>?)
    fun getDataProfileImages(listener: ProfileImageListener?)
    fun getProfileImageByName(name: String?, listener: ProfileImageListener?)
}
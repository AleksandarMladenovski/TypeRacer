package com.extremedesign.typeracer.data_repository.i_database_implementations

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.extremedesign.typeracer.data_repository.room_database.TypeRacerDatabase
import com.extremedesign.typeracer.listener.JobWorker
import com.extremedesign.typeracer.listener.ProfileImageListener
import com.extremedesign.typeracer.model.ProfileImage
import com.extremedesign.typeracer.model.User

class DatabaseDataSource(private val context: Context) : IDataSource {
    override fun insertUser(user: User?) {
        Thread { TypeRacerDatabase.getDatabase(context).typeRacerDAO().insertUser(user) }.start()
    }

    override fun getCurrentUser(uid: String?): LiveData<User?> {
        val data = MutableLiveData<User>()
        Thread {
            data.setValue(TypeRacerDatabase.getDatabase(context).typeRacerDAO().getUserByUid(uid))
        }.start()
        return data
    }

    override fun insertDataProfileImage(profileImage: ProfileImage?, listener: JobWorker?) {
        Thread {
            TypeRacerDatabase.getDatabase(context).typeRacerDAO().insertProfileImage(profileImage)
            listener!!.jobFinished(true)
        }.start()
    }

    override fun insertDataProfileImages(profileImages: List<ProfileImage?>?) {
        TODO("Not yet implemented")
    }

    override fun getDataProfileImages(listener: ProfileImageListener?) {
        Thread {
            val profileImages = TypeRacerDatabase.getDatabase(context).typeRacerDAO().getAllProfileImages()
            listener!!.getImages(profileImages)
        }.start()
    }

    override fun getProfileImageByName(name: String?, listener: ProfileImageListener?) {
        Thread {
            val profileImages = TypeRacerDatabase.getDatabase(context).typeRacerDAO().getProfileImageByName(name)
            listener!!.getImages(profileImages)
        }.start()
    }
}
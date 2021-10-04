package com.extremedesign.typeracer.data_repository.room_database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.extremedesign.typeracer.model.ProfileImage
import com.extremedesign.typeracer.model.User

@Dao
interface TypeRacerDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProfileImages(images: List<ProfileImage?>?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProfileImage(images: ProfileImage?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: User?)

    @Query("SELECT * FROM User WHERE uid LIKE :id ")
    fun getUserByUid(id: String?): User?

    @Query("SELECT * FROM ProfileImage")
    fun getAllProfileImages(): List<ProfileImage?>?

    @Query("SELECT * FROM ProfileImage WHERE name LIKE:name")
    fun getProfileImageByName(name: String?): List<ProfileImage?>?
}
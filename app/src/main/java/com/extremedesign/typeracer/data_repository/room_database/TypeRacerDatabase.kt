package com.extremedesign.typeracer.data_repository.room_database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.extremedesign.typeracer.model.ProfileImage
import com.extremedesign.typeracer.model.User

@Database(entities = [ProfileImage::class, User::class], version = 1)
abstract class TypeRacerDatabase : RoomDatabase() {
    abstract fun typeRacerDAO(): TypeRacerDAO

    companion object {
        private var INSTANCE: TypeRacerDatabase? = null

        fun getDatabase(context: Context?): TypeRacerDatabase {
            if (INSTANCE == null) {
                synchronized(TypeRacerDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(context!!, TypeRacerDatabase::class.java, "typeracer_db")
                                .addCallback(object : RoomDatabase.Callback() {})
                                .fallbackToDestructiveMigration()
                                .build()
                    }
                }
            }
            return INSTANCE!!
        }
    }
}
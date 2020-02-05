package com.extremedesign.typeracer.DataRepository.RoomDatabase;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.extremedesign.typeracer.model.ProfileImage;

@Database(entities = {ProfileImage.class},version = 1)
public abstract class TypeRacerDatabase extends RoomDatabase {
    private static TypeRacerDatabase INSTANCE;
    public abstract TypeRacerDAO typeRacerDAO();

    public static TypeRacerDatabase getDatabase(Context context){
        if(INSTANCE==null){
            synchronized (TypeRacerDatabase.class){
                if(INSTANCE==null){
                    INSTANCE= Room.databaseBuilder(context, TypeRacerDatabase.class,"questions_db")
                            .addCallback(new Callback() {
                                @Override
                                public void onOpen(@NonNull SupportSQLiteDatabase db) {
                                    super.onOpen(db);
                                }
                            })
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }

        }

        return INSTANCE;
    }

}

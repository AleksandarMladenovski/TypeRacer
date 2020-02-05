package com.extremedesign.typeracer.DataRepository.RoomDatabase;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.extremedesign.typeracer.model.ProfileImage;

import java.util.List;

@Dao
public interface TypeRacerDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertProfileImages(List<ProfileImage> images);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertProfileImage(ProfileImage images);

    @Query("SELECT * FROM ProfileImage")
    List<ProfileImage> getAllProfileImages();

    @Query("SELECT * FROM ProfileImage WHERE name LIKE:name")
    List<ProfileImage> getProfileImageByName(String name);

//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    void insertNotes(List<Question> questions);
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    void insertCategory(Category category);
//
//    @Query("SELECT * FROM Question")
//    List<Question> getAllQuestions();
//
//    @Query("SELECT * FROM Question WHERE category LIKE:category")
//    List<Question> getAllQuestionsByCategory(String category);
//
//    @Query("SELECT * FROM Category WHERE category LIKE:category ")
//    Category getObjectForCategory(String category);
//
//
//    @Query("SELECT * FROM Category")
//    List<Category> getAllCategories();



}

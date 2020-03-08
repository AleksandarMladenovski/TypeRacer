package com.extremedesign.typeracer.DataRepository.IDataBaseImplementations;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.extremedesign.typeracer.listener.JobWorker;
import com.extremedesign.typeracer.listener.ProfileImageListener;
import com.extremedesign.typeracer.model.ProfileImage;
import com.extremedesign.typeracer.model.User;

import java.util.List;

public interface IDataSource {
//    void getData(String amount, String category, String difficulty, String type, final CustomListenerRep listenerRep);
//
//    void insertData(List<Question> questionList);
//
//    void getListOfCategories(CategoryListener listener);

    void insertUser(User user);

    LiveData<User> getCurrentUser(String uid);

    void insertDataProfileImage(ProfileImage profileImage, JobWorker listener);

    void insertDataProfileImages(List<ProfileImage> profileImages);

    void getDataProfileImages(ProfileImageListener listener);

    void getProfileImageByName(String name,ProfileImageListener listener);

}

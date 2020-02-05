package com.extremedesign.typeracer.DataRepository.IDataBaseImplementations;

import com.extremedesign.typeracer.listener.JobWorker;
import com.extremedesign.typeracer.listener.ProfileImageListener;
import com.extremedesign.typeracer.model.ProfileImage;

import java.util.List;

public interface IDataSource {
//    void getData(String amount, String category, String difficulty, String type, final CustomListenerRep listenerRep);
//
//    void insertData(List<Question> questionList);
//
//    void getListOfCategories(CategoryListener listener);

    void insertDataProfileImage(ProfileImage profileImage, JobWorker listener);

    void insertDataProfileImages(List<ProfileImage> profileImages);

    void getDataProfileImages(ProfileImageListener listener);

    void getProfileImageByName(String name,ProfileImageListener listener);

}

package com.extremedesign.typeracer.DataRepository.IDataBaseImplementations;


import androidx.lifecycle.LiveData;

import com.extremedesign.typeracer.listener.JobWorker;
import com.extremedesign.typeracer.listener.ProfileImageListener;
import com.extremedesign.typeracer.model.ProfileImage;
import com.extremedesign.typeracer.model.User;

import java.util.List;

public class NetworkDataSource  implements IDataSource
 {
  @Override
  public void insertUser(User user) {

  }

  @Override
  public LiveData<User> getCurrentUser(String uid) {
   return null;
  }

  @Override
  public void insertDataProfileImage(ProfileImage profileImage, JobWorker listener) {

  }

  @Override
  public void insertDataProfileImages(List<ProfileImage> profileImages) {

  }

  @Override
  public void getDataProfileImages(ProfileImageListener listener) {

  }

  @Override
  public void getProfileImageByName(String name, ProfileImageListener listener) {

  }


//    private static GetRetrofitDataService dataServicee;
//
//    public NetworkDataSource(Retrofit retrofit) {
//        if (dataServicee == null) {
//            dataServicee = retrofit.create(GetRetrofitDataService.class);
//        }
//
//    }
//
//    @Override
//    public void getData(String amount, String category, String difficulty, String type,final CustomListenerRep listenerRep) {
//
//
//        Call<OpenTDBResult> call = dataServicee.get_Trivia_Questions(amount, category, difficulty, type);
//        call.enqueue(new Callback<OpenTDBResult>() {
//            @Override
//            public void onResponse(Call<OpenTDBResult> call, Response<OpenTDBResult> response) {
//                listenerRep.RepReturn(response.body().getQuestions());
//            }
//
//            @Override
//            public void onFailure(Call<OpenTDBResult> call, Throwable t) {
//                listenerRep.RepReturn(new ArrayList<Question>());
//            }
//        });
//
//    }
//    @Override
//    public void insertData(List<Question> questionList) {
//        //TODO MAYBE POST METHOD
//    }
//
//    @Override
//    public void getListOfCategories(CategoryListener listener) {
//
//    }
}

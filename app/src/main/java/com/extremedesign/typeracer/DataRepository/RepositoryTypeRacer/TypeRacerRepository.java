package com.extremedesign.typeracer.DataRepository.RepositoryTypeRacer;

import android.content.Context;

import com.extremedesign.typeracer.listener.JobWorker;

public interface TypeRacerRepository {
//   void getDataFromAPI(String amount, String category, String difficulty, String type, CustomListenerRep listenerRep);
//   void getDataFromDatabase(String category, CustomListenerRep listenerRep);
//   void insertDataToDatabase(List<Question> questionList);
//   void getListOfCategories(CategoryListener listener);

//      void getPorifleImages

   void synchronizeProfileImages(JobWorker listener);
}

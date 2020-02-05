package com.extremedesign.typeracer.DataRepository;

import android.content.Context;

import com.extremedesign.typeracer.DataRepository.IDataBaseImplementations.DatabaseDataSource;
import com.extremedesign.typeracer.DataRepository.IDataBaseImplementations.IDataSource;
import com.extremedesign.typeracer.DataRepository.RepositoryTypeRacer.TypeRacerRepositoryImpl;

public class RepositoryInstance {
   private static TypeRacerRepositoryImpl repository;

    public static TypeRacerRepositoryImpl getTypeRacerRepository(Context context){
        if(repository==null){
            IDataSource database=new DatabaseDataSource(context);
            repository=new TypeRacerRepositoryImpl(context,database);
        }

//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("https://opentdb.com/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//        IDataSource network = new NetworkDataSource(retrofit);

//        repositoryQuestion=new RepositoryQuestionImpl(network, database);

        return repository;
    }
}

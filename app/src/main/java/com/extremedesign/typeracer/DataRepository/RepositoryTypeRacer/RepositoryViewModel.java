package com.extremedesign.typeracer.DataRepository.RepositoryTypeRacer;


import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.extremedesign.typeracer.DataRepository.IDataBaseImplementations.DatabaseDataSource;
import com.extremedesign.typeracer.DataRepository.IDataBaseImplementations.IDataSource;
import com.extremedesign.typeracer.FirebaseRepo;
import com.extremedesign.typeracer.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class RepositoryViewModel extends AndroidViewModel  {
    //    private IDataSource networkDataSource;
//    private IDataSource databaseDataSource;
    private WeakReference<Application>  application;
    private FirebaseRepo firebaseRepo;

    public RepositoryViewModel(@NonNull Application application) {
        super(application);
//        this.databaseDataSource = new DatabaseDataSource(application);
        this.application = new WeakReference<>(application);

    }


    public boolean isUserLoggedIn(){
        return getFirebaseRepo().isUserLoggedIn();
    }

//    class syncUserTask extends AsyncTask<Void,Void,Void>{
//
//        @Override
//        protected Void doInBackground(Void... voids) {
//            databaseDataSource.insertUser(firebaseRepo.getMutableUser().getValue());
//            firebaseRepo.createCurrentUser();
//
//
//
//            return null;
//        }
//    }

    public void logInWithProvider(String nameOfProvider, OnCompleteListener<AuthResult> listener, Activity activity){
        firebaseRepo.logInWithProvider(listener,nameOfProvider,activity);
    }
    public MutableLiveData<User> getCurrentUser() {
        return getFirebaseRepo().getCurrentUser();
    }

    public FirebaseRepo getFirebaseRepo() {
        if(firebaseRepo==null){
            firebaseRepo=new FirebaseRepo();
        }
        return firebaseRepo;
    }

    public void setFirebaseRepo(FirebaseRepo firebaseRepo) {
        this.firebaseRepo = firebaseRepo;
    }
}
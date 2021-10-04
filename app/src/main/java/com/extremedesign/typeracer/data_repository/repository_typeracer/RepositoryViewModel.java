package com.extremedesign.typeracer.data_repository.repository_typeracer;


import android.app.Activity;
import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.extremedesign.typeracer.FirebaseRepo;
import com.extremedesign.typeracer.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;

import java.lang.ref.WeakReference;

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
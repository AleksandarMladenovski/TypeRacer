package com.extremedesign.typeracer.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.extremedesign.typeracer.DataRepository.RepositoryTypeRacer.RepositoryViewModel;
import com.extremedesign.typeracer.FirebaseRepo;
import com.extremedesign.typeracer.R;
import com.extremedesign.typeracer.model.User;


public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        RepositoryViewModel repositoryViewModel=ViewModelProviders.of(this).get(RepositoryViewModel.class);
                        if(repositoryViewModel.isUserLoggedIn()){
                            Intent i = new Intent(SplashActivity.this,MainActivity.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(i);
                        }
                        else{
                            Intent i = new Intent(SplashActivity.this,LoginActivity.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(i);
                        }
                    }



}

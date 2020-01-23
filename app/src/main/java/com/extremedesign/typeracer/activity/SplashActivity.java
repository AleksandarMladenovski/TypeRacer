package com.extremedesign.typeracer.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.extremedesign.typeracer.FirebaseRepo;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(FirebaseRepo.createCurrentUser()){
            isUserLoggedIn(true);
        }
        else{
            isUserLoggedIn(false);
        }
    }

    private void isUserLoggedIn(boolean state) {
//        finish();
        if(state){
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

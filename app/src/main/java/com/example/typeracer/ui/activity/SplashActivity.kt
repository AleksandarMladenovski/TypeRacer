package com.example.typeracer.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import com.example.typeracer.R
import com.example.typeracer.data_repository.model.TypeRacerImages
import com.example.typeracer.data_repository.response.ResponseData
import com.example.typeracer.data_repository.response.ResponseStatus
import com.example.typeracer.persistance.PreferencesTypeRacer
import com.example.typeracer.utils.GlobalConstants.USER_KEY
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    private val splashViewModel : SplashViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        getAllImages()
    }
    fun getAllImages(){
        splashViewModel.getAllImages().observe(this,getImageObserver())
    }

    private fun getImageObserver(): Observer<ResponseData<TypeRacerImages?>> {
        return Observer<ResponseData<TypeRacerImages?>> { response ->
            if(response.status == ResponseStatus.Success){
                checkUser()
            }else{
                //TODO PROBLEM GETTING IMAGES
            }
        }
    }
    private fun checkUser(){
        if (splashViewModel.isUserLoggedIn()) {
            val i = Intent(this@SplashActivity, MainActivity::class.java)
            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(i)
        } else {
            val i = Intent(this@SplashActivity, LoginActivity::class.java)
            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(i)
        }
    }
}
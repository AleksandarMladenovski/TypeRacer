package com.extremedesign.typeracer.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.extremedesign.typeracer.R
import com.extremedesign.typeracer.data_repository.repository_typeracer.RepositoryViewModel
import com.extremedesign.typeracer.persistance.PreferencesTypeRacer
import com.extremedesign.typeracer.utils.GlobalConstants.USER_KEY
import org.koin.android.ext.android.inject

class SplashActivity : AppCompatActivity() {
    private val preferences: PreferencesTypeRacer by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        if (preferences.getBoolean(USER_KEY)) {
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
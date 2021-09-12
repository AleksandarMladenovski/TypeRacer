package com.example.typeracer.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.typeracer.R
import com.example.typeracer.databinding.ActivityMainBinding
import com.example.typeracer.databinding.FragmentLoginBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
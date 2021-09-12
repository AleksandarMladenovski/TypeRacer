package com.example.typeracer.ui.splash_game.model

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.typeracer.R
import com.example.typeracer.databinding.FragmentSettingsBinding
import com.example.typeracer.databinding.FragmentSplashGameBinding

class SplashGameFragment : Fragment() {
    private lateinit var binding: FragmentSplashGameBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_splash_game, container, false)
        return binding.root
    }
}
package com.example.typeracer.ui.game.model

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.typeracer.R
import com.example.typeracer.databinding.FragmentGameBinding
import com.example.typeracer.databinding.FragmentHomeBinding

class GameFragment : Fragment() {
    private lateinit var binding: FragmentGameBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_game, container, false)
        return binding.root
    }

}
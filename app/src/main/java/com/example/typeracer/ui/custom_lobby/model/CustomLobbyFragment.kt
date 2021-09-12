package com.example.typeracer.ui.custom_lobby.model

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.typeracer.R
import com.example.typeracer.databinding.FragmentCustomLobbyBinding
import com.example.typeracer.databinding.FragmentHomeBinding

class CustomLobbyFragment : Fragment() {


    private lateinit var binding: FragmentCustomLobbyBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_custom_lobby, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}
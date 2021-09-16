package com.example.typeracer.ui.home

import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.typeracer.R
import com.example.typeracer.databinding.FragmentHomeBinding
import org.koin.android.viewmodel.ext.android.viewModel
import android.graphics.drawable.Drawable
import com.example.typeracer.ui.activity.GameActivity
import com.example.typeracer.ui.activity.MainActivity
import com.google.common.reflect.Reflection.getPackageName


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val homeViewModel: HomeViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listenForRandomLobby()
        listenForCustomLobby()
        listenForSettings()
        getCurrentUser()
    }

    private fun getCurrentUser() {
        homeViewModel.getCurrentUser().observe(viewLifecycleOwner,{ user->
            val photoId = resources.getIdentifier(user.photoName, "drawable", requireContext().packageName)
            binding.photoImageButton.setImageResource(photoId)
            val carId = resources.getIdentifier(user.carName, "drawable", requireContext().packageName)
            binding.carImageButton.setImageResource(carId)
        })
    }

    private fun listenForCustomLobby() {
        binding.buttonCustomLobby.setOnClickListener {
            //TODO START GAME
        }
    }

    private fun listenForSettings() {
        binding.accountSettingsIcon.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(HomeFragmentDirections.actionHomeFragmentToSettingsFragment())
        }
    }

    private fun listenForRandomLobby() {
        binding.buttonRandomLobby.setOnClickListener {
            (activity as MainActivity).startGameActivity()
        }
    }

}
package com.example.typeracer.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.example.typeracer.R
import com.example.typeracer.data_repository.response.ResponseStatus
import com.example.typeracer.databinding.FragmentHomeBinding
import com.example.typeracer.ui.activity.MainActivity
import org.koin.android.viewmodel.ext.android.viewModel


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
        listenForRemoveFromQueue()
        listenForSettings()
        getCurrentUser()
    }

    private fun listenForRemoveFromQueue() {
        binding.buttonRemoveFromQueue.setOnClickListener {
            homeViewModel.removeFromQueue().observe(viewLifecycleOwner, { response ->
                if (response.status == ResponseStatus.Success) {
                    binding.queueLayout.visibility = View.GONE
                } else {
                    Toast.makeText(context, response.errorMessage, Toast.LENGTH_LONG).show()
                }

            })
        }
    }

    private fun getCurrentUser() {
        homeViewModel.getCurrentUser().observe(viewLifecycleOwner, { user ->
            Glide
                .with(requireActivity())
                .load(user.photoName)
                .centerCrop()
                .into(binding.carImageButton)
            Glide
                .with(requireActivity())
                .load(user.carName)
                .centerCrop()
                .into(binding.photoImageButton)
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
            binding.queueLayout.visibility = View.VISIBLE
            homeViewModel.joinQueue().observe(viewLifecycleOwner, { response ->
                if (response.status == ResponseStatus.Success) {
                    startGameActivity()
                } else {
                    Toast.makeText(context, response.errorMessage, Toast.LENGTH_LONG).show()
                }
            })
        }
    }

    private fun startGameActivity() {
        (activity as MainActivity).startGameActivity()
    }
}
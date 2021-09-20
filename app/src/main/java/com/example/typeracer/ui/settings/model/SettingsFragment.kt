package com.example.typeracer.ui.settings.model

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.example.typeracer.R
import com.example.typeracer.databinding.FragmentSettingsBinding
import com.example.typeracer.ui.activity.LoginActivity
import com.example.typeracer.ui.settings.viewmodel.SettingsViewModel
import kotlinx.android.synthetic.main.fragment_register.*
import org.koin.android.viewmodel.ext.android.sharedViewModel

class SettingsFragment : Fragment() {

    private lateinit var binding: FragmentSettingsBinding
    private val settingsViewModel: SettingsViewModel by sharedViewModel()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_settings, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listenForLogOutClick()
        listenForEditProfile()
        getCurrentUser()
        onBackClick()
    }

    private fun listenForEditProfile() {
        binding.editProfile.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(SettingsFragmentDirections.actionSettingsFragmentToEditProfileFragment())
        }
    }

    private fun listenForLogOutClick() {
        binding.buttonSignOut.setOnClickListener {
            settingsViewModel.userLogOut()
            val newIntent = Intent(requireActivity(), LoginActivity::class.java)
            startActivity(newIntent)
        }
    }

    private fun getCurrentUser() {
        settingsViewModel.getCurrentUser().observe(viewLifecycleOwner,{ user->
            Glide
                .with(requireActivity())
                .load(user.photoName)
                .centerCrop()
                .into(binding.userPhotoImage)
            Glide
                .with(requireActivity())
                .load(user.carName)
                .centerCrop()
                .into(binding.userCarImage)
            binding.userName.text = user!!.name
            binding.userEmail.text = user.email
        })
    }

    private fun onBackClick() {
        binding.actionBarIconBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }


}
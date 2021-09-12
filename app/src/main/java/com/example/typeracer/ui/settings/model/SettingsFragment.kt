package com.example.typeracer.ui.settings.model

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
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
            val photoId = resources.getIdentifier(user.photoName, "drawable", requireContext().packageName)
            binding.userPhotoImage.setImageResource(photoId)
            val carId = resources.getIdentifier(user.carName, "drawable", requireContext().packageName)
            binding.userCarImage.setImageResource(carId)
            binding.userName.text = user!!.name
        })
    }


}
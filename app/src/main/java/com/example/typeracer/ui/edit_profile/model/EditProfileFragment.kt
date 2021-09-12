package com.example.typeracer.ui.edit_profile.model

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.typeracer.R
import com.example.typeracer.databinding.FragmentEditProfileBinding
import com.example.typeracer.ui.edit_profile.viewmodel.EditProfileViewModel
import kotlinx.android.synthetic.main.fragment_register.*
import org.koin.android.viewmodel.ext.android.viewModel

class EditProfileFragment : Fragment() {

    private lateinit var binding: FragmentEditProfileBinding
    private val editProfileViewModel: EditProfileViewModel by viewModel()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_edit_profile, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val user = editProfileViewModel.getCurrentUser()
        getCurrentUser()
    }

    private fun getCurrentUser() {
        editProfileViewModel.getCurrentUser().observe(viewLifecycleOwner,{ user->
            val photoId = resources.getIdentifier(user.photoName, "drawable", requireContext().packageName)
            binding.userPhotoImage.setImageResource(photoId)
            val carId = resources.getIdentifier(user.carName, "drawable", requireContext().packageName)
            binding.userCarImage.setImageResource(carId)
            binding.editName.setText(user!!.name)
        })
    }

}
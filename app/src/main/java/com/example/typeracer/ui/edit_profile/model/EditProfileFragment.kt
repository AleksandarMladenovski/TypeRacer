package com.example.typeracer.ui.edit_profile.model

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.typeracer.R
import com.example.typeracer.data_repository.model.TypeRacerImages
import com.example.typeracer.data_repository.model.User
import com.example.typeracer.data_repository.response.ResponseData
import com.example.typeracer.databinding.FragmentEditProfileBinding
import com.example.typeracer.ui.edit_profile.adapter.CarImageAdapter
import com.example.typeracer.ui.edit_profile.adapter.ChangeImageListener
import com.example.typeracer.ui.edit_profile.adapter.ProfileImageAdapter
import com.example.typeracer.ui.edit_profile.viewmodel.EditProfileViewModel
import com.example.typeracer.utils.GlobalConstants.CHANGE_IMAGE_CAR
import com.example.typeracer.utils.GlobalConstants.CHANGE_IMAGE_PROFILE
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.android.synthetic.main.fragment_register.*
import org.koin.android.ext.android.bind
import org.koin.android.viewmodel.ext.android.viewModel

class EditProfileFragment : Fragment(), ChangeImageListener {

    private lateinit var binding: FragmentEditProfileBinding
    private val viewModel: EditProfileViewModel by viewModel()
    private lateinit var user: User
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_edit_profile, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getCurrentUser()
        onSaveClick()
        onBackClick()
    }

    private fun populateProfileAdapter(user: User) {
        binding.userPhotoRecyclerView.apply {
            layoutManager =
                    GridLayoutManager(
                            requireContext(),
                            5,
                            LinearLayoutManager.VERTICAL,
                            false
                    )
            adapter = ProfileImageAdapter(requireContext(), viewModel.getProfileImages()!!.profiles, user, this@EditProfileFragment)
        }
    }

    private fun populateCarAdapter(user: User) {
        binding.userCarRecyclerView.apply {
            layoutManager =
                    GridLayoutManager(
                            requireContext(),
                            5,
                            LinearLayoutManager.VERTICAL,
                            false
                    )
            adapter = CarImageAdapter(requireContext(), viewModel.getProfileImages()!!.cars, user, this@EditProfileFragment)
        }
    }


    private fun getCurrentUser() {
        viewModel.getCurrentUser().observe(viewLifecycleOwner, { user ->
            this.user = user
            binding.editName.setText(user.name)
            populateProfileAdapter(user)
            populateCarAdapter(user)
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
        })
    }

    override fun changeImage(type: String, uri: Uri) {
        if (type == CHANGE_IMAGE_PROFILE) {
            changeImageGlide(uri , binding.userPhotoImage)
            user.photoName = uri.toString()
        } else {
            changeImageGlide(uri , binding.userCarImage)
            user.carName = uri.toString()
        }
    }

    fun changeImageGlide(uri:Uri,imageView:ImageView) {
        Glide
                .with(requireActivity())
                .load(uri)
                .centerCrop()
                .into(imageView)
    }

    private fun onSaveClick() {
        binding.saveButton.setOnClickListener {
            user.name = binding.editName.text.toString()
            viewModel.updateUser(user.uid,user.convertToFirebaseDatabaseUser())
        }
    }

    private fun onBackClick() {
        binding.actionBarIconBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }
}
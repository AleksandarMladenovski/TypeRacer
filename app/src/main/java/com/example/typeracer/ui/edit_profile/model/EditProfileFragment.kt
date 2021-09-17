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
import org.koin.android.viewmodel.ext.android.viewModel

class EditProfileFragment : Fragment(), ChangeImageListener {

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
        getCurrentUser()
        populateProfileAdapter(0)
        populateCarAdapter(0)
    }

    private fun populateProfileAdapter(position: Int) {
        binding.userPhotoRecyclerView.apply {
            layoutManager =
                    GridLayoutManager(
                            requireContext(),
                            5,
                            LinearLayoutManager.VERTICAL,
                            false
                    )
            adapter = ProfileImageAdapter(requireContext(), editProfileViewModel.getProfileImages()!!.profiles, position, this@EditProfileFragment)
        }
    }

    private fun populateCarAdapter(position: Int) {
        binding.userCarRecyclerView.apply {
            layoutManager =
                    GridLayoutManager(
                            requireContext(),
                            5,
                            LinearLayoutManager.VERTICAL,
                            false
                    )
            adapter = CarImageAdapter(requireContext(), editProfileViewModel.getProfileImages()!!.cars, position, this@EditProfileFragment)
        }
    }


    private fun getCurrentUser() {
        editProfileViewModel.getCurrentUser().observe(viewLifecycleOwner, { user ->
            val photoId = resources.getIdentifier(user.photoName, "drawable", requireContext().packageName)
            binding.userPhotoImage.setImageResource(photoId)
            val carId = resources.getIdentifier(user.carName, "drawable", requireContext().packageName)
            binding.userCarImage.setImageResource(carId)
            binding.editName.setText(user!!.name)
//            changeImageGlide(user.photoName , binding.userPhotoImage)
//            changeImageGlide(user.photoName , binding.userCarImage)
        })
    }

    override fun changeImage(type: String, uri: Uri) {
        if (type == CHANGE_IMAGE_PROFILE) {
            changeImageGlide(uri , binding.userPhotoImage)
        } else {
            changeImageGlide(uri , binding.userCarImage)
        }
    }

    fun changeImageGlide(uri:Uri,imageView:ImageView) {
        Glide
                .with(requireActivity())
                .load(uri)
                .centerCrop()
                .into(imageView)
    }
}
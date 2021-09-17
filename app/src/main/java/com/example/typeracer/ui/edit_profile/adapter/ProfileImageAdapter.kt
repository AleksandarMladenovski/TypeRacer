package com.example.typeracer.ui.edit_profile.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.typeracer.R
import com.example.typeracer.databinding.ItemProfileImageBinding
import com.example.typeracer.utils.GlobalConstants

class ProfileImageAdapter(
        var context: Context,
        var data: MutableList<Uri>,
        var selectedPosition: Int,
        private val changeImageListener: ChangeImageListener
) : RecyclerView.Adapter<ProfileImageAdapter.ProfileImageViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileImageViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemProfileImageBinding.inflate(inflater, parent, false)
        return ProfileImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProfileImageViewHolder, position: Int) {
        Glide
                .with(context)
                .load(data[position])
                .centerCrop()
                .into(holder.binding.itemProfileImage)
        if (selectedPosition == position) {
            holder.binding.itemProfileImageLayout.setBackgroundColor(context.getColor(R.color.black))
        } else {
            holder.binding.itemProfileImageLayout.setBackgroundColor(context.getColor(R.color.white))
        }

        holder.binding.itemProfileImage.setOnClickListener {
            changeImageListener.changeImage(GlobalConstants.CHANGE_IMAGE_PROFILE,data[position])
            selectedPosition = position
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int = data.size

    class ProfileImageViewHolder(val binding: ItemProfileImageBinding) : RecyclerView.ViewHolder(binding.root)
}
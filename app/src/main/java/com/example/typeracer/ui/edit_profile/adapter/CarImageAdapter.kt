package com.example.typeracer.ui.edit_profile.adapter

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.OpenableColumns
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.typeracer.R
import com.example.typeracer.data_repository.model.User
import com.example.typeracer.databinding.ItemProfileImageBinding
import com.example.typeracer.utils.GlobalConstants.CHANGE_IMAGE_CAR

class CarImageAdapter(
        var context: Context,
        var data: MutableList<Uri>,
        var user:User,
        private val changeImageListener: ChangeImageListener
) : RecyclerView.Adapter<CarImageAdapter.CarImageViewHolder>() {

    var selectedPosition: Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarImageViewHolder {
        updatePosition()
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemProfileImageBinding.inflate(inflater, parent, false)
        return CarImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CarImageViewHolder, position: Int) {
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
            changeImageListener.changeImage(CHANGE_IMAGE_CAR,data[position])
            selectedPosition = position
            notifyDataSetChanged()
        }

    }

    fun updatePosition() {
        data.forEachIndexed { index, element ->
            if ( element.toString() == user.carName) {
                selectedPosition = index
            }
        }
    }

    override fun getItemCount(): Int = data.size

    class CarImageViewHolder(val binding: ItemProfileImageBinding) : RecyclerView.ViewHolder(binding.root)
}
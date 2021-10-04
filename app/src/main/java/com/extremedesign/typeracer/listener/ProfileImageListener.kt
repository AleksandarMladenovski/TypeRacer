package com.extremedesign.typeracer.listener

import com.extremedesign.typeracer.model.ProfileImage

interface ProfileImageListener {
    fun getImages(images: List<ProfileImage?>?)
}
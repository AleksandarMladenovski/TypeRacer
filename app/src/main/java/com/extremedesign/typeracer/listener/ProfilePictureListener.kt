package com.extremedesign.typeracer.listener

import android.graphics.drawable.Drawable

interface ProfilePictureListener {
    fun onPictureChosen(drawable: Drawable?, name: String?)
}
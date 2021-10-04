package com.extremedesign.typeracer.listener

import android.graphics.drawable.Drawable

interface ChangeImageListener {
    fun changeImage(drawable: Drawable?, position: Int)
    fun applyChanges(name: String?)
}

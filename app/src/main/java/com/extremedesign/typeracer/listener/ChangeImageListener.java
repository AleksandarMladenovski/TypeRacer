package com.extremedesign.typeracer.listener;

import android.graphics.drawable.Drawable;

public interface ChangeImageListener {
    void changeImage(Drawable drawable,int position);
    void applyChanges(String name);
}

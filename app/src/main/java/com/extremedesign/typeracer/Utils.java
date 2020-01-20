package com.extremedesign.typeracer;

import android.text.TextUtils;
import android.util.Patterns;

public class Utils {
    public static boolean isEmailValid(String target) {
        if(target==null){
            return true;
        }
        return (TextUtils.isEmpty(target) || !Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
}

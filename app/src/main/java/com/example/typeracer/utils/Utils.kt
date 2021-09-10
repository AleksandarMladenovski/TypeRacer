package com.example.typeracer.utils

import android.text.TextUtils
import android.util.Patterns

class Utils {
    companion object {
        const val RC_SIGN_IN = 123
        fun isEmailValid(target: String?): Boolean {
            return if (target == null) {
                false
            } else !(TextUtils.isEmpty(target) || !Patterns.EMAIL_ADDRESS.matcher(target).matches())
        }
    }
}
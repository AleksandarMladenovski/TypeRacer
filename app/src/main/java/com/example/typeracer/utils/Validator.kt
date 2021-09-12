package com.example.typeracer.utils

class Validator {
    companion object{
        fun isEmailValid(email: String) : Boolean{
            return email.isNotEmpty() && Utils.isEmailValid(email)
        }
        fun isPasswordValid(password: String) : Boolean{
            return password.isNotEmpty() && password.length>6
        }
    }
}
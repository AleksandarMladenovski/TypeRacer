package com.example.typeracer.persistance

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys

class PreferencesTypeRacer(context: Context) {
    private val encryptedPreferences: SharedPreferences = EncryptedSharedPreferences.create(
        "encrypted_preferences",
        MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
        context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    fun saveString(key: String, content: String) =
        encryptedPreferences.edit().putString(key, content).apply()

    fun saveBoolean(key: String, value: Boolean) =
        encryptedPreferences.edit().putBoolean(key, value).apply()

    fun getString(key: String): String? = encryptedPreferences.getString(key, null)

    fun getBoolean(key: String): Boolean = encryptedPreferences.getBoolean(key, false)

    fun removeString(key: String) = encryptedPreferences.edit().remove(key).apply()

}

fun getListSharedPreferences(preference: SharedPreferences): MutableList<String> {
    val allValues = mutableListOf<String>()
    val allEntries: Map<String, *> = preference.all
    for ((key, value) in allEntries) {
        allValues.plusAssign(value.toString())
    }
    return allValues
}

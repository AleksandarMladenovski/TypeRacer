package com.example.typeracer.data_repository.model

import android.net.Uri

data class TypeRacerImages(
    val cars: MutableList<Uri>,
    val profiles: MutableList<Uri>
)
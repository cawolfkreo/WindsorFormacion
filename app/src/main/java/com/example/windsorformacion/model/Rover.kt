package com.example.windsorformacion.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Rover (
    var nombre: String,
    var posicion: Int
) : Parcelable
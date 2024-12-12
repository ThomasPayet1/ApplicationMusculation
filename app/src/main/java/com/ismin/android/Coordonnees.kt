package com.ismin.android

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Coordonnees(
    @SerializedName("lon") val lon: Double,
    @SerializedName("lat") val lat: Double
): Serializable {
    operator fun get(s: Int): Double {
        return if (s == 0) lat else lon
    }
}

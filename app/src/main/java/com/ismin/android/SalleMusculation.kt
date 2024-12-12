package com.ismin.android

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class SalleMusculation(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("addr") val addr: String,
    @SerializedName("dateCreation") val dateCreation: String,
    @SerializedName("comName") val comName: String,
    @SerializedName("equipTypeName") val equipTypeName: String,
    @SerializedName("equipTypeFamille") val equipTypeFamille: String,
    @SerializedName("coordonnees") val coordonnees: Coordonnees,
    @SerializedName("equipApsName") val equipApsname: List<String>,
    @SerializedName("regName") val regName: String,
    @SerializedName("favoris") var favoris: Boolean = false,
) : Serializable
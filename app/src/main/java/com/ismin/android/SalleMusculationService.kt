package com.ismin.android

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.POST

interface SalleMusculationService {
    @GET("/SalleMusculation")
    fun getAllSalleMusculation(): Call<List<SalleMusculation>>
}
package com.example.retrofitcrudapp

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://66240d4504457d4aaf9b8530.mockapi.io/api/"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val instance: AlumnoApi by lazy {
        retrofit.create(AlumnoApi::class.java)
    }

    val profesorApi: ProfesorApi by lazy {
        retrofit.create(ProfesorApi::class.java)
    }
}

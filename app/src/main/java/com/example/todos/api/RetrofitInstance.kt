package com.example.todos.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitInstance {
    private
    const val BASE_URL = "https://dummyjson.com/"
    val api: RetrofitApi by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create(RetrofitApi::class.java)
    }
}
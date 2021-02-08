package com.example.githubuserfinderandroid.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl(Env.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    val apiService: ApiService = getRetrofit().create(ApiService::class.java)
}
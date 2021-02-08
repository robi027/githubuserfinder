package com.example.githubuserfinderandroid.data.api

import com.example.githubuserfinder.data.model.BaseResponse
import com.example.githubuserfinder.data.model.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface ApiService {

    @GET("/users")
    @Headers("Authorization: Basic cm9iaS10YzpodHRwczovL2FwaS5naXRodWIuY29t")
    fun getUsers(@QueryMap query: Map<String, String>): Call<List<User>>

    @GET("/search/users")
    fun findUser(@QueryMap query: Map<String, String>): Call<BaseResponse<List<User>>>
}
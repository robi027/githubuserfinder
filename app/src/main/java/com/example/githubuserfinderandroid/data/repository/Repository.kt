package com.example.githubuserfinderandroid.data.repository

import com.example.githubuserfinder.data.model.BaseResponse
import com.example.githubuserfinder.data.model.User
import com.example.githubuserfinderandroid.data.api.ApiService
import com.example.githubuserfinderandroid.data.api.Param
import com.example.githubuserfinderandroid.data.api.RetrofitBuilder
import retrofit2.Callback

class Repository(private val retrofitBuilder: RetrofitBuilder) {

    fun getUsers(lastId: String, callback: Callback<List<User>>) {
        retrofitBuilder.apiService.getUsers(Param.getUsers(lastId)).enqueue(callback)
    }

    fun findUsers(query: String, page: String, callback: Callback<BaseResponse<List<User>>>) {
        retrofitBuilder.apiService.findUser(Param.findUser(query, page)).enqueue(callback)
    }
}
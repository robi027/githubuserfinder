package com.example.githubuserfinderandroid.presenter

import com.example.githubuserfinder.data.model.BaseResponse
import com.example.githubuserfinder.data.model.User
import com.example.githubuserfinderandroid.data.api.RetrofitBuilder
import com.example.githubuserfinderandroid.data.repository.Repository
import com.example.githubuserfinderandroid.view.MainView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserPresenter(private val mainView: MainView) {
    private val retrofitBuilder: RetrofitBuilder = RetrofitBuilder
    private var lastId: String = ""
    private var page: Int = 1

    fun getUsers(loadMore: Boolean) {
        mainView.onLoadingGetUsers(loadMore)

        if (!loadMore) lastId = ""

        val repo = Repository(retrofitBuilder)
        repo.getUsers(lastId, object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                if (response.isSuccessful) {
                    response.body()?.let { users ->
                        if (users.isNotEmpty()) lastId = "${users.last().id}"
                        mainView.onSuccessGetUsers(users, loadMore)
                    }
                } else {
                    mainView.onFailureGetUsers(response.message() ?: "something_wrong", loadMore)
                }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                mainView.onFailureGetUsers("something_wrong", loadMore)
            }
        })
    }

    fun findUser(query: String, loadMore: Boolean) {
        mainView.onLoadingGetUsers(loadMore)

        if (!loadMore) page = 1 else page++

        val repo = Repository(retrofitBuilder)
        repo.findUsers(query, "$page", object : Callback<BaseResponse<List<User>>> {
            override fun onResponse(
                call: Call<BaseResponse<List<User>>>,
                response: Response<BaseResponse<List<User>>>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let { users ->
                        mainView.onSuccessFindUser(users.data, loadMore)
                    }
                } else {
                    if (page > 1) page--
                    mainView.onFailureFindUser(response.message() ?: "something_wrong", loadMore)
                }
            }

            override fun onFailure(call: Call<BaseResponse<List<User>>>, t: Throwable) {
                if (page > 1) page--
                mainView.onFailureFindUser("something_wrong", loadMore)
            }
        })
    }
}
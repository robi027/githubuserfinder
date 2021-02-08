package com.example.githubuserfinderandroid.view

import com.example.githubuserfinder.data.model.User

interface MainView {
    fun onLoadingGetUsers(loadMore: Boolean)
    fun onSuccessGetUsers(users: List<User>, loadMore: Boolean)
    fun onFailureGetUsers(message: String, loadMore: Boolean)

    fun onLoadingFindUser(loadMore: Boolean)
    fun onSuccessFindUser(users: List<User>, loadMore: Boolean)
    fun onFailureFindUser(message: String, loadMore: Boolean)
}
package com.example.githubuserfinderandroid.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserfinder.data.model.User
import com.example.githubuserfinderandroid.R
import com.example.githubuserfinderandroid.presenter.UserPresenter
import com.example.githubuserfinderandroid.ui.adapter.UserAdapter
import com.example.githubuserfinderandroid.utils.controllerLoadingLayout
import com.example.githubuserfinderandroid.utils.hideKeyboard
import com.example.githubuserfinderandroid.utils.listenerScroll
import com.example.githubuserfinderandroid.utils.listenerText
import com.example.githubuserfinderandroid.view.MainView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.failure.*

class MainActivity : AppCompatActivity(), MainView {

    private lateinit var userAdapter: UserAdapter
    private lateinit var userPresenter: UserPresenter
    private var listLoading: MutableList<View> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupUI()
        setupPresenter()
    }

    private fun setupUI() {
        val llm = LinearLayoutManager(this)
        rvUsers.layoutManager = llm
        rvUsers.listenerScroll(llm, ::loadMore)
        rvUsers.hideKeyboard(this)
        rvUsers.addItemDecoration(
            DividerItemDecoration(
                rvUsers.context, llm.orientation
            )
        )
        userAdapter = UserAdapter(mutableListOf())
        rvUsers.adapter = userAdapter
        etSearch.listenerText(::findUser)
        btRefresh.setOnClickListener { refresh() }

        listLoading.add(clLoadingAll)
        listLoading.add(clFailure)
    }

    private fun setupPresenter() {
        userPresenter = UserPresenter(this)
        userPresenter.getUsers(false)
    }

    private fun refresh() {
        if (etSearch.text.isEmpty()) {
            userPresenter.getUsers(false)
        } else {
            userPresenter.findUser(etSearch.text.toString(), false)
        }
    }

    private fun loadMore() {
        if (etSearch.text.isEmpty()) {
            userPresenter.getUsers(true)
        } else {
            userPresenter.findUser(etSearch.text.toString(), true)
        }

    }

    private fun findUser(value: String) {
        if (value.isEmpty()) {
            Handler(Looper.getMainLooper()).post {
                userPresenter.getUsers(false)
            }
        } else {
            Handler(Looper.getMainLooper()).post {
                userPresenter.findUser(value, false)
            }
        }

    }

    override fun onLoadingGetUsers(loadMore: Boolean) {
        if (!loadMore) controllerLoadingLayout(listLoading, clLoadingAll)
    }

    override fun onSuccessGetUsers(users: List<User>, loadMore: Boolean) {
        userAdapter.addUsers(users, loadMore)
        controllerLoadingLayout(listLoading, null)
    }

    override fun onFailureGetUsers(message: String, loadMore: Boolean) {
        if (loadMore) {
            controllerLoadingLayout(listLoading, null)
        } else {
            setLayoutMessageError(R.string.something_wrong)
            userAdapter.addUsers(listOf(), false)
        }
    }

    override fun onLoadingFindUser(loadMore: Boolean) {
        if (!loadMore) controllerLoadingLayout(listLoading, clLoadingAll)
    }

    override fun onSuccessFindUser(users: List<User>, loadMore: Boolean) {
        if (users.isNotEmpty()) {
            userAdapter.addUsers(users, loadMore)
            controllerLoadingLayout(listLoading, null)
        } else {
            userAdapter.addUsers(listOf(), loadMore)
            setLayoutMessageError(R.string.not_match)
        }
    }

    override fun onFailureFindUser(message: String, loadMore: Boolean) {
        if (loadMore) {
            controllerLoadingLayout(listLoading, null)
        } else {
            setLayoutMessageError(R.string.something_wrong)
            userAdapter.addUsers(listOf(), false)
        }
    }

    private fun setLayoutMessageError(message: Int) {
        tvErrMessage.text = resources.getString(message)
        controllerLoadingLayout(listLoading, clFailure)
    }
}
package com.example.githubuserfinder.data.model

import com.google.gson.annotations.SerializedName

data class BaseResponse<T>(
    @SerializedName("total_count")
    val totalCount: Int,
    @SerializedName("incomplete_results")
    val incompleteResults: Boolean,
    @SerializedName("items")
    val data: T
)
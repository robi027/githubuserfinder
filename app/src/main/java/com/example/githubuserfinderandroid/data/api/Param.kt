package com.example.githubuserfinderandroid.data.api

class Param {
    companion object {
        fun getUsers(lastId: String): Map<String, String> {
            val data: MutableMap<String, String> = mutableMapOf()
            data.put("since", lastId)
            data.put("per_page", "30")
            return data
        }

        fun findUser(query: String, page: String): Map<String, String> {
            val data: MutableMap<String, String> = mutableMapOf()
            data.put("q", query)
            data.put("page", page)
            data.put("per_page", "30")
            return data
        }
    }
}
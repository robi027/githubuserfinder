package com.example.githubuserfinderandroid.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuserfinder.data.model.User
import com.example.githubuserfinderandroid.R
import kotlinx.android.synthetic.main.item_user.view.*

class UserAdapter(private var users: MutableList<User?>) :
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return if (viewType == 0) {
            UserViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
            )
        } else {
            UserViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_loading, parent, false)
            )
        }
    }

    override fun getItemCount(): Int = users.size

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        if (users.size - 1 != position) {
            holder.bind(users[position]!!)
        }
    }

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(user: User) {
            itemView.apply {
                tvName.text = user.login
                Glide.with(itemView).load(user.avatarUrl).placeholder(R.drawable.ic_profile)
                    .error(R.drawable.ic_profile)
                    .into(ivAvatar)
            }
        }
    }

    fun addUsers(users: List<User>, loadMore: Boolean) {
        if (!loadMore) this.users.clear()
        this.users.addAll(users)
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int = if (users.size - 1 != position) 0 else 1

}
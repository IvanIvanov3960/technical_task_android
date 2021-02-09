package com.technicaltask.usersapp

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.technicaltask.usersapp.network.User

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<User>?) {
    val adapter = recyclerView.adapter as UserListAdapter
    adapter.submitList(data)
}
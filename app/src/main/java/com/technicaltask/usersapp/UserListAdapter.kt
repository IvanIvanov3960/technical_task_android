package com.technicaltask.usersapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.technicaltask.usersapp.databinding.SingleItemBinding
import com.technicaltask.usersapp.network.User

class UserListAdapter(val onClickListener: OnLongClickListener) : ListAdapter<User, UserListAdapter.UserViewHolder>(DiffCallback) {

    companion object DiffCallback : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.id == newItem.id
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListAdapter.UserViewHolder {
        return UserViewHolder(SingleItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: UserListAdapter.UserViewHolder, position: Int) {
        val singleUser = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(singleUser)
        }
        holder.bind(singleUser)
    }

    class UserViewHolder(private var binding: SingleItemBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            binding.user = user
            binding.executePendingBindings()
        }
    }

    class OnLongClickListener(val clickListener: (user: User) -> Unit) {
        fun onClick(user: User) {
             clickListener(user)
        }
    }
}

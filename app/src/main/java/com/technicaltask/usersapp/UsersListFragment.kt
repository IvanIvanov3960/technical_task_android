package com.technicaltask.usersapp

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.technicaltask.usersapp.databinding.UsersListFragmentBinding

class UsersListFragment : Fragment() {

    companion object {
        fun newInstance() = UsersListFragment()
    }

    private val viewModel: UsersListViewModel by lazy {
        ViewModelProvider(this).get(UsersListViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = UsersListFragmentBinding.inflate(inflater)

        binding.lifecycleOwner = this

        binding.users = viewModel

        val listUsers = viewModel.users


        binding.recyclerView.adapter = UserListAdapter(UserListAdapter.OnClickListener {
            viewModel.displayUserDetails(it)
        })
        return binding.root
    }

}
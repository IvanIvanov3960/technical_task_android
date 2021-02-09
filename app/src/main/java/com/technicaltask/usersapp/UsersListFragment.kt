package com.technicaltask.usersapp

import android.R
import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
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

        val fab = binding.fab as FloatingActionButton
        fab.setOnClickListener { view ->
            
        }


        binding.recyclerView.adapter = UserListAdapter(UserListAdapter.OnLongClickListener {
            viewModel.displayUserDetails(it)
            viewModel.deleteUser(it)
            Toast.makeText(context, "Long click", Toast.LENGTH_SHORT).show()
        })
        return binding.root
    }

    fun onCreateDialog(){

    }

}
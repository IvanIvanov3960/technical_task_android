package com.technicaltask.usersapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.technicaltask.usersapp.network.User
import com.technicaltask.usersapp.network.UserApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.awaitResponse
import java.lang.Exception

class UsersListViewModel : ViewModel() {

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main )

    private val _navigateToSelectedProperty = MutableLiveData<User>()

    val navigateToSelectedProperty: LiveData<User>
        get() = _navigateToSelectedProperty

    fun displayUserDetails(user: User) {
        _navigateToSelectedProperty.value = user
    }

    fun deleteUser(user: User) {
        coroutineScope.launch {
            var getPropertiesDeferred = UserApi.retrofitService.deleteUser(user.id, R.string.token.toString())
            val res = getPropertiesDeferred.await()
            val a = 1
        }
    }

    init {
        getUsers()
    }

    private val _status = MutableLiveData<String>()

    val response: LiveData<String>
        get() = _status

    private val _users = MutableLiveData<List<User>>()

    val users: LiveData<List<User>>
        get() = _users

    private fun getUsers() {
        coroutineScope.launch {
            var getPropertiesDeferred = UserApi.retrofitService.getUsers()
            try {
                var listResult = getPropertiesDeferred.await()
                _status.value = "Success: ${listResult.data.size} Number of users retrieved"
                if (listResult.data.size > 0) {
                    getLastPageUsers(listResult.meta.pagination.pages)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun getLastPageUsers(page: Long) {
        coroutineScope.launch {
            var getPropertiesDeferred = UserApi.retrofitService.getLastPage(page)
            try {
                var listResult = getPropertiesDeferred.await()
                if (listResult.data.size > 0) {
                    _users.value = listResult.data
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
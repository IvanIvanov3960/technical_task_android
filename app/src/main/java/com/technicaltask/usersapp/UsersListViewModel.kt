package com.technicaltask.usersapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.technicaltask.usersapp.network.User
import com.technicaltask.usersapp.network.UserApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class UsersListViewModel : ViewModel() {

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main )

    private val _user = MutableLiveData<User>()

    val user: LiveData<User>
        get() = this._user

    fun displayUserDetails(user: User) {
        this._user.value = user
    }

    fun deleteUser(user: User) {
        coroutineScope.launch {
            var getPropertiesDeferred = UserApi.retrofitService.deleteUser(user.id, R.string.token.toString())
            getPropertiesDeferred.enqueue(object : Callback<ResponseBody> {
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {

                }
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    if (response.code() == 200) {
                        
//                        Toast.makeText(, "Long click", Toast.LENGTH_SHORT).show()
//                        Snackbar.make(viewModelScope, response.code().toString(), Snackbar.LENGTH_SHORT).show
                    }
                }
            })
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
        get() = this._users

    private fun getUsers() {
        coroutineScope.launch {
            var getPropertiesDeferred = UserApi.retrofitService.getUsers()
            try {
                var listResult = getPropertiesDeferred.await()
                _status.value = "Success: ${listResult.data.size} Number of users retrieved"
                if (listResult.data.size > 0) {
                    getLastPageUsers(listResult.meta?.pagination!!.pages)
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
                    this@UsersListViewModel._users.value = listResult.data
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
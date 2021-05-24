package com.technicaltask.usersapp.network

import com.google.gson.JsonObject
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Json
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*

private const val BASE_URL = "https://gorest.co.in/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()

interface UserApiService {
    @GET("public-api/users")
    fun getUsers():
            Deferred<Request>

    @GET("public-api/users")
    fun getLastPage(@Query("page")page: Long):
            Deferred<Request>

    @DELETE("public-api/users/{id}")
    fun deleteUser(@Path("id") id: Long, @Header("Authorization") authHeader: String): Call<ResponseBody>
}

object UserApi {
    val retrofitService : UserApiService by lazy {
        retrofit.create(UserApiService::class.java)
    }
}
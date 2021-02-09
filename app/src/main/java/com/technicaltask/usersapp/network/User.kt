package com.technicaltask.usersapp.network

data class Request (
    val code: Long,
    val meta: Meta,
    val data: List<User>
)

data class User (
    val id: Long,
    val name: String,
    val email: String,
    val gender: Gender,
    val status: Status,
    val createdAt: String? = null,
    val updatedAt: String? = null
)

enum class Gender {
    Female,
    Male
}

enum class Status {
    Active,
    Inactive
}

data class Meta (
    val pagination: Pagination
)

data class Pagination (
    val total: Long,
    val pages: Long,
    val page: Long,
    val limit: Long
)
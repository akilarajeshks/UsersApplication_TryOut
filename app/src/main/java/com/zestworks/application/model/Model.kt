package com.zestworks.application.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity(tableName = "users_table")
data class Users(
    @PrimaryKey(autoGenerate = true)
    val key: Int,
    @SerializedName("data")
    val `data`: List<Data>,
    val limit: Int,
    val page: Int,
    val total: Int
)

data class Data(
    val firstName: String,
    @PrimaryKey
    val id: Int,
    val image: String,
    val lastName: String,
    val nameTitle: String
)

@Entity(tableName = "user_info_table")
data class UserInfo(
    val cell: String,
    val dob: String,
    val email: String,
    val firstName: String,
    val gender: String,
    @PrimaryKey
    val id: Int,
    val image: String,
    val lastName: String,
    val location: Location,
    val nameTitle: String,
    val password: String,
    val phone: String,
    val username: String
)

data class Location(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val city: String,
    val postcode: Int,
    val state: String,
    val street: String,
    val timezone: String
)
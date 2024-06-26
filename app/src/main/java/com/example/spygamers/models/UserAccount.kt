package com.example.spygamers.models

import com.google.gson.annotations.SerializedName
import java.util.Date

data class UserAccount(
    @SerializedName("id")
    val id : Int,
    @SerializedName("username")
    val username: String,

    @SerializedName("date_created")
    val dateCreated: Date,

    @SerializedName("timezone_code")
    val timezoneCode: String
)
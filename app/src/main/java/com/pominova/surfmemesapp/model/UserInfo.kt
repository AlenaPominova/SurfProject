package com.pominova.surfmemesapp.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class UserInfo {
    @SerializedName("id")
    @Expose
    private val id: Int = 0
    @SerializedName("username")
    @Expose
    private val username: String? = null
    @SerializedName("firstName")
    @Expose
    private val firstName: String? = null
    @SerializedName("lastName")
    @Expose
    private val lastName: String? = null
    @SerializedName("userDescription")
    @Expose
    private val userDescription: String? = null
}

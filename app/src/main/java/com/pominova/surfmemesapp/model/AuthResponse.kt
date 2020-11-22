package com.pominova.surfmemesapp.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class AuthResponse {
    @SerializedName("accessToken")
    @Expose
    private val accessToken: String? = null
    @SerializedName("userInfo")
    @Expose
    private val userInfo: UserInfo? = null
}

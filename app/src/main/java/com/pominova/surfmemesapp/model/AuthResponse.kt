package com.pominova.surfmemesapp.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class AuthResponse {
    @SerializedName("accessToken")
    @Expose
    var accessToken: String? = null
    @SerializedName("userInfo")
    @Expose
    var userInfo: UserInfo? = null
}

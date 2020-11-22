package com.pominova.surfmemesapp.service

import com.pominova.surfmemesapp.model.AuthRequest
import com.pominova.surfmemesapp.model.AuthResponse

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface JSONPlaceHolderApi {
    @POST("/auth/login")
    fun login(@Body authBody: AuthRequest): Call<AuthResponse>
}

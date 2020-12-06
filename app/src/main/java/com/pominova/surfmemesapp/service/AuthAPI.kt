package com.pominova.surfmemesapp.service

import com.pominova.surfmemesapp.model.AuthRequest
import com.pominova.surfmemesapp.model.AuthResponse
import com.pominova.surfmemesapp.util.AppConstant.AUTH_URL

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthAPI {

    @POST(AUTH_URL)
    fun login(@Body authBody: AuthRequest): Call<AuthResponse>
}

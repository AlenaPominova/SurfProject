package com.pominova.surfmemesapp.service

import com.pominova.surfmemesapp.util.AppConstant.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkService private constructor() {
    companion object {
        private var instance: NetworkService? = null

        fun getInstance(): NetworkService {
            if (instance == null) {
                instance = NetworkService()
            }
            return instance!!
        }
    }

    private val retrofit: Retrofit

    val authAPI: AuthAPI
        get() = retrofit.create(AuthAPI::class.java)

    init {
        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}

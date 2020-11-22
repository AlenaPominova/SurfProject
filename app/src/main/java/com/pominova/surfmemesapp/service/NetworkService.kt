package com.pominova.surfmemesapp.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkService private constructor() {
    private val BASE_URL =
        "https://virtserver.swaggerhub.com/AndroidSchool/SurfAndroidSchool/1.0.0"
    private var networkServiceInstance: NetworkService? = null
    private val retrofit: Retrofit

    var instance: NetworkService? = null
        get() {
            if (networkServiceInstance == null) {
                networkServiceInstance = NetworkService()
            }
            return networkServiceInstance
        }

    val jsonApi: JSONPlaceHolderApi
        get() = retrofit.create(JSONPlaceHolderApi::class.java)

    init {
        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}

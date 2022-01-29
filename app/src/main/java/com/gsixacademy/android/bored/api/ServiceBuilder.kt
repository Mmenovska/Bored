package com.gsixacademy.android.bored.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceBuilder {

    val client = OkHttpClient.Builder().build()
    val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl("https://www.boredapi.com")
        .client(client)
        .build()
    fun <T> buildService (service : Class<T>):T{
        return retrofit.create(service)
    }
}
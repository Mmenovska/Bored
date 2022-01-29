package com.gsixacademy.android.bored.api

import com.gsixacademy.android.bored.models.ActivityResponse
import retrofit2.Call
import retrofit2.http.GET

interface BoredAPI {

    @GET ("/api/activity/")
    fun getRandomActivity () : Call<ActivityResponse>
}
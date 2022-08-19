package com.weatherapp.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    @GET("onecall")
    suspend fun getWeather(@Query("lat") lat : Double, @Query("lon") lon : Double, @Query("appid") appid : String) : List<Any>

}
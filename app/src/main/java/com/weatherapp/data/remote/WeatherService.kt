package com.weatherapp.data.remote

import com.weatherapp.data.remote.dto.ServiceResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    @GET("forecast")
    suspend fun getWeather(@Query("lat") lat : Double, @Query("lon") lon : Double, @Query("appid") appid : String) : ServiceResponse

}
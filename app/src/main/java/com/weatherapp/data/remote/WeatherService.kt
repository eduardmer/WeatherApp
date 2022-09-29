package com.weatherapp.data.remote

import com.weatherapp.data.remote.dto.CurrentWeatherResponse
import com.weatherapp.data.remote.dto.TodayWeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    @GET("forecast")
    suspend fun getTodayWeather(@Query("lat") lat : Double, @Query("lon") lon : Double, @Query("appid") appid : String) : TodayWeatherResponse

    @GET("weather")
    suspend fun getCurrentWeather(@Query("lat") lat: Double, @Query("lon") lon: Double, @Query("appid") appid: String) : CurrentWeatherResponse

}
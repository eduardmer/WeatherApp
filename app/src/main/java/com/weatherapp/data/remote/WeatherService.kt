package com.weatherapp.data.remote

import com.weatherapp.data.remote.model.CurrentWeatherResponse
import com.weatherapp.data.remote.model.TodayWeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    @GET("forecast")
    suspend fun getTodayWeather(@Query("lat") lat : Double, @Query("lon") lng : Double, @Query("appid") appid : String) : TodayWeatherResponse

    @GET("weather")
    suspend fun getCurrentWeather(@Query("lat") lat: Double, @Query("lon") lng: Double, @Query("appid") appid: String) : CurrentWeatherResponse

}
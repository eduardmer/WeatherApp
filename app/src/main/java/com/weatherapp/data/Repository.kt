package com.weatherapp.data

import com.weatherapp.data.remote.WeatherService
import com.weatherapp.data.remote.dto.ServiceResponse
import javax.inject.Inject

class Repository @Inject constructor(val service: WeatherService) {

    suspend fun getWeather(lat : Double = 41.3275, lon : Double = 19.8187, appid : String): ServiceResponse {
        return service.getWeather(lat, lon, appid)
    }

}
package com.weatherapp.data.local.model

data class CityDto(val city: String,
                   val lng: Double,
                   val lat: Double) {

    override fun toString(): String {
        return city
    }

}

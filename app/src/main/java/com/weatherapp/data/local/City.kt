package com.weatherapp.data.local

data class City(val id: Long,
                val city: String,
                val lng: Double,
                val lat: Double) {

    override fun toString(): String {
        return city
    }

}

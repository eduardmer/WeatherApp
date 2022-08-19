package com.weatherapp.data.local.entities

import androidx.room.Entity

@Entity
data class Weather(
    val id : Int,
    val date : String
)

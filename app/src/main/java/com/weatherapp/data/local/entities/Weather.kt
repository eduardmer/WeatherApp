package com.weatherapp.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Weather(
    @PrimaryKey
    val id : Int,
    val date : String
)

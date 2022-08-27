package com.weatherapp.utils

fun Double?.toCelsius() =
    if (this != null)
        "%.1f".format(this - 273.15)
    else "0.0"
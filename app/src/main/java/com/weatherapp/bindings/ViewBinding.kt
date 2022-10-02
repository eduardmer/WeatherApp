package com.weatherapp.bindings

import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import coil.load
import com.weatherapp.utils.DATE_FORMAT_24H
import com.weatherapp.utils.ICON_BASE_URL
import com.weatherapp.R
import com.weatherapp.utils.toCelsius
import java.text.SimpleDateFormat
import java.util.*

@BindingAdapter("sunrise", "sunset")
fun ConstraintLayout.setBackground(sunriseTime: Long, sunsetTime: Long) {
    setBackgroundResource(if(System.currentTimeMillis() < sunriseTime * 1000 || System.currentTimeMillis() > sunsetTime * 1000) R.drawable.bg_night else R.drawable.bg_day)
}

@BindingAdapter("date")
fun TextView.setDate(date: Long) {
    text = SimpleDateFormat(DATE_FORMAT_24H, Locale.getDefault()).format(Date(date * 1000))
}

@BindingAdapter("text","date24H")
fun TextView.setTextAndDate(text: String, date: Long) {
    this.text = text + SimpleDateFormat(DATE_FORMAT_24H, Locale.getDefault()).format(Date(date * 1000))
}

@BindingAdapter("celsius")
fun TextView.setTemp(temp: Double) {
    text = temp.toCelsius()
}

@BindingAdapter("image")
fun ImageView.setImage(url: String) {
    this.load("$ICON_BASE_URL$url.png") {
        placeholder(R.drawable.ic_image)
    }
}
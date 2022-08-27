package com.weatherapp.bindings

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.weatherapp.DATE_FORMAT_24H
import com.weatherapp.ICON_BASE_URL
import com.weatherapp.utils.toCelsius
import java.text.SimpleDateFormat
import java.util.*

object ViewBinding {

    @JvmStatic
    @BindingAdapter("date")
    fun setDate(textView: TextView, date: Long) {
        textView.text = SimpleDateFormat(DATE_FORMAT_24H, Locale.getDefault()).format(Date(date * 1000))
    }

    @JvmStatic
    @BindingAdapter("celsius")
    fun setTemp(textView: TextView, temp: Double) {
        textView.text = temp.toCelsius()
    }

    @JvmStatic
    @BindingAdapter("image")
    fun setImage(imageView: ImageView, url: String) {
        Glide.with(imageView).load("$ICON_BASE_URL$url.png").into(imageView)
    }

}
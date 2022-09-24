package com.weatherapp.ui

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.TextView
import com.weatherapp.R
import com.weatherapp.data.local.City
import java.util.ArrayList

class CityAdapter(private val mContext: Context, val resource: Int, val cities: ArrayList<City>) :
    ArrayAdapter<City>(mContext, resource, cities) {

    private val filteredCities = ArrayList<City>()

    init {
        filteredCities.addAll(cities)
    }

    override fun getItem(position: Int): City {
        return filteredCities.get(position)
    }

    override fun getItemId(position: Int): Long {
        return filteredCities.get(position).id
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var myView = convertView
        if (myView == null)
            myView = (mContext as Activity).layoutInflater.inflate(resource, parent, false)
        val cityTextView = myView?.findViewById<TextView>(R.id.city)
        val coordinates = myView?.findViewById<TextView>(R.id.coordinates)
        val city = getItem(position)
        cityTextView?.text = city.city
        coordinates?.text = "${city.lng}, ${city.lat}"
        return myView!!
    }

    fun updateData(cities: ArrayList<City>) {
        this.cities.clear()
        this.filteredCities.clear()
        this.cities.addAll(cities)
        this.filteredCities.addAll(this.cities)
        notifyDataSetChanged()
    }

    override fun getCount(): Int {
        return filteredCities.size
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(p0: CharSequence?): FilterResults {
                val results = FilterResults()
                val suggestions = ArrayList<City>()
                if (p0.isNullOrEmpty())
                    suggestions.addAll(cities)
                else {
                    for (city in cities) {
                        if (city.city.contains(p0!!, true))
                            suggestions.add(city)
                    }
                }
                results.values = suggestions
                results.count = suggestions.size
                return results
            }

            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                filteredCities.clear()
                for (result in p1?.values as List<*>) {
                    if (result is City)
                        filteredCities.add(result)
                }
                notifyDataSetChanged()
            }

        }
    }

}
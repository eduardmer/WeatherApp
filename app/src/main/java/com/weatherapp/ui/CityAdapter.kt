package com.weatherapp.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import androidx.databinding.DataBindingUtil
import com.weatherapp.data.local.City
import com.weatherapp.databinding.CityItemBinding
import java.util.ArrayList

class CityAdapter(mContext: Context, private val resource: Int, val cities: MutableList<City>) :
    ArrayAdapter<City>(mContext, resource, cities) {

    private val filteredCities = mutableListOf<City>()

    init {
        filteredCities.addAll(cities)
    }

    override fun getItem(position: Int): City {
        return filteredCities[position]
    }

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val binding: CityItemBinding? = if (view == null) {
            DataBindingUtil.inflate(LayoutInflater.from(context), resource, parent, false)
        } else {
            DataBindingUtil.getBinding(view)
        }
        binding?.data = getItem(position)
        return binding?.root!!
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
                        if (city.city.contains(p0, true))
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
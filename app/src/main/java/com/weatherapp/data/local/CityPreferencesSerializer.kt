package com.weatherapp.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import com.weatherapp.CityPreferences
import java.io.InputStream
import java.io.OutputStream

object CityPreferencesSerializer : Serializer<CityPreferences> {

    override val defaultValue: CityPreferences = CityPreferences.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): CityPreferences {
        return CityPreferences.parseFrom(input)
    }

    override suspend fun writeTo(t: CityPreferences, output: OutputStream) {
        t.writeTo(output)
    }

    val Context.cityProtoDataStore: DataStore<CityPreferences> by dataStore(
        fileName = "city_proto.proto",
        serializer = CityPreferencesSerializer
    )

}
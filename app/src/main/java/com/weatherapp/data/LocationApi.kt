package com.weatherapp.data

import android.location.Location
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.tasks.CancellationTokenSource
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class LocationApi @Inject constructor(val locationProvider: FusedLocationProviderClient) {

    suspend fun getLocation(): Location? {
        val cancellationTokenSource = CancellationTokenSource()
        return try {
            locationProvider.getCurrentLocation(LocationRequest.PRIORITY_HIGH_ACCURACY, cancellationTokenSource.token).await(cancellationTokenSource)
        } catch (e: SecurityException) {
            null
        }
    }

}
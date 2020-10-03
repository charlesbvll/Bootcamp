package com.github.charlesbvll.bootcamp

import android.content.Context
import android.location.Criteria
import android.location.LocationManager

private fun buildManagerFromContext(context: Context): LocationManager{
    return context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
}

class AndroidLocationService(locationManager: LocationManager, locationProvider: String?, locationCriteria: Criteria?):
    LocationService {
    private var locationManager = locationManager
    private var locationProvider = locationProvider
    private var locationCriteria = locationCriteria

    constructor(context: Context, provider: String): this(
        buildManagerFromContext(
            context
        ), provider, null)

    constructor(context: Context, criteria: Criteria): this(
        buildManagerFromContext(
            context
        ), null, criteria)

    private fun getLocationProvider(): String? {
        if(locationProvider != null)
            return locationProvider
        return locationManager.getBestProvider(locationCriteria, true)
    }

    override fun getCurrentLocation(): Location? {
        try {
            val loc: android.location.Location = this.locationManager.getLastKnownLocation(getLocationProvider())
                ?: return null
            return Location(loc.latitude, loc.longitude)
        } catch (ex: SecurityException){
            throw ex
        }
    }
}
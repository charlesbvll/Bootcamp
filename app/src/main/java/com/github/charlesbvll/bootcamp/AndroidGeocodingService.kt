package com.github.charlesbvll.bootcamp

import android.content.Context
import android.location.Geocoder
import java.io.IOException


class AndroidGeocodingService(private val geocoder: Geocoder?):
    GeocodingService {

    constructor(context: Context?): this(Geocoder(context))

    @Throws(IOException::class)
    override fun getLocation(address: String): Any {
        return geocoder!!.getFromLocationName(address, 5)
            .stream()
            .filter { addr: android.location.Address -> addr.hasLatitude() && addr.hasLongitude() }
            .map<Any> { addr: android.location.Address ->
                Location(
                    addr.latitude,
                    addr.longitude
                )
            }
            .findFirst()
            .get()
    }

    @Throws(IOException::class)
    override fun getAddress(location: Location): Address? {
        val address =
            geocoder!!.getFromLocation(location.latitude, location.longitude, 1)[0]
        val addressLines: MutableList<String> = ArrayList()
        for (i in 0..address.maxAddressLineIndex) addressLines.add(address.getAddressLine(i))
        return Address(addressLines)
    }
}
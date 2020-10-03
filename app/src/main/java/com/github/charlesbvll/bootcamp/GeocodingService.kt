package com.github.charlesbvll.bootcamp


interface GeocodingService {
    fun getLocation(address: String): Any
    fun getAddress(location: Location): Address?
}
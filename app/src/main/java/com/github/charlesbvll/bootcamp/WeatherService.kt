package com.github.charlesbvll.bootcamp

import com.github.charlesbvll.bootcamp.Forecast

interface WeatherService {
    fun getWeather(location: Location): Forecast?
}
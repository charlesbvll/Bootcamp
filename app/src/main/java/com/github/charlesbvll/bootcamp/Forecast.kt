package com.github.charlesbvll.bootcamp

import androidx.annotation.NonNull

class Forecast(reports: Array<Report?>) {
    var reports = reports

    enum class Day{
        TODAY, TOMORROW, AFTER_TOMORROW
    }

    fun getWeatherReport(@NonNull offset: Day): Report? {
        return reports[offset.ordinal]
    }
}
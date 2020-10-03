package com.github.charlesbvll.bootcamp

class Report(averageTemperature: Double, minimumTemperature: Double, maximalTemperature: Double, weatherType: String, weatherIcon: String) {
    @kotlin.jvm.JvmField
    var averageTemperature = averageTemperature
    @kotlin.jvm.JvmField
    var minimumTemperature = minimumTemperature
    @kotlin.jvm.JvmField
    var maximalTemperature = maximalTemperature
    @kotlin.jvm.JvmField
    var weatherType = weatherType
    @kotlin.jvm.JvmField
    var weatherIcon = weatherIcon
}
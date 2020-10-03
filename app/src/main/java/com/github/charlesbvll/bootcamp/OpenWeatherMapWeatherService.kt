package com.github.charlesbvll.bootcamp

import android.content.Context
import android.util.Log
import androidx.annotation.NonNull
import org.json.JSONException
import org.json.JSONObject
import org.json.JSONTokener
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.URL
import java.nio.charset.StandardCharsets
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection


class OpenWeatherMapWeatherService(apiKey: String):
    WeatherService {
    private val API_ENDPOINT = "https://api.openweathermap.org/data/2.5/onecall"
    private val TEMP_UNIT = "metric"
    private val NO_DATA: Report =
        Report(0.0, 0.0, 0.0, "N/A", "N/A")

    val apiKey = apiKey

    constructor(@NonNull context: Context):this(context.getString(R.string.openweather_api_key))

    fun buildFromApiKey(apiKey: String): WeatherService {
        return OpenWeatherMapWeatherService(
            apiKey
        )
    }

    @Throws(JSONException::class)
    private fun parseReport(report: JSONObject): Report?{
        val weather: JSONObject = report.getJSONArray("weather").getJSONObject(0)
        return Report(
            report.getJSONObject("temp").getDouble("day"),
            report.getJSONObject("temp").getDouble("min"),
            report.getJSONObject("temp").getDouble("max"),

            weather.getString("main"),
            weather.getString("icon")
        )
    }

    @Throws(JSONException::class)
    private fun parseForecast(forecast: JSONObject): Forecast? {
        val daily = forecast.getJSONArray("daily")
        val reports: Array<Report?> =
            arrayOfNulls<Report>(Math.max(3, daily.length()))
        for (i in 0 until Math.max(3, daily.length())) {
            if (i >= daily.length()) reports[i] = NO_DATA else try {
                reports[i] = parseReport(daily.getJSONObject(i))
            } catch (ex: JSONException) {
                Log.e("com.github.charlesbvll.bootcamp.OpenWeatherMapWeatherService", "Error when parsing day $i", ex)
                reports[i] = NO_DATA
            }
        }
        return Forecast(reports)
    }

    @Throws(IOException::class)
    private fun getRawForecast(location: Location): String? {
        val queryUrl = API_ENDPOINT + "?lat=" + location.latitude + "&lon=" + location.longitude +
                "&units=" + TEMP_UNIT + "&exclude=current,minutely,hourly" + "&appid=" + apiKey
        val url = URL(queryUrl)

        var stream: InputStream? = null
        var connection: HttpsURLConnection? = null
        var result: String? = null

        try {
            connection = url.openConnection() as HttpsURLConnection
            connection.readTimeout = 3000
            connection.connectTimeout = 3000
            connection.requestMethod = "GET"

            connection.doInput = true
            connection.connect()

            var responseCode = connection.responseCode
            if (responseCode != HttpsURLConnection.HTTP_OK){
                throw IOException("HTTP error code: " + responseCode)
            }

            stream = connection.inputStream
            if (stream != null){
                val reader = BufferedReader(
                    InputStreamReader(
                        stream,
                        StandardCharsets.UTF_8
                    )
                )
                result = reader.lines().collect(Collectors.joining("\n"))
            }
        } finally {
            stream?.close()
            connection?.disconnect()
        }
        return result
    }

    override fun getWeather(location: Location): Forecast? {
        val forecast: String? = getRawForecast(location)
        try {
            val json: JSONObject = JSONTokener(forecast).nextValue() as JSONObject
            return parseForecast(json)
        } catch (e: JSONException) {
            throw IOException(e)
        }
    }
}
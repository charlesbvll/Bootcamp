package com.github.charlesbvll.bootcamp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val weatherButton: Button = findViewById(R.id.weatherButton)

        weatherButton.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    WeatherActivity::class.java
                )
            )
        }
    }


}

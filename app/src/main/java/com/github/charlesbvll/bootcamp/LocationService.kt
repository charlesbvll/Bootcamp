package com.github.charlesbvll.bootcamp

import android.content.Context
import android.location.Criteria

interface LocationService {
    fun getCurrentLocation(): Location?
}
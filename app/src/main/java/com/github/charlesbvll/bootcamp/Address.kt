package com.github.charlesbvll.bootcamp

import androidx.annotation.NonNull

class Address(addressLines: List<String>) {
    var addressLines = addressLines

    @NonNull
    fun toString(separator: String): String{
        var buffer: StringBuilder = java.lang.StringBuilder()
        for (line in addressLines) buffer.append(line).append(separator)
        return buffer.toString()
    }

    @NonNull
    @Override
    override fun toString(): String{
        return this.toString("\n")
    }
}
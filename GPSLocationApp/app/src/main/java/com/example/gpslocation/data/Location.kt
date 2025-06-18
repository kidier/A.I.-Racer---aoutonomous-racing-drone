package com.example.gpslocation.data

/** Data model representing a saved location. */
data class Location(
    val id: Long = 0,
    val latitude: Double,
    val longitude: Double,
    val timestamp: Long,
    val region: String?
)

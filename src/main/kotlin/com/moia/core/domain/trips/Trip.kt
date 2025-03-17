package com.moia.core.domain.trips

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class Trip(
    val tripId: String,
    val customerId: String,
    val timestamp: Long,
    val origin: String? = null,
    val pickup_stop: String? = null,
    val dropoff_stop: String? = null,
    val destination: String? = null,
    val pickup_interval: Time? = null,
    val dropoff_interval: Time? = null,
    val order_time: Long? = null,
    val service_class_id: String? = null,
    val pickup_time: Long? = null,
    val dropoff_time: Long? = null,
    val state: TripState,
)

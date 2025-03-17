package com.moia.domain.trips

import com.moia.core.domain.trips.TripResults

class TripsResponse() {
    lateinit var tripsResults: TripResults
    var tripStatusCode: Int? = null
}
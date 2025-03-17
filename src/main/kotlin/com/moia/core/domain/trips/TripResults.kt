package com.moia.core.domain.trips

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
class TripResults : ArrayList<Trip>(
)
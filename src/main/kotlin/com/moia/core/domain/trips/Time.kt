package com.moia.core.domain.trips

import com.fasterxml.jackson.annotation.JsonProperty

data class Time(
    @JsonProperty("start")
    // can also be offsetDateTime
    var start: Long,

    @JsonProperty("end")
    var end: Long
)
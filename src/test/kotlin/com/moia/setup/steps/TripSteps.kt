package com.moia.setup.steps

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.moia.core.domain.trips.TripState
import com.moia.domain.trips.TripsResponse
import io.cucumber.datatable.DataTable
import io.cucumber.java.en.And
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import io.restassured.response.Response
import org.assertj.core.api.Assertions
import urls.ApiClient
import java.net.URLEncoder
import java.nio.charset.StandardCharsets


class TripSteps(private val tripsResponse: TripsResponse) {
    private var response: TripsResponse = tripsResponse

    @Given("fetch the trips details")
    fun endpoint() {
        response.tripsResults = ApiClient.fetchData()
    }

    @And("Get the trip count for user {string}")
    fun data(userId: String) {
        val count = response.tripsResults.count { it.customerId == userId && it.state == TripState.ORDERED }
        println("\nCustomer ${userId} has booked ${count} trips")
    }

    @And("no of trips active at {long}")
    fun activeTrips(timestamp: Long) {
        val activeTrips = response.tripsResults.filter {
            (it.pickup_time ?: Long.MAX_VALUE) <= timestamp && (it.dropoff_time ?: Long.MIN_VALUE) >= timestamp
        }

        val response = mapOf("tripsResults" to activeTrips.size)
        println("\nActive trips are: $response")
    }

    @And("most popular drop off stop")
    fun popularStop() {
        val mostPopularDropoff =
            response.tripsResults
                .filter { it.state == TripState.DROPPED_OFF }
                .groupingBy { it.dropoff_stop }
                .eachCount()
                .maxByOrNull { it.value }
                ?.key ?: "No drop-offs"
        print("\nMost popular drop off location:${mostPopularDropoff}")
    }

    @And("get the total duration of active trips")
    fun activeTripDuration() {
        val totalActiveTime = response.tripsResults
            .filter { it.pickup_time != null && it.dropoff_time != null && it.pickup_time!! < it.dropoff_time!! }
            .sumOf { (it.dropoff_time!! - it.pickup_time!!) }

        println("Total accumulated active trip time: $totalActiveTime seconds")

    }

    @Given("user had booked a trip with id {string}")
    fun trip(tripId: String) {
        val result: Response = ApiClient.getTrip(tripId);
        response.tripStatusCode = result.statusCode();
    }

    @When("check that API status code is {int}")
    fun statusCode(expectedStatusCode: Int) {
        Assertions.assertThat(response.tripStatusCode).isEqualTo(expectedStatusCode)
    }

    @Then("validate the response")
    fun statusCode() {
        response.tripsResults.forEach {
            require(it.tripId.isNotBlank())
            require(it.customerId.isNotBlank())
        }
    }

    @Given("user wants to book a trip")
    fun book(table: DataTable) {
        val values = table.asMaps().first()
        val objectMapper = jacksonObjectMapper()
        val payload = objectMapper.writeValueAsString(values)
        val result = ApiClient.createTrip(payload)
        response.tripStatusCode = result.statusCode()
    }

    @Then("validate the trips API response")
    fun validateJson() {
        response.tripsResults.first() { it.tripId.isNotBlank() && it.state.equals(TripState.ORDERED) }
    }

    @And("update the trip {string}")
    fun update(tripId: String, table: DataTable) {
        val values = table.asMaps().first()
        val objectMapper = jacksonObjectMapper()
        val payload = objectMapper.writeValueAsString(values)
        val result = ApiClient.editTrip(tripId, payload)
    }

    @And("check that current state of trip {string} is {string}")
    fun tripState(tripId: String, expectedState: String) {
        val trip = response.tripsResults.find { it.tripId == tripId }
        Assertions.assertThat(trip).isNotNull
        Assertions.assertThat(trip?.state).isEqualTo(expectedState)
    }

    private fun encode(value: String): String = URLEncoder.encode(value, StandardCharsets.UTF_8)

}




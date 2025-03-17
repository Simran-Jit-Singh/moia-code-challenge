package urls

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.moia.core.domain.trips.TripResults
import io.restassured.response.Response
import restassured.RestOperations

object ApiClient {

    fun fetchData(): TripResults {
        val response: TripResults = loadConfig();
        return response;
    }

    fun getTrip(tripId: String): Response {
        val path = "/trip/${tripId}";
        val response: Response = RestOperations.get(path)
        response.`as`(TripResults::class.java)
        return response
    }

    fun createTrip(payload: Any): Response {
        val path = "/trip"
        val response: Response = RestOperations.post(path, payload)
        response.`as`(TripResults::class.java)
        return response
    }

    fun editTrip(tripId: String, payload: Any): Response {
        val path = "/trip/${tripId}";
        val response: Response = RestOperations.patch(path, payload)
        response.`as`(TripResults::class.java)
        return response;
    }

    private fun loadConfig(): TripResults {
        val inputStream = object {}.javaClass.classLoader.getResourceAsStream("config.json")
            ?: throw IllegalArgumentException("Config file not found!")

        //Deserialize json object to trips
        val json = inputStream.bufferedReader().use { it.readText() }
        return jacksonObjectMapper().readValue(json, TripResults::class.java)
    }
}
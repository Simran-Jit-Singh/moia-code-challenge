package restassured

import io.restassured.RestAssured.given
import io.restassured.response.Response
import java.util.*

object RestOperations {
    private val env = System.getProperty("env") ?: "dev"
    private val props = Properties().apply {
        load(ClassLoader.getSystemResourceAsStream("$env.properties"))
    }

    // env specific config
    val baseUrl = props.getProperty("baseUrl")
    val apiKey = props.getProperty("x-api-key")

    fun get(path: String): Response {
        val url = baseUrl.plus(path)
        val result = given()
            .header("trace-id", "something")
            .header("x-api-key", apiKey) // if any
            .header("X-Application-Platform", "platformName")
            .contentType("application/json")
            .get(url)
        return result
    }

    fun post(path: String, payload: Any): Response {
        val url = baseUrl.plus(path)
        val result = given()
            .header("trace-id", "something")
            .header("x-api-key", apiKey)
            .header("X-Application-Platform", "platformName")
            .contentType("application/json")
            .body(payload)
            .post(url)
        return result
    }

    fun patch(path: String, payload: Any): Response {
        val url = baseUrl.plus(path)
        val result = given()
            .header("trace-id", "something")
            .header("x-api-key", apiKey)
            .header("X-Application-Platform", "platformName")
            .contentType("application/json")
            .body(payload)
            .patch(url)
        return result
    }
}
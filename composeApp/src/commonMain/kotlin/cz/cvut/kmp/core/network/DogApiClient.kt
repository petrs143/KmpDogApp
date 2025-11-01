package cz.cvut.kmp.core.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

class DogApiClient {

    val httpClient = HttpClient {
        defaultRequest {
            contentType(ContentType.Application.Json)
            url("https://dogapi.dog/api/v2/")
        }
        install(ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true
                }
            )
        }
    }

    suspend inline fun <reified T> get(endpoint: String): T {
        val response = httpClient.get(endpoint)
        return if (response.status.isSuccess()) {
            response.body<ResponseWrapper<T>>().data
        } else {
            throw HttpException(response.status.value, response.bodyAsText())
        }
    }
}

@Serializable
data class ResponseWrapper<T>(
    @SerialName("data")
    val data: T
)

class HttpException(statusCode: Int, bodyAsText: String) : Exception() {

    override val message = "statusCode=$statusCode, bodyAsText=$bodyAsText"
}

package pe.edu.upeu.data.repository

import io.ktor.client.HttpClient
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import kotlinx.serialization.json.Json
import pe.edu.upeu.data.local.SessionManager
import pe.edu.upeu.data.remote.api.ApiClient
import pe.edu.upeu.data.remote.dto.BookingRequestDto
import pe.edu.upeu.data.remote.dto.RoomRequestDto
import pe.edu.upeu.domain.model.BookingRequest
import pe.edu.upeu.domain.repository.BookingRepository
import kotlinx.serialization.encodeToString

class BookingRepositoryImpl (private val client: HttpClient,
    private val sessionManager: SessionManager
    ): BookingRepository{
    private val baseUrl = ApiClient.getBaseUrl()
    private val jsonSerializer = Json{
        ignoreUnknownKeys = true
        isLenient = true
    }

    override suspend fun createBooking(request: BookingRequest): Result<Boolean> {
        return try {
            val  token = sessionManager.getToken()
            val dto = BookingRequestDto(
                checkInDate = request.checkInDate,
                checkOutDate = request.checkOutDate,
                numberOfGuests = request.numberOfGuests,
                roomRequests = request.roomRequest.map {
                    RoomRequestDto(it.roomTypeId, it.numberOfRooms)
                }
            )
            val jsonPayload = jsonSerializer.encodeToString(dto)
            print("DEBUG : final JSON: $jsonPayload")
            val authHeader = if (token != null) {
                if (token.startsWith("Bearer ", ignoreCase = true)) token else "Bearer $token"
            } else {
                null
            }
            val response = client.post("$baseUrl/bookings"){
                if (authHeader !=null){
                    header("Authorization", authHeader)
                }
                contentType(ContentType.Application.Json)
                setBody(jsonPayload)
            }
            println("DEBUG_BOOKING_REPO: Response status: ${response.status}")
            if (response.status.isSuccess()) {
                Result.success(true)
            } else {
                Result.failure(Exception("Booking failed: ${response.status}"))
            }
        }catch (e: Exception){
            print("DEBUG_BOOKING: Exception: ${e.message}")
            Result.failure(e)
        }
    }

}
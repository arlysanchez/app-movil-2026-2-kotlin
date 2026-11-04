package pe.edu.upeu.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BookingRequestDto(
    @SerialName("check_in_date") val checkInDate: String,
    @SerialName("check_out_date") val checkOutDate: String,
    @SerialName("number_of_guests") val numberOfGuests: Int,
    @SerialName("roomRequests") val roomRequests: List<RoomRequestDto>
)

@Serializable
data class RoomRequestDto(
    @SerialName("roomTypeId") val roomTypeId: Int,
    @SerialName("numberOfRooms") val numberOfRooms: Int
)

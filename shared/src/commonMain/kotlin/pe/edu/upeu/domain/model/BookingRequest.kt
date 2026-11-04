package pe.edu.upeu.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class BookingRequest (
    val checkInDate : String,
    val checkOutDate: String,
    val numberOfGuests: Int,
    val roomRequest: List<RoomRequest>
)
@Serializable
data class RoomRequest(
    val roomTypeId : Int,
    val numberOfRooms: Int
)

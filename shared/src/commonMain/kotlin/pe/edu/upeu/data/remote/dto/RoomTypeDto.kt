package pe.edu.upeu.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class AmenityDto(
    val id: Int,
    val name: String,
    val description: String
)
@Serializable
data class RoomImageDto(
    val id: Int,
    val url: String,
    val altText: String
)

@Serializable
data class RoomTypeDto (
    val id: Int,
    val name: String,
    val description: String,
    val capacity: Int,
    val price : Double,
    val amenities : List<AmenityDto> = emptyList(),
    val images : List<RoomImageDto> = emptyList()
)
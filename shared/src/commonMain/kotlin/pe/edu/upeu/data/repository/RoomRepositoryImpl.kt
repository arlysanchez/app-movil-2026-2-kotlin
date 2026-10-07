package pe.edu.upeu.data.repository

import io.ktor.client.call.body
import io.ktor.client.request.*
import io.ktor.http.*
import pe.edu.upeu.data.remote.api.ApiClient
import pe.edu.upeu.data.remote.dto.RoomTypeDto
import pe.edu.upeu.data.remote.mapper.toDomain
import pe.edu.upeu.domain.model.RoomType
import pe.edu.upeu.domain.repository.RoomRepository

class RoomRepositoryImpl(
    private val client: io.ktor.client.HttpClient,

): RoomRepository {
    private val baseUrl = ApiClient.getBaseUrl()

    override suspend fun getRooms(): Result<List<RoomType>> {
        return try {
            val response = client.get("$baseUrl/room-types"){
              contentType(ContentType.Application.Json)
            }
            println("HTTP STATUS: ${response.status}")

            if (response.status.isSuccess()){
                val roomTypes = response.body<List<RoomTypeDto>>()
                println("Cantidad de habitaciones: ${roomTypes.size}")
                println("Datos recibidos:")
                println(roomTypes)

                println("====================================")
                Result.success(roomTypes.map{it.toDomain()})
            }else{
                Result.failure(Exception("Failed to fetch rooms: ${response.status}"))
            }
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}
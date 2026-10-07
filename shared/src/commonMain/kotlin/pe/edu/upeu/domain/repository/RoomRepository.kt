package pe.edu.upeu.domain.repository

import pe.edu.upeu.domain.model.RoomType

interface RoomRepository {
   suspend fun getRooms(): Result<List<RoomType>>
}
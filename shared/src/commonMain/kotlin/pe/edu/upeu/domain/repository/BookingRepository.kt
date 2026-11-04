package pe.edu.upeu.domain.repository

import pe.edu.upeu.domain.model.BookingRequest

interface BookingRepository {
    suspend fun createBooking(request: BookingRequest): Result<Boolean>
}
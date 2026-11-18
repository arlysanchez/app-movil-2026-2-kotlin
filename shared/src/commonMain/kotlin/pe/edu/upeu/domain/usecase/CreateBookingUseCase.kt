package pe.edu.upeu.domain.usecase

import pe.edu.upeu.domain.model.BookingRequest
import pe.edu.upeu.domain.repository.BookingRepository

class CreateBookingUseCase (private  val repository: BookingRepository){
    suspend operator fun invoke(request: BookingRequest): Result<Boolean>{
        return repository.createBooking(request)
    }
}
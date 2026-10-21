package pe.edu.upeu.domain.usecase

import pe.edu.upeu.domain.model.User
import pe.edu.upeu.domain.repository.AuthRepository

class RegisterUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(
        name: String,
        lastname: String,
        email: String,
        phone: String,
        password: String
    ): Result<User> {
        return repository.register(name, lastname, email, phone, password)

    }
}

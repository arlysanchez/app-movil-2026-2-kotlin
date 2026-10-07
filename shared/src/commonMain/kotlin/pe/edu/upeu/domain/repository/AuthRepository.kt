package pe.edu.upeu.domain.repository

import pe.edu.upeu.domain.model.User

interface AuthRepository {
    suspend fun register(name: String,lastname: String,email: String,phone: String,password: String): Result<User>
    suspend fun login(email: String, password: String): Result<User>
    suspend fun updateProfile(name: String,lastname: String,phone: String, imageBytes: ByteArray?): Result<User>

}
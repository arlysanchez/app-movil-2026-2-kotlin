package pe.edu.upeu.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequest(
    val name: String,
    val lastname: String,
    val email: String,
    val phone: String,
    val password: String
)

@Serializable
data class LoginRequest(
    val email: String,
    val password: String
)
@Serializable
data class UserDto(
    val id : Int,
    val name: String,
    val lastname: String,
    val email: String,
    val phone: String,
    val image: String? = null
)
@Serializable
data class AuthResponse(
    val user: UserDto,
    val token: String
)
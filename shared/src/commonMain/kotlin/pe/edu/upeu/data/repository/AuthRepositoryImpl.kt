package pe.edu.upeu.data.repository

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import pe.edu.upeu.data.remote.api.ApiClient
import pe.edu.upeu.data.remote.dto.AuthResponse
import pe.edu.upeu.data.remote.dto.LoginRequest
import pe.edu.upeu.data.remote.dto.RegisterRequest
import pe.edu.upeu.data.remote.mapper.toDomain
import pe.edu.upeu.domain.model.User
import pe.edu.upeu.domain.repository.AuthRepository


class AuthRepositoryImpl(private val client: HttpClient): AuthRepository {

    private val baseUrl = ApiClient.getBaseUrl()

    override suspend fun register(name: String,lastname: String,email: String,phone: String, password: String):
            Result<User> {
       return try {
           val response = client.post("$baseUrl/auth/register"){
               contentType(ContentType.Application.Json)
               setBody(RegisterRequest(name,lastname,email,phone,password))
           }
           if (response.status.isSuccess()){
               val authResponse = response.body<AuthResponse>()
               Result.success(authResponse.toDomain())
           }else{
               Result.failure(Exception("Register failed: ${response.status}"))
           }
       }catch (e: Exception){
           Result.failure(e)
       }

    }

    override suspend fun login(email: String,password: String): Result<User> {
        return try {
            val response = client.post("$baseUrl/auth/login"){
                contentType(ContentType.Application.Json)
                setBody(LoginRequest(email,password))
            }
            if (response.status.isSuccess()){
                val authResponse = response.body<AuthResponse>()
                Result.success(authResponse.toDomain())
            }else{
                Result.failure(Exception("Login failed: ${response.status}"))
            }
        }catch (e: Exception){
            Result.failure(e)
        }
    }

    override suspend fun updateProfile(name: String,lastname: String,phone: String,imageBytes: ByteArray?
    ): Result<User> {
        TODO("Not yet implemented")
    }
}
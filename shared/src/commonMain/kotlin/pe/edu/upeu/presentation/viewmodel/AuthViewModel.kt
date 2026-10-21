package pe.edu.upeu.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import pe.edu.upeu.data.local.SessionManager
import pe.edu.upeu.domain.model.User
import pe.edu.upeu.domain.usecase.LoginUseCase
import pe.edu.upeu.domain.usecase.RegisterUseCase

sealed class AuthState {
    object Idle : AuthState()
    object  Loading: AuthState()
    data class Success (val user: User): AuthState()
    data class Error(val message: String): AuthState()
}
class AuthViewModel (
    private val registerUseCase: RegisterUseCase,
    private val loginUseCase: LoginUseCase,
    private val sessionManager: SessionManager
): ViewModel(){
    private val _state = MutableStateFlow<AuthState>(AuthState.Idle)
    val state: StateFlow<AuthState> = _state.asStateFlow()

    fun register(name: String, lastname: String, email: String, phone: String,password: String){
        viewModelScope.launch {
            _state.value = AuthState.Loading
            val result = registerUseCase(name,lastname,email,phone,password)
            result.onSuccess {
                user ->
                print("DEGUB_AUTH: Register Success for ${user.email}")
                sessionManager.saveToken(user.token ?: "")
                sessionManager.saveUser(user)
                _state.value = AuthState.Success(user)
            }.onFailure { e ->
                print("DEGUB_AUTH: Register Failure for ${e.message}")
                _state.value = AuthState.Error(e.message ?:"Unknown error occurred during registration")
            }
        }

    }
    fun login(email: String, password: String){
        viewModelScope.launch {
            _state.value = AuthState.Loading
            val result = loginUseCase(email,password)
            result.onSuccess {
                    user ->
                print("DEGUB_AUTH: Login Success for ${user.email}")
                sessionManager.saveToken(user.token ?: "")
                sessionManager.saveUser(user)
                _state.value = AuthState.Success(user)
            }.onFailure { e ->
                print("DEGUB_AUTH: Login Failure for ${e.message}")
                _state.value = AuthState.Error(e.message ?:"Unknown error occurred during Login")
            }
        }
    }
    fun resetState(){
        _state.value = AuthState.Idle
    }

}
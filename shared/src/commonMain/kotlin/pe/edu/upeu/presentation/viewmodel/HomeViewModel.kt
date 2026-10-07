package pe.edu.upeu.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import pe.edu.upeu.domain.model.RoomType
import pe.edu.upeu.domain.repository.RoomRepository
import pe.edu.upeu.domain.usecase.GetRoomTypesUseCase

data class HomeUiState(
    val rooms: List<RoomType> = emptyList(),
    val cartItems: List<RoomType> = emptyList(),
    val isLoggedIn: Boolean = false,
    val isLoading : Boolean = false,
    val errorMessage: String? = null,
    val successMessage : String? = null

)
class HomeViewModel(
    private val getRoomTypesUseCase: GetRoomTypesUseCase
): ViewModel(){
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadRooms()
    }

     fun loadRooms(){
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            val result = getRoomTypesUseCase()
            result.onSuccess { rooms ->
                _uiState.update { it.copy(rooms=rooms, isLoading = false) }
            }.onFailure { e ->
                _uiState.update { it.copy(isLoading = false, errorMessage = e.message) }
            }
        }

    }

    fun addToCart(room: RoomType){
        _uiState.update { it.copy(cartItems = it.cartItems + room) }
    }
    fun removeFromCart(room: RoomType){
        _uiState.update { it.copy(cartItems = it.cartItems - room) }
    }
    fun clearCart(){
        _uiState.update{it.copy(cartItems = emptyList())}
    }
    fun toggleLogin(status: Boolean){
        _uiState.update { it.copy(isLoggedIn = status) }
    }
    fun clearMessages() {
        _uiState.update { it.copy(errorMessage = null, successMessage = null) }
    }

}
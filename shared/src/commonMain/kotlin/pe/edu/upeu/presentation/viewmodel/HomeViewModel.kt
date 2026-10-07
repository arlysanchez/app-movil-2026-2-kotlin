package pe.edu.upeu.presentation.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import pe.edu.upeu.domain.model.RoomType
import pe.edu.upeu.domain.repository.RoomRepository

data class HomeUiState(
    val rooms: List<RoomType> = emptyList(),
    val cartItems: List<RoomType> = emptyList(),
    val isLoggedIn: Boolean = false,
    val isLoading : Boolean = false

)
class HomeViewModel(private val repository: RoomRepository): ViewModel(){
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadRooms()
    }

    private fun loadRooms(){
        val rooms = repository.getRooms()
        _uiState.update { it.copy(rooms = rooms) }
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

}
package pe.edu.upeu.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import pe.edu.upeu.domain.usecase.GetNotificationSettingsUseCase
import pe.edu.upeu.domain.usecase.SendTestNotificationUseCase
import pe.edu.upeu.domain.usecase.SetNotificationEnabledUseCase

data class NotificationSettingsUiState(
    val isEnabled : Boolean = false,
    val isSendingTest: Boolean = false,
    val feedbackMessage : String? = null,
    val isFeedBackError: Boolean = false
)
class NotificationSettingsViewModel(
    private val getNotificationSettingsUseCase: GetNotificationSettingsUseCase,
    private val setNotificationEnabledUseCase: SetNotificationEnabledUseCase,
    private val sendTestNotificationUseCase: SendTestNotificationUseCase
): ViewModel(){
    private val _uiState = MutableStateFlow(
        NotificationSettingsUiState(
            isEnabled = getNotificationSettingsUseCase().isEnabled
        )
    )
    val uiState: StateFlow<NotificationSettingsUiState> = _uiState.asStateFlow()

    fun onPermissionResult(granted : Boolean){
        if (granted){
            setNotificationEnabledUseCase(true)
            _uiState.update {
                it.copy(
                    isEnabled = true,
                    feedbackMessage = "Notificaciones activadas",
                    isFeedBackError = false
                )
            }
        }else{
            setNotificationEnabledUseCase(false)
            _uiState.update {
                it.copy(
                    isEnabled = false,
                    feedbackMessage = "Se necesita permisos para mostrar notificaciones",
                    isFeedBackError = false
                )
            }
        }
    }
    fun setNotificationEnabled(enabled: Boolean){
        setNotificationEnabledUseCase(enabled)
        _uiState.update {
            it.copy(
                isEnabled = enabled,
                feedbackMessage = if (enabled) "Notificaciones activadas" else "Notificaciones desactivadas",
                isFeedBackError = false
            )
        }
    }
    fun sendTestNotification(){
        if (_uiState.value.isSendingTest) return
        viewModelScope.launch {
            _uiState.update { it.copy(isSendingTest = true, feedbackMessage = null) }
            sendTestNotificationUseCase().fold(
                onSuccess = {
                    _uiState.update {
                        it.copy(
                            isSendingTest = false,
                            feedbackMessage = "Notificaciones local enviada",
                            isFeedBackError = false

                        )
                    }
                },
                onFailure = { error ->
                    _uiState.update {
                        it.copy(
                            isSendingTest = false,
                            feedbackMessage = error.message ?: "No se pudo enviar Notificaciones local enviada",
                            isFeedBackError = true
                        )
                    }
                }
            )
        }
    }


}
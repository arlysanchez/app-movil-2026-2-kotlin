package pe.edu.upeu.domain.repository

interface NotificationRepository {
    fun isEnabled(): Boolean
    fun setEnabled(enabled: Boolean)
    suspend fun sendTestNotification(): Result<Unit>
}
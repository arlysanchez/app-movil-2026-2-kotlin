package pe.edu.upeu.data.repository

import com.russhwolf.settings.Settings
import pe.edu.upeu.data.notification.LocalNotificationPlatform
import pe.edu.upeu.domain.repository.NotificationRepository

class NotificationRepositoryImpl(
    private val settings: Settings,
    private val localNotificationPlatform: LocalNotificationPlatform
): NotificationRepository {

    override fun isEnabled(): Boolean =
        settings.getBoolean(KEY_NOTIFICATION_ENABLED,false)


    override fun setEnabled(enabled: Boolean) {
        settings.putBoolean(KEY_NOTIFICATION_ENABLED,enabled)
    }

    override suspend fun sendTestNotification(): Result<Unit> {
        if (!isEnabled()){
            return Result.failure(IllegalStateException("Activa las notificaciones antes de enviar"))
        }
        return  localNotificationPlatform.showTestNotification()
    }
    private companion object{
        const val KEY_NOTIFICATION_ENABLED = "notifications_enabled"
    }
}
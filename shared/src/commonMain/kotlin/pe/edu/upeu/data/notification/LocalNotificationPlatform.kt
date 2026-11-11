package pe.edu.upeu.data.notification

interface LocalNotificationPlatform{
    suspend fun showTestNotification(): Result<Unit>
}
object  UnsupportedLocalNotificationPlatform: LocalNotificationPlatform{
    override suspend fun showTestNotification(): Result<Unit> =
        Result.failure(UnsupportedOperationException
            ("Las notificaciones locales solo estan disponibles en android"))
}
package pe.edu.upeu.domain.usecase

import pe.edu.upeu.domain.repository.NotificationRepository

class SendTestNotificationUseCase(
    private val repository: NotificationRepository
) {
    suspend operator fun invoke()=repository.sendTestNotification()
}
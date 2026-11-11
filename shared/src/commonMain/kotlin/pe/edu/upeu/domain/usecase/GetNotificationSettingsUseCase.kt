package pe.edu.upeu.domain.usecase

import pe.edu.upeu.domain.model.NotificationSettings
import pe.edu.upeu.domain.repository.NotificationRepository

class GetNotificationSettingsUseCase(
    private val repository: NotificationRepository
) {
    operator  fun invoke() = NotificationSettings(
        isEnabled = repository.isEnabled())
}
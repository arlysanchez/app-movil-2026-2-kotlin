package pe.edu.upeu.domain.usecase

import pe.edu.upeu.domain.repository.NotificationRepository

class SetNotificationEnabledUseCase(
    private val repository: NotificationRepository
) {
    operator fun invoke(enabled: Boolean) = repository.setEnabled(enabled)
}
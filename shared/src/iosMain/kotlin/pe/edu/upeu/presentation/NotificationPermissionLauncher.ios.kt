package pe.edu.upeu.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.getValue

@Composable
actual fun rememberNotificationPermissionLauncher(
    onResult: (granted: Boolean) -> Unit):
        NotificationPermissionLauncher {
    val currentOnResult by rememberUpdatedState(onResult)
    return remember{
        object : NotificationPermissionLauncher {
            override fun request() {
                currentOnResult(false)
            }
        }
    }
}
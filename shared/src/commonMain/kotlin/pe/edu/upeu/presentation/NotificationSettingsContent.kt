package pe.edu.upeu.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import pe.edu.upeu.presentation.viewmodel.NotificationSettingsViewModel
import androidx.compose.runtime.getValue


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationSettingsContent(
    viewModel: NotificationSettingsViewModel,
    onBack: () -> Unit
) {
    val state by viewModel.uiState.collectAsState()
    val permissionLauncher = rememberNotificationPermissionLauncher(viewModel::onPermissionResult)

    Scaffold (
        topBar = {
            TopAppBar(
                title = { Text("Ajustes de notificaciones") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Top
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
                Column(modifier = Modifier.weight(1f)) {
                    Text("Notificaciones", style = MaterialTheme.typography.titleMedium)
                    Text(
                        "Activa las notificaciones locales de esta app.",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Switch(
                    checked = state.isEnabled,
                    onCheckedChange = { enabled ->
                        if (enabled) permissionLauncher.request()
                        else viewModel.setNotificationEnabled(false)
                    }
                )
            }

            Spacer(Modifier.height(24.dp))

            Button(
                onClick = viewModel::sendTestNotification,
                enabled = state.isEnabled && !state.isSendingTest,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (state.isSendingTest) "Enviando…" else "Enviar notificación local de prueba")
            }

            state.feedbackMessage?.let { message ->
                Spacer(Modifier.height(16.dp))
                Text(
                    text = message,
                    color = if (state.isFeedBackError) MaterialTheme.colorScheme.error
                    else MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(Modifier.height(24.dp))
            Text(
                "Esta prueba se genera en el dispositivo; todavía no requiere Firebase ni el backend.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
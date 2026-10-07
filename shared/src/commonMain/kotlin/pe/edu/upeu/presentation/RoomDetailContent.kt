package pe.edu.upeu.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import pe.edu.upeu.domain.model.RoomType
import pe.edu.upeu.presentation.components.BookingButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoomDetailContent(
    room: RoomType,
    onBack: () -> Unit,
    onAddToCart: (RoomType) -> Unit

) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalle de Habitación") },
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, null) } }
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).verticalScroll(rememberScrollState())) {
            // Galería simulada (Placeholder grande)
            Box(modifier = Modifier.fillMaxWidth().height(250.dp).background(Color.DarkGray)) {
                Text("Galería de Imágenes (Firebase)", color = Color.White, modifier = Modifier.align(
                    Alignment.Center))
            }

            Column(modifier = Modifier.padding(20.dp)) {
                Text(room.name, style = MaterialTheme.typography.headlineMedium, color = MaterialTheme.colorScheme.primary)
                Text("S/ ${room.price} por noche", style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.secondary)

                Spacer(Modifier.height(16.dp))
                Text("Descripción", style = MaterialTheme.typography.titleMedium)
                Text(room.description, style = MaterialTheme.typography.bodyMedium)

                Spacer(Modifier.height(24.dp))

                // SECCIÓN DE AMENITIES (Simulando el backend)
                Text("Servicios incluidos", style = MaterialTheme.typography.titleMedium)
                Row(modifier = Modifier.padding(vertical = 8.dp), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    AmenityIcon(Icons.Default.Check, "WiFi")
                    AmenityIcon(Icons.Default.Check, "TV")
                    AmenityIcon(Icons.Default.Check, "A/C")
                }

                Spacer(Modifier.height(32.dp))

                // BOTÓN FINAL DE RESERVA
                BookingButton(
                    text = "Agregar a mi lista",
                    onClick = {
                        // Aquí avisamos al App.kt que agregue esta habitación
                        onAddToCart(room)
                    }
                )
            }
        }
    }
}

@Composable
fun AmenityIcon(icon: ImageVector, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(icon, null, tint = Color.Gray)
        Text(label, style = MaterialTheme.typography.labelSmall)
    }
}
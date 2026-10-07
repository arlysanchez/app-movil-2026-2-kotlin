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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
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
                if (room.images.isNotEmpty()){
                    KamelImage(
                        resource = asyncPainterResource(room.images.first().url),
                        contentDescription = room.images.first().altText,
                        contentScale = ContentScale.Crop
                    )
                }else{
                    Box(modifier = Modifier.fillMaxSize().background(Color.DarkGray),
                    contentAlignment = Alignment.Center
                    ){
                        Text("Imagen no disponible", color = Color.White)
                    }
                }

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
                Row(modifier = Modifier.padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    if (room.amenities.isNotEmpty()){
                        room.amenities.forEach { amenity ->
                            AmenityIcon(Icons.Default.Check,amenity.name)
                        }
                    }else{
                        Text("No hay servicios disponibles", style = MaterialTheme.typography.bodySmall)
                    }
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
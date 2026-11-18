package pe.edu.upeu.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Build
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import pe.edu.upeu.presentation.components.BookingButton

@Composable
fun PaymentDialogContent (totalAmount: Double, onFinish :(method: String)-> Unit) {
    var selectedMethod by remember { mutableStateOf("credit_card") }
    Column(modifier = Modifier.padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Método de Pago", style = MaterialTheme.typography.headlineSmall)
        Text("Total a pagar: S/ $totalAmount", color = MaterialTheme.colorScheme.primary)
        Spacer(Modifier.height(24.dp))
        // OPCIONES DE PAGO
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            PaymentOption(
                label = "Tarjeta",
                icon = Icons.Default.AccountBox,
                isSelected = selectedMethod == "credit_card",
                onClick = { selectedMethod = "credit_card" }
            )
            PaymentOption(
                label = "Efectivo",
                icon = Icons.Default.Build,
                isSelected = selectedMethod == "cash",
                onClick = { selectedMethod = "cash" }
            )
            Spacer(Modifier.height(32.dp))

            BookingButton(text = "Finalizar y Pagar", onClick = { onFinish(selectedMethod) })
        }
    }
}
    @Composable
    fun PaymentOption(label: String, icon: ImageVector, isSelected: Boolean, onClick: () -> Unit) {
        OutlinedCard(
            onClick = onClick,
            modifier = Modifier.size(100.dp),
            border = BorderStroke(
                2.dp,
                if (isSelected) MaterialTheme.colorScheme.primary else Color.LightGray
            )
        ) {
            Column(Modifier.fillMaxSize(), Arrangement.Center, Alignment.CenterHorizontally) {
                Icon(
                    icon,
                    null,
                    tint = if (isSelected) MaterialTheme.colorScheme.primary else Color.Gray
                )
                Text(label, style = MaterialTheme.typography.labelMedium)
            }
        }

    }

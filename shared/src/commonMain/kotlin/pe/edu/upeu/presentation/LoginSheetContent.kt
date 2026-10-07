package pe.edu.upeu.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import pe.edu.upeu.presentation.components.BookingButton
import pe.edu.upeu.presentation.components.BookingTextField

@Composable
fun LoginDialogContent(
    onNavigateToRegister: () -> Unit,
    onLoginSuccess: () -> Unit
){
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text("Hola de nuevo!", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(16.dp))
        BookingTextField(value = email,
            onValueChange = {email = it},
            label = "Email",
            icono = Icons.Default.Email)
        Spacer(Modifier.height(8.dp))
        BookingTextField(
            value = password,
            onValueChange = {password = it},
            label = "Password",
            icono = Icons.Default.Lock,
            isPassword = true
        )
        Spacer(Modifier.height(24.dp))
        BookingButton(text = "Entrar", onClick= onLoginSuccess)
        TextButton(onClick = onNavigateToRegister){
            Text("Eres Nuevo? Regístrate")
        }
    }

}
package pe.edu.upeu.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Php
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import pe.edu.upeu.presentation.components.BookingButton
import pe.edu.upeu.presentation.components.BookingTextField

@Composable
fun RegisterDialogContent(
    onNavigationToLogin: () -> Unit,
    onRegisterSuccess: () -> Unit
){
    var name by remember { mutableStateOf("") }
    var lastname by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier.fillMaxWidth()
            .padding(24.dp)
            .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Text(
            text = "Crear Cuenta",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = "Completa tus datos para empezar a reservar",
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(bottom = 24.dp)
        )
        BookingTextField(
            value = name,
            onValueChange = {name = it},
            label = "Nombre",
            icono = Icons.Default.Person
        )
        Spacer(Modifier.height(12.dp))

        BookingTextField(
            value = lastname,
            onValueChange = {lastname = it},
            label = "Apellidos",
            icono = Icons.Default.AccountBox
        )
        Spacer(Modifier.height(12.dp))

        BookingTextField(
            value = email,
            onValueChange = {email = it},
            label = "Email",
            icono = Icons.Default.Email
        )
        Spacer(Modifier.height(12.dp))
        BookingTextField(
            value = phone,
            onValueChange = {phone = it},
            label = "Telefono",
            icono = Icons.Default.Phone
        )
        Spacer(Modifier.height(12.dp))

        BookingTextField(
            value = password,
            onValueChange = {password = it},
            label = "Password",
            icono = Icons.Default.Lock,
            isPassword = true
        )
        Spacer(Modifier.height(32.dp))
        BookingButton(
            text = "Registrarme",
            onClick = {
                if (name.isNotBlank() && email.isNotBlank() && password.isNotBlank()){
                    onRegisterSuccess()
                }
            }
        )
        Spacer(Modifier.height(8.dp))
        TextButton(onClick = onNavigationToLogin){
            Text("¿Ya tienes cuenta Inicia Sesion")
        }

    }


}
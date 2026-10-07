package pe.edu.upeu.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import pe.edu.upeu.domain.model.RoomType
import pe.edu.upeu.presentation.components.RoomCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeContent(
    isLoggedIn: Boolean,
    onLoginClick: () -> Unit,
    onLogout: () -> Unit,
    onReserveClick: (RoomType) -> Unit,
    onEditProfile: () -> Unit,
    onCartClick: () -> Unit,
    cartSize: Int,
) {
    //controlar los estados del home
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val selectTab by remember { mutableStateOf(0) }

    val dummyRooms = listOf(
        RoomType(1, "Habitación Simple", "Cama confortable, WiFi y vista a la ciudad.", 1, 80.0),
        RoomType(2,"Habitación Doble","Dos camas, balcón privado y aire acondicionado.",2, 120.0),
        RoomType(3, "Suite Ejecutiva", "Lujo total con jacuzzi y mini bar.", 2, 250.0)
    )
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        item {
            Text("Habitaciones Disponibles", style = MaterialTheme.typography.headlineSmall)
            Spacer(Modifier.height(16.dp))
        }
        items(dummyRooms) { room ->
            RoomCard(
                room = room,
                onViewDetail = { onReserveClick(room) } // <--- Pase el objeto room
            )
        }
    }

    //estructura del drawer (menu lateral

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Box(
                    modifier = Modifier.fillMaxWidth().height(220.dp)
                        .background(MaterialTheme.colorScheme.primary),
                    contentAlignment = Alignment.Center
                ) {
                    if (isLoggedIn) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Box(contentAlignment = Alignment.BottomEnd) {
                                //componente 1:icono perfil
                                Icon(
                                    imageVector = Icons.Default.AccountCircle,
                                    contentDescription = null,
                                    modifier = Modifier.size(90.dp)
                                        .clickable { onEditProfile() },
                                    tint = Color.White
                                )
                                //componente 2: lapiz de edicion
                                Surface(
                                    shape = CircleShape,
                                    color = MaterialTheme.colorScheme.secondary,
                                    modifier = Modifier.size(30.dp).offset(x = (-5).dp, y = (-4).dp)
                                ) {
                                    IconButton(onClick = onEditProfile) {
                                        Icon(
                                            Icons.Default.Edit,
                                            contentDescription = null,
                                            modifier = Modifier.size(16.dp),
                                            tint = MaterialTheme.colorScheme.onSecondary
                                        )
                                    }
                                }
                            }
                            Text(
                                "Juan Perez", color = Color.White,
                                style = MaterialTheme.typography.headlineSmall
                            )
                            Text("admin@gmail.com", color = Color.White.copy(alpha = 0.7f))
                        }
                    } else {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                Icons.Default.AccountCircle, null,
                                modifier = Modifier.size(80.dp),
                                tint = Color.White.copy(alpha = 0.5f)
                            )
                            Spacer(Modifier.height(12.dp))
                            Button(
                                onClick = {
                                    scope.launch { drawerState.close() };
                                    onLoginClick()
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.secondary
                                )
                            ) {
                                Text(
                                    "iniciar Sesion",
                                    color = MaterialTheme.colorScheme.onSecondary
                                )
                            }
                        }
                    }

                }
                Spacer(Modifier.height(12.dp))
                if (isLoggedIn) {
                    NavigationDrawerItem(
                        label = { Text("Mi Perfil") },
                        selected = false,
                        onClick = { scope.launch { drawerState.close() } },
                        icon = { Icon(Icons.Default.Person, null) }
                    )
                    NavigationDrawerItem(
                        label = { Text("Cerrar Sesion") },
                        selected = false,
                        onClick = {
                            scope.launch { drawerState.close() };
                            onLogout()
                        },
                        icon = { Icon(Icons.Default.ExitToApp, null) }
                    )
                }
            }
        }

    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Reservas App") },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(Icons.Default.Menu, null)
                        }
                    },
                    //acciones fuera del navigation icon
                    actions = {
                        IconButton(onClick = onCartClick) {
                            BadgedBox(
                                badge = {
                                    if (cartSize > 0) {
                                        Badge { Text("$cartSize") }
                                    }
                                }
                            ) {
                                Icon(
                                    Icons.Default.ShoppingCart,
                                    contentDescription = "Carrito"
                                )
                            }
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.secondary
                    )

                )
            },
            bottomBar = {
                NavigationBar {
                    NavigationBarItem(
                        selected = selectTab == 0,
                        onClick = { selectTab },
                        icon = { Icon(Icons.Default.Home, null) },
                        label = { Text("Inicio") }
                    )
                    NavigationBarItem(
                        selected = selectTab == 1,
                        onClick = { selectTab },
                        icon = { Icon(Icons.Default.DateRange, null) },
                        label = { Text("Reservas") }
                    )
                }
            }
        ) { padding ->
            Column(
                modifier =
                    Modifier.padding(padding).fillMaxSize().padding(16.dp)
            ) {
                if (selectTab == 0) {
                    LazyColumn(modifier = Modifier.fillMaxSize())
                    {
                        item {
                            Text(
                                "Habitaciones disponibles",
                                style = MaterialTheme.typography.headlineSmall
                            )
                            Spacer(Modifier.height(16.dp))
                        }
                        items(dummyRooms) { room ->
                            RoomCard(
                                room = room,
                                onViewDetail = { onReserveClick(room) }

                            )
                        }
                    }
                }else{
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center ){
                        Text("historial de reservas")
                    }
                }

            }


        }


    }


}
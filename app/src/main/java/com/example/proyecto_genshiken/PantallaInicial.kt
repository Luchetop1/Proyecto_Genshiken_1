package com.example.proyecto_genshiken

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.material3.*
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaInicio(navController: NavController) {

    val curiosidad = remember { Curiosidades.lista.random() }
    var expandir by remember { mutableStateOf(false) }

    Scaffold(

        // En este header aparecerá el nombre del juego y la configuracion para que se vea siempre d:
        topBar = {
            Spacer(modifier = Modifier.height(16.dp))
            TopAppBar(
                title = {
                    Text("Genshiken")
                },
                actions = {

                    IconButton(onClick = { expandir = true }) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "Configuración"
                        )
                    }

                    DropdownMenu(
                        expanded = expandir,
                        onDismissRequest = { expandir = false }
                    ) {

                        DropdownMenuItem(
                            text = { Text("Modo oscuro") },
                            onClick = {
                                expandir = false
                                ThemeState.isDarkMode.value =
                                    !ThemeState.isDarkMode.value
                            }
                        )

                        DropdownMenuItem(
                            text = { Text("Cambiar nombre") },
                            onClick = {
                                expandir = false
                                navController.navigate("cambiarNombre")
                            }
                        )
                    }
                }
            )
        }

    ) { paddingValues ->

        // Este es el siguiente contenido, este se puede Scrollear hacia arriba o abajo
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues) // muy importante el Scroll
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Bienvenido a la aplicación",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Seleccione el modo al que desea jugar",
                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.height(40.dp))

            Image(
                painter = painterResource(id = R.drawable.espadacasual),
                contentDescription = "Casual",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .clickable {
                        navController.navigate("inicioSesionCasual")
                    }
            )

            Spacer(modifier = Modifier.height(40.dp))

            Image(
                painter = painterResource(id = R.drawable.espadacasual),
                contentDescription = "Competitivo",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .clickable {
                        if (UserSession.userId == 0) {
                            navController.navigate("inicioSesionCompeti")
                        } else {
                            navController.navigate("Juego")
                        }
                    }
            )

            Spacer(modifier = Modifier.height(60.dp))

            Image(
                painter = painterResource(id = R.drawable.corona),
                contentDescription = "Ranking",
                modifier = Modifier
                    .size(180.dp)
                    .clickable {
                        navController.navigate("Ranking")
                    }
            )

            Spacer(modifier = Modifier.height(30.dp))

            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        text = "¿Sabías que?",
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = curiosidad,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            Button(onClick = {
                navController.navigate("gacha")
            }) {
                Text("Gacha")
            }

            Button(onClick = {
                navController.navigate("coleccion")
            }) {
                Text("Colección")
            }
        }
    }
}
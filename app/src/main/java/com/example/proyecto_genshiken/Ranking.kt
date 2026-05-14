package com.example.proyecto_genshiken

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import java.util.Calendar

@Composable
fun Ranking(navController: NavController) {

    var ranking by remember {
        mutableStateOf<List<Player>>(emptyList())
    }

    val meses = listOf(
        "Enero","Febrero","Marzo","Abril",
        "Mayo","Junio","Julio","Agosto",
        "Septiembre","Octubre","Noviembre","Diciembre"
    )

    val calendario = Calendar.getInstance()

    var mesSeleccionado by remember {
        mutableIntStateOf(
            calendario.get(Calendar.MONTH) + 1
        )
    }

    var anioSeleccionado by remember {
        mutableIntStateOf(
            calendario.get(Calendar.YEAR)
        )
    }

    var expandedMes by remember {
        mutableStateOf(false)
    }

    var expandedAnio by remember {
        mutableStateOf(false)
    }

    val anios = (2026..2040).toList()

    LaunchedEffect(
        mesSeleccionado,
        anioSeleccionado
    ) {

        UserRepository.getRanking(
            mesSeleccionado,
            anioSeleccionado
        ) {

            ranking = it
        }
    }

    val top10 = ranking.take(10)

    val userIndex = ranking.indexOfFirst {
        it.nombre == UserSession.userName
    }

    val userData = ranking.getOrNull(userIndex)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),

        horizontalAlignment =
            Alignment.CenterHorizontally
    ) {

        Spacer(Modifier.height(30.dp))

        Text(
            "RANKING",
            fontSize = 32.sp
        )

        Spacer(Modifier.height(20.dp))

        Row {

            Box {

                Button(
                    onClick = {
                        expandedMes = true
                    }
                ) {

                    Text(
                        meses[mesSeleccionado - 1]
                    )
                }

                DropdownMenu(
                    expanded = expandedMes,
                    onDismissRequest = {
                        expandedMes = false
                    }
                ) {

                    meses.forEachIndexed { index, mes ->

                        DropdownMenuItem(
                            text = {
                                Text(mes)
                            },
                            onClick = {

                                mesSeleccionado =
                                    index + 1

                                expandedMes = false
                            }
                        )
                    }
                }
            }

            Spacer(Modifier.width(10.dp))

            Box {

                Button(
                    onClick = {
                        expandedAnio = true
                    }
                ) {

                    Text("$anioSeleccionado")
                }

                DropdownMenu(
                    expanded = expandedAnio,
                    onDismissRequest = {
                        expandedAnio = false
                    }
                ) {

                    anios.forEach { anio ->

                        DropdownMenuItem(
                            text = {
                                Text("$anio")
                            },
                            onClick = {

                                anioSeleccionado =
                                    anio

                                expandedAnio = false
                            }
                        )
                    }
                }
            }
        }

        Spacer(Modifier.height(20.dp))

        Row(
            Modifier.fillMaxWidth()
        ) {

            Text(
                "Pos",
                Modifier.weight(1f)
            )

            Text(
                "Nombre",
                Modifier.weight(2f)
            )

            Text(
                "Pts",
                Modifier.weight(1f)
            )

            Text(
                "Tiempo",
                Modifier.weight(1f)
            )
        }

        Spacer(Modifier.height(10.dp))

        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {

            itemsIndexed(top10) { index, player ->

                val color = when(index){

                    0 -> Color(0xFFFFD700)
                    1 -> Color(0xFFC0C0C0)
                    2 -> Color(0xFFCD7F32)

                    else -> Color.Transparent
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color)
                        .padding(8.dp)
                ) {

                    Text(
                        "${player.posicion}",
                        Modifier.weight(1f)
                    )

                    Text(
                        player.nombre,
                        Modifier.weight(2f)
                    )

                    Text(
                        "${player.puntuacion}",
                        Modifier.weight(1f)
                    )

                    Text(
                        "${player.tiempo}",
                        Modifier.weight(1f)
                    )
                }
            }
        }

        Spacer(Modifier.height(20.dp))

        Text(
            "Tu posición",
            fontSize = 22.sp
        )

        Spacer(Modifier.height(10.dp))

        userData?.let {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Cyan)
                    .padding(8.dp)
            ) {

                Text(
                    "${it.posicion}",
                    Modifier.weight(1f)
                )

                Text(
                    it.nombre,
                    Modifier.weight(2f)
                )

                Text(
                    "${it.puntuacion}",
                    Modifier.weight(1f)
                )

                Text(
                    "${it.tiempo}",
                    Modifier.weight(1f)
                )
            }
        }

        Spacer(Modifier.height(20.dp))

        Button(
            onClick = {
                navController.navigate("inicio")
            }
        ) {

            Text("Volver")
        }
    }
}

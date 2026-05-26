package com.example.proyecto_genshiken

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun PantallaColeccion(navController: NavController) {

    val totalEspadas =
        EspadasData.lista.size

    val desbloqueadas =
        GachaState.espadasDesbloqueadas.size

    val porcentaje =
        desbloqueadas.toFloat() /
                totalEspadas.toFloat()

    /*
    ----------------------------------------
    FONDO
    ----------------------------------------
    */

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color(0xFF0F172A),
                        Color(0xFF1E1B4B),
                        Color(0xFF111827)
                    )
                )
            )
    ) {

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(18.dp),

            horizontalAlignment =
                Alignment.CenterHorizontally
        ) {

            item {

                Spacer(
                    Modifier.height(30.dp)
                )

                /*
                ----------------------------------------
                TÍTULO
                ----------------------------------------
                */

                Text(
                    text = " COLECCIÓN ",

                    fontSize = 34.sp,

                    fontWeight =
                        FontWeight.ExtraBold,

                    color = Color.White
                )

                Spacer(
                    Modifier.height(18.dp)
                )

                /*
                ----------------------------------------
                PROGRESO
                ----------------------------------------
                */

                Card(
                    colors = CardDefaults.cardColors(
                        containerColor =
                            Color.White.copy(
                                alpha = 0.1f
                            )
                    ),

                    shape =
                        RoundedCornerShape(22.dp)
                ) {

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp)
                    ) {

                        Text(
                            text =
                                "Desbloqueadas: $desbloqueadas / $totalEspadas",

                            color = Color.White,

                            fontWeight =
                                FontWeight.Bold,

                            fontSize = 18.sp
                        )

                        Spacer(
                            Modifier.height(14.dp)
                        )

                        LinearProgressIndicator(
                            progress = {
                                porcentaje
                            },

                            modifier = Modifier
                                .fillMaxWidth()
                                .height(10.dp),

                            color = Color(
                                0xFFFACC15
                            ),

                            trackColor = Color(
                                0xFF334155
                            )
                        )
                    }
                }

                Spacer(
                    Modifier.height(24.dp)
                )
            }

            /*
            ----------------------------------------
            LISTA ESPADAS
            ----------------------------------------
            */

            items(EspadasData.lista) { espada ->

                val desbloqueada =
                    GachaState
                        .espadasDesbloqueadas
                        .contains(espada.id)

                val colorFondo =
                    when (espada.rareza) {

                        Rareza.COMUN ->
                            Color.Gray

                        Rareza.RARA ->
                            Color(0xFF38BDF8)

                        Rareza.EPICA ->
                            Color(0xFFC084FC)

                        Rareza.LEGENDARIA ->
                            Color(0xFFFACC15)
                    }

                val estrellas =
                    when (espada.rareza) {

                        Rareza.COMUN -> 1
                        Rareza.RARA -> 2
                        Rareza.EPICA -> 3
                        Rareza.LEGENDARIA -> 4
                    }

                AnimatedVisibility(
                    visible = true,

                    enter =
                        fadeIn(
                            animationSpec =
                                tween(500)
                        ) +

                                scaleIn(
                                    initialScale = 0.9f,

                                    animationSpec =
                                        tween(500)
                                )
                ) {

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 10.dp),

                        shape =
                            RoundedCornerShape(28.dp),

                        elevation =
                            CardDefaults.cardElevation(
                                12.dp
                            ),

                        colors =
                            CardDefaults.cardColors(
                                containerColor =
                                    if (desbloqueada)

                                        colorFondo.copy(
                                            alpha = 0.25f
                                        )

                                    else

                                        Color.DarkGray.copy(
                                            alpha = 0.45f
                                        )
                            )
                    ) {

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(18.dp),

                            verticalAlignment =
                                Alignment.CenterVertically
                        ) {

                            /*
                            ----------------------------------------
                            IMAGEN
                            ----------------------------------------
                            */

                            Card(
                                shape =
                                    RoundedCornerShape(
                                        20.dp
                                    ),

                                colors =
                                    CardDefaults.cardColors(
                                        containerColor =
                                            Color.White.copy(
                                                alpha = 0.08f
                                            )
                                    )
                            ) {

                                Image(
                                    painter =
                                        painterResource(

                                            if (desbloqueada)
                                                espada.imagen

                                            else
                                                R.drawable.ic_launcher_foreground
                                        ),

                                    contentDescription =
                                        espada.nombre,

                                    contentScale =
                                        ContentScale.Fit,

                                    modifier = Modifier
                                        .size(110.dp)
                                        .padding(10.dp)

                                        .alpha(
                                            if (desbloqueada)
                                                1f
                                            else
                                                0.35f
                                        )
                                )
                            }

                            Spacer(
                                modifier =
                                    Modifier.width(18.dp)
                            )

                            /*
                            ----------------------------------------
                            INFO
                            ----------------------------------------
                            */

                            Column(
                                modifier = Modifier.weight(1f)
                            ) {

                                Text(
                                    text =

                                        if (desbloqueada)
                                            espada.nombre

                                        else
                                            "?????",

                                    fontSize = 24.sp,

                                    fontWeight =
                                        FontWeight.ExtraBold,

                                    color = Color.White
                                )

                                Spacer(
                                    Modifier.height(8.dp)
                                )

                                if (desbloqueada) {

                                    Text(
                                        text =
                                            "⭐".repeat(
                                                estrellas
                                            ),

                                        fontSize = 22.sp
                                    )

                                    Spacer(
                                        Modifier.height(10.dp)
                                    )

                                    Text(
                                        text =
                                            espada.descripcion,

                                        color =
                                            Color.LightGray,

                                        fontSize = 15.sp
                                    )

                                    Spacer(
                                        Modifier.height(10.dp)
                                    )

                                    Text(
                                        text =
                                            espada.rareza.name,

                                        color =
                                            colorFondo,

                                        fontWeight =
                                            FontWeight.Bold
                                    )

                                } else {

                                    Text(
                                        text =
                                            " Bloqueada",

                                        color =
                                            Color.LightGray,

                                        fontSize = 18.sp,

                                        fontWeight =
                                            FontWeight.Bold
                                    )

                                    Spacer(
                                        Modifier.height(8.dp)
                                    )

                                    Text(
                                        text =
                                            "Consíguela en el gachapon.",

                                        color =
                                            Color.Gray
                                    )
                                }
                            }
                        }
                    }
                }
            }

            /*
            ----------------------------------------
            BOTÓN VOLVER
            ----------------------------------------
            */

            item {

                Spacer(
                    Modifier.height(26.dp)
                )

                OutlinedButton(
                    onClick = {
                        navController.navigate(
                            "inicio"
                        )
                    },

                    modifier = Modifier
                        .fillMaxWidth()
                        .height(58.dp),

                    shape =
                        RoundedCornerShape(18.dp),

                    colors =
                        ButtonDefaults
                            .outlinedButtonColors(
                                contentColor =
                                    Color.White
                            )
                ) {

                    Text(
                        text =
                            " Volver al inicio",

                        fontSize = 18.sp,

                        fontWeight =
                            FontWeight.Bold
                    )
                }

                Spacer(
                    Modifier.height(40.dp)
                )
            }
        }
    }
}
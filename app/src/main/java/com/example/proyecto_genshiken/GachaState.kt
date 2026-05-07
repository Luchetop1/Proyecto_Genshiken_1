package com.example.proyecto_genshiken

import android.content.Context
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object GachaState {

    var monedas = mutableStateOf(0)
    var espadasDesbloqueadas = mutableStateListOf<Int>()

    fun añadirEspada(id: Int, context: Context) {
        if (!espadasDesbloqueadas.contains(id)) {
            espadasDesbloqueadas.add(id)
            guardar(context)
        }
    }

    fun guardar(context: Context) {
        val prefs = context.getSharedPreferences("gacha", Context.MODE_PRIVATE)

        prefs.edit().apply {
            putInt("monedas", monedas.value)

            // 🔥 Convertimos lista a String
            putString(
                "espadas",
                espadasDesbloqueadas.joinToString(",")
            )

            apply()
        }
    }

    fun cargar(context: Context) {
        val prefs = context.getSharedPreferences("gacha", Context.MODE_PRIVATE)

        monedas.value = prefs.getInt("monedas", 0)

        val guardadas = prefs.getString("espadas", "") ?: ""

        espadasDesbloqueadas.clear()

        if (guardadas.isNotEmpty()) {
            espadasDesbloqueadas.addAll(
                guardadas.split(",").map { it.toInt() }
            )
        }
    }
}
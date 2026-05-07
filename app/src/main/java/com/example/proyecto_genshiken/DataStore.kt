package com.example.proyecto_genshiken

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(name = "gacha_data")

object DataStoreManager {

    private val MONEDAS = intPreferencesKey("monedas")
    private val ESPADAS = stringSetPreferencesKey("espadas")

    // con esta funcion suspendida se guardaran las monedas, esto afecta incluso cuando la app esta cerrada
    suspend fun saveMonedas(context: Context, monedas: Int) {
        context.dataStore.edit {
            it[MONEDAS] = monedas
        }
    }

    //  Con esta funcion podremos tener esas monedas disponibles
    fun getMonedas(context: Context): Flow<Int> {
        return context.dataStore.data.map {
            it[MONEDAS] ?: 0
        }
    }

    // con esta funcion suspendida se guardaran la coleccion de espadas que hayamos, esto afecta incluso cuando la app esta cerrada
    suspend fun saveEspadas(context: Context, espadas: Set<String>) {
        context.dataStore.edit {
            it[ESPADAS] = espadas
        }
    }

    // con esta funcion suspendida se guardara la coleccion, esto afecta incluso cuando la app esta cerrada
    fun getEspadas(context: Context): Flow<Set<String>> {
        return context.dataStore.data.map {
            it[ESPADAS] ?: emptySet()
        }
    }
}
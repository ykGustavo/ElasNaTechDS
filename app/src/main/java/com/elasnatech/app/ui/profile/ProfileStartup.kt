package com.elasnatech.app.ui.profile

import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.platform.LocalContext
import com.elasnatech.app.data.db.AppDatabase

/**
 * Descobre, uma única vez ao abrir o app, se já existe perfil salvo.
 * Retorna null enquanto está lendo o banco (mostre um loading), depois true ou false.
 *
 * Lê direto do DAO porque o ProfileViewModel.perfil começa como null e não dá para
 * diferenciar "ainda carregando" de "não existe perfil".
 */
@Composable
fun rememberHasProfile(): Boolean? {
    val context = LocalContext.current.applicationContext
    val state = produceState<Boolean?>(initialValue = null) {
        value = AppDatabase.getInstance(context).userProfileDao().getProfile() != null
    }
    return state.value
}

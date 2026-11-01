package com.elasnatech.app.ui.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.elasnatech.app.AppCard
import com.elasnatech.app.SectionTitle

private const val MAX_NAME = 30

/** Primeira tela do app (serve como onboarding): pede nome e avatar. */
@Composable
fun CreateProfileScreen(onCreate: (name: String, avatarId: Int) -> Unit) {
    var name by rememberSaveable { mutableStateOf("") }
    var avatarId by rememberSaveable { mutableIntStateOf(0) }

    Column(
        Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text(
            "Bem-vinda ao Elas na Tech!",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(Modifier.height(8.dp))
        Text(
            "Crie seu perfil para acompanhar seu nível e seu progresso.",
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(Modifier.height(24.dp))

        AppCard(Modifier.fillMaxWidth()) {
            Column(Modifier.padding(16.dp)) {
                SectionTitle("Seu nome")
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = name,
                    onValueChange = { if (it.length <= MAX_NAME) name = it },
                    label = { Text("Como podemos te chamar?") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(20.dp))
                SectionTitle("Escolha seu avatar")
                Spacer(Modifier.height(8.dp))
                AvatarPicker(selectedId = avatarId, onSelect = { avatarId = it })
            }
        }

        Spacer(Modifier.height(24.dp))
        Button(
            onClick = { onCreate(name.trim(), avatarId) },
            enabled = name.isNotBlank(),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Criar perfil", fontWeight = FontWeight.Bold)
        }
    }
}

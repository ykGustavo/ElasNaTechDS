package com.elasnatech.app.ui.profile

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.elasnatech.app.AppCard
import com.elasnatech.app.data.repository.UserProfile

/**
 * XP necessário por nível. É a mesma regra do repositório (calcularNivel = xp / 100 + 1).
 * Se o Dev 1 mudar a regra lá, mude aqui também.
 */
private const val XP_POR_NIVEL = 100

/**
 * @param nivel vem de ProfileViewModel.calcularNivelAtual(profile.xp), para não duplicar a regra do nível.
 */
@Composable
fun ProfileScreen(profile: UserProfile, nivel: Int) {
    val xpNoNivel = profile.xp % XP_POR_NIVEL
    val progresso by animateFloatAsState(
        targetValue = xpNoNivel / XP_POR_NIVEL.toFloat(),
        label = "xp"
    )

    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(16.dp))
        AvatarBadge(avatarId = profile.avatarId, size = 120.dp)
        Spacer(Modifier.height(12.dp))
        Text(
            profile.name,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(Modifier.height(20.dp))

        AppCard(Modifier.fillMaxWidth()) {
            Column(Modifier.padding(16.dp)) {
                Text(
                    "Nível $nivel",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(Modifier.height(12.dp))
                LinearProgressIndicator(
                    progress = { progresso },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(12.dp)
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    "$xpNoNivel / $XP_POR_NIVEL XP para o próximo nível",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    "Total: ${profile.xp} XP  •  🔥 ${profile.streak} dias seguidos",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}

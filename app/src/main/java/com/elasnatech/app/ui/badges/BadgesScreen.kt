package com.elasnatech.app.ui.badges

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.elasnatech.app.AppCard
import com.elasnatech.app.data.repository.EarnedBadge
import com.elasnatech.app.viewmodel.BadgesViewModel

/**
 * Modelo de apresentação visual de um emblema do aplicativo.
 */
data class BadgeItemUi(
    val id: String,
    val title: String,
    val description: String,
    val requirement: String,
    val icon: String,
    val isUnlocked: Boolean,
    val earnedDate: String? = null
)

/**
 * Catálogo estático dos emblemas do aplicativo Elas na Tech.
 * Alinhado rigorosamente aos IDs verificados nas regras de negócio da equipe:
 * "primeiro_quiz", "quiz_100_por_cento", "5_dias_seguidos", "nivel_5".
 */
val allAppBadgeDefinitions = listOf(
    BadgeItemUi(
        id = "primeiro_quiz",
        title = "Primeiro Passo",
        description = "Concluiu seu primeiro quiz na plataforma Elas na Tech.",
        requirement = "Finalize qualquer um dos quizzes temáticos.",
        icon = "🌱",
        isUnlocked = false
    ),
    BadgeItemUi(
        id = "quiz_100_por_cento",
        title = "Gabarito Perfeito",
        description = "Acertou 100% de todas as perguntas de um quiz.",
        requirement = "Obtenha nota máxima (8 de 8 acertos) em um quiz.",
        icon = "🎯",
        isUnlocked = false
    ),
    BadgeItemUi(
        id = "5_dias_seguidos",
        title = "Fogo Constante",
        description = "Manteve o hábito de aprendizado por 5 dias consecutivos.",
        requirement = "Atinja uma sequência (streak) ininterrupta de 5 dias.",
        icon = "🔥",
        isUnlocked = false
    ),
    BadgeItemUi(
        id = "nivel_5",
        title = "Mestre da Tecnologia",
        description = "Alcançou o nível 5 de experiência acumulada.",
        requirement = "Acumule pelo menos 400 XP completando quizzes.",
        icon = "👑",
        isUnlocked = false
    ),
    BadgeItemUi(
        id = "pioneiras_tech",
        title = "Guardiã das Pioneiras",
        description = "Dominou a história de Ada Lovelace, Grace Hopper e precursoras.",
        requirement = "Conclua o Quiz 1 (Pioneiras da Computação).",
        icon = "📜",
        isUnlocked = false
    ),
    BadgeItemUi(
        id = "mulheres_espaco",
        title = "Conquistadora Espacial",
        description = "Aprendeu sobre as matemáticas e engenheiras da NASA.",
        requirement = "Conclua o Quiz 2 (Ciência Espacial & Hardware).",
        icon = "🚀",
        isUnlocked = false
    ),
    BadgeItemUi(
        id = "redes_algoritmos",
        title = "Arquiteta de Redes",
        description = "Conheceu as inventoras de protocolos, circuitos e algoritmos.",
        requirement = "Conclua o Quiz 3 (Linguagens, Algoritmos & Redes).",
        icon = "🌐",
        isUnlocked = false
    ),
    BadgeItemUi(
        id = "tech_brasil",
        title = "Orgulho Brasileiro",
        description = "Explorou o impacto das cientistas e desenvolvedoras no Brasil.",
        requirement = "Conclua o Quiz 4 (Mulheres Tech no Brasil).",
        icon = "🇧🇷",
        isUnlocked = false
    )
)

private enum class BadgeFilter(val label: String) {
    ALL("Todos"),
    UNLOCKED("Conquistados"),
    LOCKED("Bloqueados")
}

@Composable
fun BadgesScreen(
    badgesViewModel: BadgesViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val earnedBadges by badgesViewModel.emblemasConquistados.collectAsState()

    var selectedFilter by rememberSaveable { mutableStateOf(BadgeFilter.ALL) }
    var selectedBadgeForDetails by remember { mutableStateOf<BadgeItemUi?>(null) }

    // Mapeia o catálogo visual com o banco Room através do BadgesViewModel
    val mappedBadges = remember(earnedBadges) {
        allAppBadgeDefinitions.map { badgeDef ->
            val earned = earnedBadges.find { it.badgeId == badgeDef.id }
            if (earned != null) {
                badgeDef.copy(isUnlocked = true, earnedDate = earned.earnedDate)
            } else {
                badgeDef.copy(isUnlocked = false, earnedDate = null)
            }
        }
    }

    val unlockedCount = mappedBadges.count { it.isUnlocked }
    val totalCount = mappedBadges.size

    val filteredBadges = remember(selectedFilter, mappedBadges) {
        when (selectedFilter) {
            BadgeFilter.ALL -> mappedBadges
            BadgeFilter.UNLOCKED -> mappedBadges.filter { it.isUnlocked }
            BadgeFilter.LOCKED -> mappedBadges.filter { !it.isUnlocked }
        }
    }

    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Card de Resumo da Coleção
        item {
            AppCard(Modifier.fillMaxWidth()) {
                Column(Modifier.padding(18.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                "Galeria de Conquistas",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Spacer(Modifier.height(4.dp))
                            Text(
                                "Você conquistou $unlockedCount de $totalCount emblemas",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                        Box(
                            modifier = Modifier
                                .size(54.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.secondaryContainer),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("🏅", fontSize = 26.sp)
                        }
                    }
                }
            }
        }

        // Filtros Rápidos (Todos / Conquistados / Bloqueados)
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                BadgeFilter.entries.forEach { filter ->
                    val isSelected = selectedFilter == filter
                    FilterChip(
                        selected = isSelected,
                        onClick = { selectedFilter = filter },
                        label = {
                            val count = when (filter) {
                                BadgeFilter.ALL -> totalCount
                                BadgeFilter.UNLOCKED -> unlockedCount
                                BadgeFilter.LOCKED -> totalCount - unlockedCount
                            }
                            Text("${filter.label} ($count)")
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MaterialTheme.colorScheme.primary,
                            selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                        )
                    )
                }
            }
        }

        // Lista de Cards de Emblemas
        items(filteredBadges, key = { it.id }) { badge ->
            BadgeItemCard(
                badge = badge,
                onClick = { selectedBadgeForDetails = badge }
            )
        }

        if (filteredBadges.isEmpty()) {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "Nenhum emblema encontrado nesta categoria.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                }
            }
        }
    }

    // Modal de Detalhes do Emblema ao Tocar
    selectedBadgeForDetails?.let { badge ->
        AlertDialog(
            onDismissRequest = { selectedBadgeForDetails = null },
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(if (badge.isUnlocked) badge.icon else "🔒", fontSize = 26.sp)
                    Spacer(Modifier.width(10.dp))
                    Text(badge.title, fontWeight = FontWeight.Bold)
                }
            },
            text = {
                Column {
                    Text(badge.description, style = MaterialTheme.typography.bodyMedium)
                    Spacer(Modifier.height(12.dp))
                    Text(
                        "Como conquistar:",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(badge.requirement, style = MaterialTheme.typography.bodyMedium)

                    Spacer(Modifier.height(12.dp))
                    if (badge.isUnlocked) {
                        Text(
                            "✅ Conquistado em: ${badge.earnedDate ?: "Recentemente"}",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF2E7D32)
                        )
                    } else {
                        Text(
                            "🔒 Emblema bloqueado. Conclua os desafios para desbloquear!",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                        )
                    }
                }
            },
            confirmButton = {
                if (badge.isUnlocked) {
                    Button(onClick = {
                        val intent = Intent(Intent.ACTION_SEND).apply {
                            type = "text/plain"
                            putExtra(
                                Intent.EXTRA_TEXT,
                                "Conquistei o emblema \"${badge.title}\" (${badge.icon}) no app Elas na Tech! Venha aprender sobre mulheres na tecnologia."
                            )
                        }
                        context.startActivity(Intent.createChooser(intent, "Compartilhar Conquista"))
                    }) {
                        Text("Compartilhar")
                    }
                } else {
                    Button(onClick = { selectedBadgeForDetails = null }) {
                        Text("Entendido")
                    }
                }
            },
            dismissButton = {
                if (badge.isUnlocked) {
                    TextButton(onClick = { selectedBadgeForDetails = null }) {
                        Text("Fechar")
                    }
                }
            }
        )
    }
}

@Composable
fun BadgeItemCard(
    badge: BadgeItemUi,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isUnlocked = badge.isUnlocked
    val contentAlpha = if (isUnlocked) 1.0f else 0.45f

    AppCard(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Círculo com Ícone (dourado/destaque se conquistado, cinza se bloqueado)
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
                    .background(
                        if (isUnlocked)
                            MaterialTheme.colorScheme.secondaryContainer
                        else
                            MaterialTheme.colorScheme.surfaceVariant
                    ),
                contentAlignment = Alignment.Center
            ) {
                if (isUnlocked) {
                    Text(badge.icon, fontSize = 28.sp)
                } else {
                    Text("🔒", fontSize = 22.sp, modifier = Modifier.alpha(0.8f))
                }
            }

            Spacer(Modifier.width(16.dp))

            // Textos informativos
            Column(
                modifier = Modifier
                    .weight(1f)
                    .alpha(contentAlpha)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        badge.title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    if (isUnlocked) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color(0xFFE8F5E9))
                                .padding(horizontal = 8.dp, vertical = 2.dp)
                        ) {
                            Text(
                                "Conquistado",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF2E7D32)
                            )
                        }
                    } else {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(MaterialTheme.colorScheme.surfaceVariant)
                                .padding(horizontal = 8.dp, vertical = 2.dp)
                        ) {
                            Text(
                                "Bloqueado",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }

                Spacer(Modifier.height(4.dp))
                Text(
                    badge.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(Modifier.height(6.dp))
                if (isUnlocked && badge.earnedDate != null) {
                    Text(
                        "Desbloqueado em: ${badge.earnedDate}",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.SemiBold
                    )
                } else {
                    Text(
                        "Requisito: ${badge.requirement}",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                }
            }
        }
    }
}

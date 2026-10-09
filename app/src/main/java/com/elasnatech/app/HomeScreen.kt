package com.elasnatech.app

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(onGoToChallenges: () -> Unit) {
    // Favoritos guardados só em memória. TODO: mover para ViewModel/DataStore para persistir.
    val favorites = remember { mutableStateListOf<Int>() }

    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Boas-vindas
        item {
            AppCard(Modifier.fillMaxWidth()) {
                Column(Modifier.padding(16.dp)) {
                    Text(
                        "Elas na Tech",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        "Este app mostra a importância das mulheres na tecnologia e ajuda quem quer " +
                            "entrar na área. Use as abas: Notícias para novidades, Apoio para redes e " +
                            "comunidades e Desafios para testar o que você aprendeu.",
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }

        // Carrossel de cientistas
        item { SectionTitle("Elas na Tech") }
        item {
            LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                items(MockData.scientists, key = { it.id }) { ScientistCard(it) }
            }
        }

        // Linha do tempo
        item { SectionTitle("Linha do tempo") }
        item {
            Column {
                MockData.timeline.forEachIndexed { index, event ->
                    TimelineItem(event, isLast = index == MockData.timeline.lastIndex)
                }
            }
        }

        // Vídeos
        item { SectionTitle("Indicações de Carreira") }
        items(MockData.videos, key = { it.id }) { video ->
            VideoCard(
                video = video,
                isFavorite = video.id in favorites,
                onToggleFavorite = {
                    if (video.id in favorites) favorites.remove(video.id) else favorites.add(video.id)
                }
            )
        }

        // Call to action
        item {
            Column(
                Modifier.fillMaxWidth().padding(top = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "Quanto você sabe sobre elas?",
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(Modifier.height(8.dp))
                Button(onClick = onGoToChallenges, modifier = Modifier.fillMaxWidth()) {
                    Text("Testar Conhecimentos", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
private fun Avatar(name: String) {
    // TODO: trocar por Image(painterResource(...)) quando tiverem as fotos.
    val initials = name.split(" ").mapNotNull { it.firstOrNull() }.take(2).joinToString("")
    Box(
        Modifier.size(72.dp).clip(CircleShape).background(MaterialTheme.colorScheme.primary),
        contentAlignment = Alignment.Center
    ) {
        Text(initials, color = MaterialTheme.colorScheme.onPrimary, fontWeight = FontWeight.Bold)
    }
}

@Composable
private fun ScientistCard(s: Scientist) {
    var expanded by rememberSaveable(s.id) { mutableStateOf(false) }
    AppCard(Modifier.width(260.dp).clickable { expanded = !expanded }) {
        Column(
            Modifier.padding(16.dp).animateContentSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Avatar(s.name)
            Spacer(Modifier.height(8.dp))
            Text(s.name, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
            Spacer(Modifier.height(4.dp))
            Text(
                s.shortBio,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            AnimatedVisibility(visible = expanded) {
                Column(Modifier.padding(top = 8.dp)) {
                    Text("Principais feitos", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                    s.achievements.forEach {
                        Text("• $it", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurface)
                    }
                    Spacer(Modifier.height(6.dp))
                    Text(
                        "“${s.quote}”",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
            if (!expanded) {
                Spacer(Modifier.height(6.dp))
                Text("Toque para ver mais", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.primary)
            }
        }
    }
}

@Composable
private fun TimelineItem(event: TimelineEvent, isLast: Boolean) {
    val lineColor = MaterialTheme.colorScheme.primary
    Row(Modifier.height(IntrinsicSize.Min)) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(Modifier.size(14.dp).clip(CircleShape).background(lineColor))
            if (!isLast) {
                Box(Modifier.width(2.dp).weight(1f).background(lineColor.copy(alpha = 0.4f)))
            }
        }
        Spacer(Modifier.width(12.dp))
        Column(Modifier.padding(bottom = 16.dp)) {
            Text(event.year, fontWeight = FontWeight.Bold, color = lineColor)
            Text(event.text, color = MaterialTheme.colorScheme.onBackground)
        }
    }
}

@Composable
private fun VideoCard(video: VideoItem, isFavorite: Boolean, onToggleFavorite: () -> Unit) {
    val uriHandler = LocalUriHandler.current
    val heartColor by animateColorAsState(
        targetValue = if (isFavorite) Color(0xFFE53935) else MaterialTheme.colorScheme.onSurface,
        label = "heart"
    )
    AppCard(
        Modifier.fillMaxWidth().clickable(enabled = video.url.isNotBlank()) {
            uriHandler.openUri(video.url)
        }
    ) {
        Row(Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(
                Modifier.size(48.dp).clip(CircleShape).background(MaterialTheme.colorScheme.secondaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Filled.PlayArrow, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
            }
            Spacer(Modifier.width(12.dp))
            Column(Modifier.weight(1f)) {
                Text(video.title, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                Text(video.channel, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurface)
            }
            IconButton(onClick = onToggleFavorite) {
                Icon(
                    imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                    contentDescription = "Favoritar",
                    tint = heartColor
                )
            }
        }
    }
}

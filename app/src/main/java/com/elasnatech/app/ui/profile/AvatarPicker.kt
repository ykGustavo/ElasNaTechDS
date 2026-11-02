package com.elasnatech.app.ui.profile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Avatares disponíveis (emojis, sem precisar de imagens).
 * O avatarId salvo no banco é o ÍNDICE desta lista. Não mude a ordem depois de publicar.
 */
val AVATARS = listOf("👩‍💻", "🚀", "🔬", "💡", "🌟", "🦄")

/** Círculo com o emoji do avatar. */
@Composable
fun AvatarBadge(avatarId: Int, size: Dp, modifier: Modifier = Modifier) {
    Box(
        modifier
            .size(size)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.secondaryContainer),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = AVATARS.getOrElse(avatarId) { AVATARS.first() },
            fontSize = (size.value * 0.5f).sp
        )
    }
}

/** Linha rolável para escolher o avatar. */
@Composable
fun AvatarPicker(selectedId: Int, onSelect: (Int) -> Unit, modifier: Modifier = Modifier) {
    LazyRow(modifier, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        itemsIndexed(AVATARS) { index, _ ->
            val selected = index == selectedId
            AvatarBadge(
                avatarId = index,
                size = 64.dp,
                modifier = Modifier
                    .border(
                        BorderStroke(
                            if (selected) 3.dp else 0.dp,
                            if (selected) MaterialTheme.colorScheme.primary
                            else MaterialTheme.colorScheme.surface
                        ),
                        CircleShape
                    )
                    .clickable { onSelect(index) }
            )
        }
    }
}

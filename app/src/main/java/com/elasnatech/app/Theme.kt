package com.elasnatech.app

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

val Purple = Color(0xFF6B4BB8)

private val LightColors = lightColorScheme(
    primary = Purple,
    onPrimary = Color.White,
    background = Color(0xFFF8F5FF),
    onBackground = Color(0xFF333333),
    surface = Color.White,
    onSurface = Color(0xFF333333),
    secondaryContainer = Color(0xFFE6DCF7),
    onSecondaryContainer = Color(0xFF2A1B52)
)

private val DarkColors = darkColorScheme(
    primary = Color(0xFFB79CFF), // roxo mais claro para ter contraste no escuro
    onPrimary = Color(0xFF2A1B52),
    background = Color(0xFF17122A),
    onBackground = Color(0xFFEDE8F7),
    surface = Color(0xFF241C3D),
    onSurface = Color(0xFFEDE8F7),
    secondaryContainer = Color(0xFF352A58),
    onSecondaryContainer = Color(0xFFEDE8F7)
)

@Composable
fun ElasNaTechTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColors else LightColors,
        content = content
    )
}

/** Fundo em degradê (lilás claro no modo claro). */
@Composable
fun GradientBackground(content: @Composable BoxScope.() -> Unit) {
    val colors = if (isSystemInDarkTheme())
        listOf(Color(0xFF17122A), Color(0xFF241C3D))
    else
        listOf(Color(0xFFF8F5FF), Color(0xFFEFE8FF))
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(colors)),
        content = content
    )
}

/** Card padrão do app: fundo da superfície, cantos de 20dp e sombra suave. */
@Composable
fun AppCard(
    modifier: Modifier = Modifier,
    containerColor: Color = MaterialTheme.colorScheme.surface,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = containerColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        content = content
    )
}

/** Título de seção padrão. */
@Composable
fun SectionTitle(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onBackground
    )
}

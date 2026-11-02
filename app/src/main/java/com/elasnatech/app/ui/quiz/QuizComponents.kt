package com.elasnatech.app.ui.quiz

import android.content.Intent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.elasnatech.app.AppCard

val CorrectGreen = Color(0xFF2E7D32)
val WrongRed = Color(0xFFC62828)

/**
 * Banner com o Streak atual, melhor streak e o progresso semanal (Segunda a Domingo).
 */
@Composable
fun QuizStreakBannerCard(
    streakProgress: StreakProgress,
    modifier: Modifier = Modifier
) {
    val progressRatio = (streakProgress.completedDaysOfWeek.toFloat() / streakProgress.totalDaysOfWeek)
        .coerceIn(0f, 1f)
    val animatedProgress by animateFloatAsState(targetValue = progressRatio, label = "weeklyRatio")

    AppCard(modifier = modifier.fillMaxWidth()) {
        Column(Modifier.padding(18.dp)) {
            // Linha do Topo: Streak em Destaque + Ícone
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "🔥 ${streakProgress.currentStreak} ${if (streakProgress.currentStreak == 1) "Dia Seguido!" else "Dias Seguidos!"}",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                    Spacer(Modifier.height(3.dp))
                    Text(
                        text = "Melhor sequência histórica: ${streakProgress.bestStreak} ${if (streakProgress.bestStreak == 1) "dia" else "dias"}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.75f)
                    )
                }

                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFFFF3E0)),
                    contentAlignment = Alignment.Center
                ) {
                    Text("🔥", fontSize = 26.sp)
                }
            }

            // Indicador de status do dia de hoje
            Spacer(Modifier.height(10.dp))
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(
                        if (streakProgress.isActiveToday) Color(0xFFE8F5E9)
                        else MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.6f)
                    )
                    .padding(horizontal = 10.dp, vertical = 6.dp)
            ) {
                Text(
                    text = if (streakProgress.isActiveToday) "✅ Quiz de hoje concluído! Streak mantido!"
                    else "🎯 Complete o quiz hoje para avançar no seu streak!",
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.SemiBold,
                    color = if (streakProgress.isActiveToday) CorrectGreen
                    else MaterialTheme.colorScheme.onSecondaryContainer
                )
            }

            Spacer(Modifier.height(16.dp))

            // Calendário Semanal (Bolinhas de Seg a Dom)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                streakProgress.daysOfWeek.forEach { day ->
                    WeekDayIndicator(day = day)
                }
            }

            Spacer(Modifier.height(16.dp))

            // Barra de Progresso Semanal
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Progresso Semanal",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    "${streakProgress.completedDaysOfWeek} de ${streakProgress.totalDaysOfWeek} dias",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(Modifier.height(6.dp))
            LinearProgressIndicator(
                progress = { animatedProgress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(RoundedCornerShape(4.dp))
            )
        }
    }
}

/**
 * Indicador circular individual de um dia da semana.
 */
@Composable
private fun WeekDayIndicator(day: DayProgress) {
    val isCompleted = day.isCompleted
    val isToday = day.isToday

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(
                    when {
                        isCompleted -> MaterialTheme.colorScheme.primary
                        isToday -> MaterialTheme.colorScheme.secondaryContainer
                        else -> MaterialTheme.colorScheme.surfaceVariant
                    }
                )
                .then(
                    if (isToday && !isCompleted) Modifier.border(2.dp, MaterialTheme.colorScheme.primary, CircleShape)
                    else Modifier
                ),
            contentAlignment = Alignment.Center
        ) {
            if (isCompleted) {
                Text(
                    "✓",
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            } else {
                Text(
                    day.dayName.take(1),
                    color = if (isToday) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
        Spacer(Modifier.height(4.dp))
        Text(
            text = day.dayName,
            style = MaterialTheme.typography.labelSmall,
            fontWeight = if (isToday) FontWeight.Bold else FontWeight.Normal,
            color = if (isToday) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
        )
    }
}

/**
 * Card para uma das 4 opções de resposta da pergunta.
 */
@Composable
fun QuizOptionCard(
    optionIndex: Int,
    text: String,
    correctIndex: Int,
    selectedIndex: Int?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isAnswered = selectedIndex != null
    val isCorrect = optionIndex == correctIndex
    val isSelected = optionIndex == selectedIndex

    val targetBackground = when {
        !isAnswered -> MaterialTheme.colorScheme.surface
        isCorrect -> CorrectGreen
        isSelected -> WrongRed
        else -> MaterialTheme.colorScheme.surface
    }

    val backgroundColor by animateColorAsState(targetBackground, label = "optionBg")
    val isHighlighted = isAnswered && (isCorrect || isSelected)
    val optionLetters = listOf("A", "B", "C", "D")
    val letter = optionLetters.getOrElse(optionIndex) { "?" }

    AppCard(
        modifier = modifier
            .fillMaxWidth()
            .clickable(enabled = !isAnswered, onClick = onClick),
        containerColor = backgroundColor
    ) {
        Row(
            modifier = Modifier
                .padding(14.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Letra da alternativa (A, B, C, D)
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(
                        if (isHighlighted) Color.White.copy(alpha = 0.25f)
                        else MaterialTheme.colorScheme.secondaryContainer
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = letter,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = if (isHighlighted) Color.White else MaterialTheme.colorScheme.primary
                )
            }

            Spacer(Modifier.width(12.dp))

            Text(
                text = text,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = if (isHighlighted) FontWeight.Bold else FontWeight.Normal,
                color = if (isHighlighted) Color.White else MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.weight(1f)
            )

            if (isAnswered) {
                if (isCorrect) {
                    Text("✓", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                } else if (isSelected) {
                    Text("✗", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                }
            }
        }
    }
}

/**
 * Card de feedback didático exibido logo após a usuária escolher uma resposta.
 */
@Composable
fun QuizFeedbackCard(
    isCorrect: Boolean,
    explanation: String,
    isLastQuestion: Boolean,
    onNext: () -> Unit,
    modifier: Modifier = Modifier
) {
    AppCard(modifier = modifier.fillMaxWidth()) {
        Column(Modifier.padding(16.dp)) {
            Text(
                text = if (isCorrect) "Correto! 🎉" else "Quase! ❌",
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleMedium,
                color = if (isCorrect) CorrectGreen else WrongRed
            )
            Spacer(Modifier.height(6.dp))
            Text(
                text = explanation,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(Modifier.height(14.dp))
            Button(
                onClick = onNext,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = if (isLastQuestion) "Ver Resultado" else "Próxima Pergunta",
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

/**
 * Modal / Dialog final exibindo o resumo do quiz, selo conquistado e botões de ação.
 */
@Composable
fun QuizResultDialog(
    score: Int,
    totalQuestions: Int,
    earnedBadge: String,
    currentStreak: Int,
    onRestart: () -> Unit,
    onFinish: () -> Unit
) {
    val context = LocalContext.current

    AlertDialog(
        onDismissRequest = onFinish,
        title = {
            Text("Desafio Concluído! 🎉", fontWeight = FontWeight.Bold)
        },
        text = {
            Column {
                Text(
                    text = "Você acertou $score de $totalQuestions perguntas!",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium
                )
                Spacer(Modifier.height(10.dp))
                Text(
                    text = "Selo alcançado: $earnedBadge",
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(Modifier.height(6.dp))
                Text(
                    text = "🔥 Sequência diária: $currentStreak ${if (currentStreak == 1) "dia" else "dias"}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = "+${score * 10} XP ganhos para o seu perfil!",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.secondary,
                    fontWeight = FontWeight.SemiBold
                )
            }
        },
        confirmButton = {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedButton(onClick = {
                    val intent = Intent(Intent.ACTION_SEND).apply {
                        type = "text/plain"
                        putExtra(
                            Intent.EXTRA_TEXT,
                            "Fiz $score/$totalQuestions no Quiz Elas na Tech e conquistei o selo $earnedBadge com um streak de $currentStreak dias! 👩‍💻✨"
                        )
                    }
                    context.startActivity(Intent.createChooser(intent, "Compartilhar Resultado"))
                }) {
                    Text("Compartilhar")
                }
                Button(onClick = onRestart) {
                    Text("Jogar Novamente")
                }
            }
        },
        dismissButton = {
            TextButton(onClick = onFinish) {
                Text("Concluir")
            }
        }
    )
}

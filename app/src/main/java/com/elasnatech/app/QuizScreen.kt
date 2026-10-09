package com.elasnatech.app

import android.content.Intent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

private val CorrectGreen = Color(0xFF2E7D32)
private val WrongRed = Color(0xFFC62828)

@Composable
fun QuizScreen() {
    val questions = MockData.quiz
    val context = LocalContext.current

    // Estados simples de UI. TODO: mover para um ViewModel quando o app crescer.
    var currentIndex by rememberSaveable { mutableIntStateOf(0) }
    var selected by rememberSaveable { mutableStateOf<Int?>(null) }
    var score by rememberSaveable { mutableIntStateOf(0) }
    var finished by rememberSaveable { mutableStateOf(false) }

    // Dados mockados do topo da tela
    val streakDays = 5
    val weeklyGoal = 5
    var missionsDone by rememberSaveable { mutableIntStateOf(3) }
    val weeklyProgress by animateFloatAsState(missionsDone / weeklyGoal.toFloat(), label = "weekly")

    val question = questions[currentIndex]
    val isLast = currentIndex == questions.lastIndex

    fun restart() {
        currentIndex = 0
        selected = null
        score = 0
        finished = false
    }

    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item { SectionTitle("Desafios") }

        // Streak + progresso semanal
        item {
            AppCard(Modifier.fillMaxWidth()) {
                Column(Modifier.padding(16.dp)) {
                    Text(
                        "🔥 $streakDays Dias Seguidos!",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(Modifier.height(8.dp))
                    Text("Progresso semanal: $missionsDone de $weeklyGoal missões", color = MaterialTheme.colorScheme.onSurface)
                    Spacer(Modifier.height(6.dp))
                    LinearProgressIndicator(progress = { weeklyProgress }, modifier = Modifier.fillMaxWidth())
                }
            }
        }

        // Pergunta
        item {
            AppCard(Modifier.fillMaxWidth()) {
                Column(Modifier.padding(16.dp)) {
                    Text(
                        "Pergunta ${currentIndex + 1} de ${questions.size}",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(question.question, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                }
            }
        }

        // Opções
        item {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                question.options.forEachIndexed { index, text ->
                    OptionCard(
                        text = text,
                        index = index,
                        correctIndex = question.correctIndex,
                        selected = selected,
                        onClick = {
                            if (selected == null) {
                                selected = index
                                if (index == question.correctIndex) score++
                            }
                        }
                    )
                }
            }
        }

        // Feedback + botão de avançar
        if (selected != null) {
            item {
                val correct = selected == question.correctIndex
                AppCard(Modifier.fillMaxWidth()) {
                    Column(Modifier.padding(16.dp)) {
                        Text(
                            if (correct) "Correto! 🎉" else "Quase! ❌",
                            fontWeight = FontWeight.Bold,
                            color = if (correct) CorrectGreen else WrongRed
                        )
                        Text(question.explanation, color = MaterialTheme.colorScheme.onSurface)
                        Spacer(Modifier.height(8.dp))
                        Button(
                            onClick = {
                                if (isLast) {
                                    finished = true
                                    missionsDone = minOf(weeklyGoal, missionsDone + 1)
                                } else {
                                    currentIndex++
                                    selected = null
                                }
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(if (isLast) "Ver resultado" else "Próxima", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }

    // Tela de nota final
    if (finished) {
        val badge = badgeFor(score, questions.size)
        AlertDialog(
            onDismissRequest = { restart() },
            title = { Text("Resultado", fontWeight = FontWeight.Bold) },
            text = {
                Column {
                    Text("Você acertou $score de ${questions.size}.")
                    Spacer(Modifier.height(8.dp))
                    Text("Selo conquistado: $badge", fontWeight = FontWeight.Bold)
                }
            },
            confirmButton = {
                Button(onClick = {
                    val intent = Intent(Intent.ACTION_SEND).apply {
                        type = "text/plain"
                        putExtra(
                            Intent.EXTRA_TEXT,
                            "Fiz $score/${questions.size} no quiz do app Elas na Tech e ganhei o selo $badge!"
                        )
                    }
                    context.startActivity(Intent.createChooser(intent, "Compartilhar resultado"))
                }) { Text("Compartilhar") }
            },
            dismissButton = { TextButton(onClick = { restart() }) { Text("Tentar de novo") } }
        )
    }
}

@Composable
private fun OptionCard(
    text: String,
    index: Int,
    correctIndex: Int,
    selected: Int?,
    onClick: () -> Unit
) {
    val answered = selected != null
    val targetColor = when {
        !answered -> MaterialTheme.colorScheme.surface
        index == correctIndex -> CorrectGreen
        index == selected -> WrongRed
        else -> MaterialTheme.colorScheme.surface
    }
    val background by animateColorAsState(targetColor, label = "option")
    val highlighted = answered && (index == correctIndex || index == selected)

    AppCard(
        modifier = Modifier.fillMaxWidth().clickable(enabled = !answered, onClick = onClick),
        containerColor = background
    ) {
        Row(Modifier.padding(16.dp)) {
            Text(
                text,
                fontWeight = FontWeight.Medium,
                color = if (highlighted) Color.White else MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

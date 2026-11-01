package com.elasnatech.app

import android.content.Intent
import androidx.compose.animation.animateColorAsState
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
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

private val CorrectGreen = Color(0xFF2E7D32)
private val WrongRed = Color(0xFFC62828)

private enum class ChallengesTab(val label: String) {
    CHALLENGES("Desafios & Quizzes"),
    BADGES("Coleção de Emblemas")
}

@Composable
fun QuizScreen() {
    var selectedTab by rememberSaveable { mutableStateOf(ChallengesTab.CHALLENGES) }
    var activeQuiz by remember { mutableStateOf<Dev5QuizTopicMock?>(null) }

    Column(modifier = Modifier.fillMaxWidth()) {
        // Seletor de sub-abas superior (Desafios & Quizzes vs Coleção de Emblemas)
        // Só exibe se não estiver no meio de um quiz ativo
        if (activeQuiz == null) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                ChallengesTab.entries.forEach { tab ->
                    FilterChip(
                        selected = selectedTab == tab,
                        onClick = { selectedTab = tab },
                        label = { Text(tab.label, fontWeight = FontWeight.SemiBold) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MaterialTheme.colorScheme.primary,
                            selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                        )
                    )
                }
            }
        }

        // Conteúdo da aba selecionada
        if (activeQuiz != null) {
            ActiveQuizView(
                quizTopic = activeQuiz!!,
                onCloseQuiz = { activeQuiz = null }
            )
        } else {
            when (selectedTab) {
                ChallengesTab.CHALLENGES -> {
                    ChallengesHomeView(
                        onSelectQuiz = { quiz -> activeQuiz = quiz }
                    )
                }
                ChallengesTab.BADGES -> {
                    BadgesScreen()
                }
            }
        }
    }
}

/**
 * Visão principal da aba Desafios (Streak, Missões e Lista dos 4 Quizzes).
 */
@Composable
private fun ChallengesHomeView(
    onSelectQuiz: (Dev5QuizTopicMock) -> Unit
) {
    val streakData = Dev5MockData.streak
    val missions = Dev5MockData.missions
    val quizzes = Dev5MockData.quizzes

    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item { SectionTitle("Desafios & Hábitos") }

        // Streak e Progresso Semanal
        item {
            StreakBannerCard(streak = streakData)
        }

        // Missões em Destaque
        item { SectionTitle("Missões em Andamento") }
        items(missions, key = { it.id }) { mission ->
            MissionItemCard(mission = mission)
        }

        // Catálogo dos 4 Quizzes
        item {
            Spacer(Modifier.height(4.dp))
            SectionTitle("Quizzes Temáticos")
        }
        items(quizzes, key = { it.id }) { quiz ->
            QuizTopicCard(
                quiz = quiz,
                onStartQuiz = { onSelectQuiz(quiz) }
            )
        }
    }
}

/**
 * Interface de Execução do Quiz selecionado (8 perguntas com feedback).
 */
@Composable
private fun ActiveQuizView(
    quizTopic: Dev5QuizTopicMock,
    onCloseQuiz: () -> Unit
) {
    val questions = quizTopic.questions
    val context = LocalContext.current

    var currentIndex by rememberSaveable(quizTopic.id) { mutableIntStateOf(0) }
    var selectedOption by rememberSaveable(quizTopic.id) { mutableStateOf<Int?>(null) }
    var score by rememberSaveable(quizTopic.id) { mutableIntStateOf(0) }
    var finished by rememberSaveable(quizTopic.id) { mutableStateOf(false) }

    val currentQuestion = questions[currentIndex]
    val isLast = currentIndex == questions.lastIndex

    fun restart() {
        currentIndex = 0
        selectedOption = null
        score = 0
        finished = false
    }

    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Cabeçalho do Quiz com botão de voltar
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(Modifier.weight(1f)) {
                    Text(
                        quizTopic.title,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        "Pergunta ${currentIndex + 1} de ${questions.size}",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                }
                TextButton(onClick = onCloseQuiz) {
                    Text("Voltar aos Desafios")
                }
            }
        }

        // Card com o Enunciado da Pergunta
        item {
            AppCard(Modifier.fillMaxWidth()) {
                Column(Modifier.padding(16.dp)) {
                    Text(
                        currentQuestion.question,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }

        // Alternativas
        item {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                currentQuestion.options.forEachIndexed { index, optionText ->
                    OptionCard(
                        text = optionText,
                        index = index,
                        correctIndex = currentQuestion.correctIndex,
                        selected = selectedOption,
                        onClick = {
                            if (selectedOption == null) {
                                selectedOption = index
                                if (index == currentQuestion.correctIndex) {
                                    score++
                                }
                            }
                        }
                    )
                }
            }
        }

        // Feedback Imediato e Botão Avançar
        if (selectedOption != null) {
            item {
                val isCorrect = selectedOption == currentQuestion.correctIndex
                AppCard(Modifier.fillMaxWidth()) {
                    Column(Modifier.padding(16.dp)) {
                        Text(
                            text = if (isCorrect) "Correto! 🎉" else "Quase! ❌",
                            fontWeight = FontWeight.Bold,
                            color = if (isCorrect) CorrectGreen else WrongRed
                        )
                        Spacer(Modifier.height(4.dp))
                        Text(
                            text = currentQuestion.explanation,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(Modifier.height(10.dp))
                        Button(
                            onClick = {
                                if (isLast) {
                                    finished = true
                                } else {
                                    currentIndex++
                                    selectedOption = null
                                }
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                if (isLast) "Ver Resultado" else "Próxima Pergunta",
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }

    // Modal de Resultado Final
    if (finished) {
        val earnedBadgeTitle = badgeFor(score, questions.size)
        AlertDialog(
            onDismissRequest = {
                restart()
                onCloseQuiz()
            },
            title = {
                Text("Desafio Concluído! 🎉", fontWeight = FontWeight.Bold)
            },
            text = {
                Column {
                    Text(
                        "Você acertou $score de ${questions.size} perguntas no quiz \"${quizTopic.title}\".",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(Modifier.height(10.dp))
                    Text(
                        "Selo alcançado: $earnedBadgeTitle",
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            },
            confirmButton = {
                Button(onClick = {
                    val intent = Intent(Intent.ACTION_SEND).apply {
                        type = "text/plain"
                        putExtra(
                            Intent.EXTRA_TEXT,
                            "Fiz $score/${questions.size} no quiz \"${quizTopic.title}\" do app Elas na Tech e ganhei o selo $earnedBadgeTitle!"
                        )
                    }
                    context.startActivity(Intent.createChooser(intent, "Compartilhar Resultado"))
                }) {
                    Text("Compartilhar")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    restart()
                    onCloseQuiz()
                }) {
                    Text("Concluir")
                }
            }
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
        modifier = Modifier
            .fillMaxWidth()
            .clickable(enabled = !answered, onClick = onClick),
        containerColor = background
    ) {
        Row(Modifier.padding(16.dp)) {
            Text(
                text = text,
                fontWeight = FontWeight.Medium,
                color = if (highlighted) Color.White else MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

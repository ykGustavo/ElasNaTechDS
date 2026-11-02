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
import androidx.compose.runtime.collectAsState
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.elasnatech.app.data.Quiz
import com.elasnatech.app.data.QuizData
import com.elasnatech.app.data.repository.ResultadoQuizModel
import com.elasnatech.app.ui.badges.BadgesScreen
import com.elasnatech.app.viewmodel.ProfileViewModel
import com.elasnatech.app.viewmodel.QuizViewModel
import java.util.Calendar

private val CorrectGreen = Color(0xFF2E7D32)
private val WrongRed = Color(0xFFC62828)

private enum class ChallengesTab(val label: String) {
    CHALLENGES("Desafios & Quizzes"),
    BADGES("Coleção de Emblemas")
}

@Composable
fun QuizScreen(
    profileViewModel: ProfileViewModel = viewModel(),
    quizViewModel: QuizViewModel = viewModel()
) {
    var selectedTab by rememberSaveable { mutableStateOf(ChallengesTab.CHALLENGES) }
    var activeQuiz by remember { mutableStateOf<Quiz?>(null) }

    Column(modifier = Modifier.fillMaxWidth()) {
        // Seletor de abas superior (Desafios & Quizzes vs Coleção de Emblemas)
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
                quiz = activeQuiz!!,
                quizViewModel = quizViewModel,
                onCloseQuiz = { activeQuiz = null }
            )
        } else {
            when (selectedTab) {
                ChallengesTab.CHALLENGES -> {
                    ChallengesHomeView(
                        profileViewModel = profileViewModel,
                        quizViewModel = quizViewModel,
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
 * Visão principal de Desafios conectada aos ViewModels reais.
 */
@Composable
private fun ChallengesHomeView(
    profileViewModel: ProfileViewModel,
    quizViewModel: QuizViewModel,
    onSelectQuiz: (Quiz) -> Unit
) {
    val perfil by profileViewModel.perfil.collectAsState()
    val historico by quizViewModel.historicoQuizzes.collectAsState()

    val currentStreak = perfil?.streak ?: 0
    val totalQuizzesCompleted = historico.size
    val distinctQuizzesCompleted = historico.map { it.quizId }.distinct().size
    val hasPerfectScore = historico.any { it.score == it.total && it.total > 0 }

    // Determina o dia atual da semana (0 = Seg, ..., 6 = Dom)
    val todayDayOfWeekIndex = remember {
        val cal = Calendar.getInstance()
        when (cal.get(Calendar.DAY_OF_WEEK)) {
            Calendar.MONDAY -> 0
            Calendar.TUESDAY -> 1
            Calendar.WEDNESDAY -> 2
            Calendar.THURSDAY -> 3
            Calendar.FRIDAY -> 4
            Calendar.SATURDAY -> 5
            Calendar.SUNDAY -> 6
            else -> 0
        }
    }

    val dayNames = listOf("Seg", "Ter", "Qua", "Qui", "Sex", "Sáb", "Dom")
    val daysOfWeek = remember(currentStreak, todayDayOfWeekIndex) {
        dayNames.mapIndexed { index, name ->
            val isToday = index == todayDayOfWeekIndex
            val isCompleted = if (index <= todayDayOfWeekIndex) {
                (todayDayOfWeekIndex - index) < currentStreak
            } else false
            DayStreakUi(dayName = name, isCompleted = isCompleted, isToday = isToday)
        }
    }

    // Missões conectadas ao estado real do usuário
    val missions = listOf(
        MissionUi(
            id = 1,
            title = "Primeiro Passo",
            description = "Finalize o seu primeiro quiz na plataforma.",
            currentProgress = minOf(1, totalQuizzesCompleted),
            totalRequired = 1,
            xpReward = 50,
            isCompleted = totalQuizzesCompleted >= 1
        ),
        MissionUi(
            id = 2,
            title = "Sequência em Chamas",
            description = "Atinja um streak de pelo menos 5 dias seguidos.",
            currentProgress = minOf(5, currentStreak),
            totalRequired = 5,
            xpReward = 150,
            isCompleted = currentStreak >= 5
        ),
        MissionUi(
            id = 3,
            title = "Exploradora de Temas",
            description = "Conclua pelo menos 3 quizzes temáticos diferentes.",
            currentProgress = minOf(3, distinctQuizzesCompleted),
            totalRequired = 3,
            xpReward = 120,
            isCompleted = distinctQuizzesCompleted >= 3
        ),
        MissionUi(
            id = 4,
            title = "Gabarito Perfeito",
            description = "Acerte todas as 8 perguntas de um quiz sem errar.",
            currentProgress = if (hasPerfectScore) 1 else 0,
            totalRequired = 1,
            xpReward = 100,
            isCompleted = hasPerfectScore
        )
    )

    val completedMissionsCount = missions.count { it.isCompleted }

    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item { SectionTitle("Desafios & Hábitos") }

        // Banner do Streak
        item {
            StreakBannerCard(
                currentStreak = currentStreak,
                completedMissionsCount = completedMissionsCount,
                totalMissionsGoal = missions.size,
                daysOfWeek = daysOfWeek
            )
        }

        // Lista de Missões
        item { SectionTitle("Missões em Andamento") }
        items(missions, key = { it.id }) { mission ->
            MissionItemCard(mission = mission)
        }

        // Quizzes Oficiais do QuizData
        item {
            Spacer(Modifier.height(4.dp))
            SectionTitle("Quizzes Temáticos")
        }
        items(QuizData.allQuizzes, key = { it.id }) { quiz ->
            QuizTopicCard(
                quiz = quiz,
                onStartQuiz = { onSelectQuiz(quiz) }
            )
        }
    }
}

/**
 * Interface de Execução do Quiz selecionado conectada ao Room via [QuizViewModel].
 */
@Composable
private fun ActiveQuizView(
    quiz: Quiz,
    quizViewModel: QuizViewModel,
    onCloseQuiz: () -> Unit
) {
    val questions = quiz.questions
    val context = LocalContext.current

    var currentIndex by rememberSaveable(quiz.id) { mutableIntStateOf(0) }
    var selectedOption by rememberSaveable(quiz.id) { mutableStateOf<Int?>(null) }
    var score by rememberSaveable(quiz.id) { mutableIntStateOf(0) }
    var finished by rememberSaveable(quiz.id) { mutableStateOf(false) }
    var resultadoFinal by remember { mutableStateOf<ResultadoQuizModel?>(null) }

    val currentQuestion = questions[currentIndex]
    val isLast = currentIndex == questions.lastIndex

    fun restart() {
        currentIndex = 0
        selectedOption = null
        score = 0
        finished = false
        resultadoFinal = null
    }

    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Cabeçalho do Quiz
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(Modifier.weight(1f)) {
                    Text(
                        quiz.title,
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

        // Card do Enunciado
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
                                    // Salva no banco de dados Room via ViewModel
                                    quizViewModel.finalizarQuiz(
                                        quizId = quiz.id,
                                        acertos = score,
                                        totalPerguntas = questions.size
                                    ) { resultado ->
                                        resultadoFinal = resultado
                                        finished = true
                                    }
                                } else {
                                    currentIndex++
                                    selectedOption = null
                                }
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                if (isLast) "Finalizar Desafio" else "Próxima Pergunta",
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }

    // Modal de Resultado Final com Persistência Real
    if (finished) {
        val earnedBadgeTitle = badgeFor(score, questions.size)
        val xpGanho = resultadoFinal?.xpGanho ?: (score * 10)
        val novosEmblemas = resultadoFinal?.emblemasNovos.orEmpty()
        val subiuNivel = resultadoFinal?.subiuDeNivel == true
        val novoNivel = resultadoFinal?.novoNivel

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
                        "Você acertou $score de ${questions.size} perguntas no quiz \"${quiz.title}\".",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        "Selo alcançado: $earnedBadgeTitle",
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        "Pontos: +$xpGanho XP adicionados ao seu perfil!",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.SemiBold
                    )

                    if (subiuNivel && novoNivel != null) {
                        Spacer(Modifier.height(6.dp))
                        Text(
                            "⭐ Parabéns! Você subiu para o Nível $novoNivel!",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }

                    if (novosEmblemas.isNotEmpty()) {
                        Spacer(Modifier.height(6.dp))
                        Text(
                            "🏆 Novo emblema desbloqueado na sua galeria!",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF2E7D32)
                        )
                    }
                }
            },
            confirmButton = {
                Button(onClick = {
                    val intent = Intent(Intent.ACTION_SEND).apply {
                        type = "text/plain"
                        putExtra(
                            Intent.EXTRA_TEXT,
                            "Fiz $score/${questions.size} no quiz \"${quiz.title}\" do app Elas na Tech e ganhei o selo $earnedBadgeTitle! (+${xpGanho} XP)"
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

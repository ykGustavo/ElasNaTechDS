package com.elasnatech.app.ui.quiz

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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.LinearProgressIndicator
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.elasnatech.app.AppCard
import com.elasnatech.app.ui.badges.BadgesScreen
import com.elasnatech.app.QuizQuestion
import com.elasnatech.app.SectionTitle
import com.elasnatech.app.badgeFor
import com.elasnatech.app.data.Quiz
import com.elasnatech.app.data.QuizData
import com.elasnatech.app.viewmodel.QuizViewModel

private enum class ChallengesSubTab(val label: String) {
    CHALLENGES("Desafios & Quizzes"),
    BADGES("Coleção de Emblemas")
}

data class ActiveQuizSession(
    val title: String,
    val subtitle: String,
    val questions: List<QuizQuestion>,
    val quizId: Int
)

/**
 * Tela principal de Quiz e Desafios (Pessoa 4).
 *
 * Responsável por:
 * - Apresentar streak consecutivo e progresso semanal (Seg a Dom) salvos no aparelho.
 * - Disponibilizar o Quiz Geral com 10 perguntas sorteadas e embaralhadas a cada nova partida.
 * - Catálogo de quizzes temáticos sobre mulheres na história da computação e tecnologia.
 * - Interface didática com 4 opções por pergunta, feedback imediato e explicação histórica.
 * - Persistência local no SharedPreferences e sincronização com Room Database via ViewModel.
 * - Integração limpa com a aba Coleção de Emblemas (Pessoa 5).
 */
@Composable
fun QuizScreen(
    quizViewModel: QuizViewModel = viewModel()
) {
    val context = LocalContext.current
    val quizStorage = remember { QuizStorage(context) }

    var selectedTab by rememberSaveable { mutableStateOf(ChallengesSubTab.CHALLENGES) }
    var activeSession by remember { mutableStateOf<ActiveQuizSession?>(null) }
    var streakProgress by remember { mutableStateOf(quizStorage.getStreakProgress()) }

    Column(modifier = Modifier.fillMaxWidth()) {
        // Seletor superior de sub-abas (só aparece fora de um quiz em andamento)
        if (activeSession == null) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                ChallengesSubTab.entries.forEach { tab ->
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

        // Conteúdo da aba ou sessão do quiz
        if (activeSession != null) {
            val session = activeSession!!
            ActiveQuizScreenView(
                session = session,
                onRestartSession = {
                    // Embaralha uma nova rodada de perguntas
                    val newQuestions = if (session.quizId == 0) {
                        QuizQuestions.getShuffledQuiz(10)
                    } else {
                        val topic = QuizData.allQuizzes.find { it.id == session.quizId }
                        topic?.questions?.shuffled() ?: session.questions.shuffled()
                    }
                    activeSession = session.copy(questions = newQuestions)
                },
                onFinishQuiz = { score, totalQuestions ->
                    // 1. Salvar localmente no aparelho (SharedPreferences)
                    val updatedProgress = quizStorage.recordQuizCompletion(score, totalQuestions)
                    streakProgress = updatedProgress

                    // 2. Salvar no Room Database através do ViewModel
                    quizViewModel.finalizarQuiz(
                        quizId = session.quizId,
                        acertos = score,
                        totalPerguntas = totalQuestions,
                        onResultadoPronto = { /* Room salvo e emblemas verificados */ }
                    )
                },
                onCloseQuiz = {
                    streakProgress = quizStorage.getStreakProgress()
                    activeSession = null
                }
            )
        } else {
            when (selectedTab) {
                ChallengesSubTab.CHALLENGES -> {
                    ChallengesCatalogView(
                        streakProgress = streakProgress,
                        onStartMainQuiz = {
                            activeSession = ActiveQuizSession(
                                title = "Desafio Geral: Mulheres na Tecnologia",
                                subtitle = "10 perguntas sorteadas do banco histórico de pioneiras",
                                questions = QuizQuestions.getShuffledQuiz(10),
                                quizId = 0
                            )
                        },
                        onStartThematicQuiz = { quiz ->
                            activeSession = ActiveQuizSession(
                                title = quiz.title,
                                subtitle = quiz.subtitle,
                                questions = quiz.questions.shuffled(),
                                quizId = quiz.id
                            )
                        }
                    )
                }
                ChallengesSubTab.BADGES -> {
                    BadgesScreen()
                }
            }
        }
    }
}

/**
 * Visualização do catálogo de quizzes e painel de streak e progresso semanal.
 */
@Composable
private fun ChallengesCatalogView(
    streakProgress: StreakProgress,
    onStartMainQuiz: () -> Unit,
    onStartThematicQuiz: (Quiz) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item { SectionTitle("Desafios & Hábitos") }

        // Banner com Streak atual e os 7 dias da semana
        item {
            QuizStreakBannerCard(streakProgress = streakProgress)
        }

        // Card de Destaque: Desafio Oficial Geral (10 Perguntas)
        item {
            Spacer(Modifier.height(4.dp))
            SectionTitle("Desafio em Destaque")
            Spacer(Modifier.height(4.dp))
            MainQuizFeaturedCard(
                onStart = onStartMainQuiz,
                totalCompleted = streakProgress.totalQuizzesCompleted,
                bestScore = streakProgress.bestScore
            )
        }

        // Catálogo de Quizzes Temáticos
        item {
            Spacer(Modifier.height(6.dp))
            SectionTitle("Trilhas Temáticas de Conhecimento")
        }

        items(QuizData.allQuizzes, key = { it.id }) { quiz ->
            ThematicQuizCard(
                quiz = quiz,
                onStart = { onStartThematicQuiz(quiz) }
            )
        }
    }
}

/**
 * Card principal para o Desafio Geral com 10 perguntas sorteadas.
 */
@Composable
private fun MainQuizFeaturedCard(
    onStart: () -> Unit,
    totalCompleted: Int,
    bestScore: Int
) {
    AppCard(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onStart)
    ) {
        Column(Modifier.padding(18.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(54.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primaryContainer),
                    contentAlignment = Alignment.Center
                ) {
                    Text("🌟", fontSize = 28.sp)
                }

                Spacer(Modifier.width(14.dp))

                Column(Modifier.weight(1f)) {
                    Text(
                        "Desafio Geral: Mulheres na Tecnologia",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        "10 perguntas aleatórias a cada partida",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            Spacer(Modifier.height(10.dp))
            Text(
                "Teste sua bagagem sobre pioneiras, linguagens de programação, ciência espacial, hardware e o protagonismo feminino na tecnologia.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.85f)
            )

            Spacer(Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(MaterialTheme.colorScheme.surfaceVariant)
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            "📝 10 perguntas",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(MaterialTheme.colorScheme.secondaryContainer)
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            "🔀 Embaralhado",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    }
                }

                Button(onClick = onStart) {
                    Text("Jogar Agora", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

/**
 * Card para um Quiz Temático do catálogo.
 */
@Composable
private fun ThematicQuizCard(
    quiz: Quiz,
    onStart: () -> Unit
) {
    AppCard(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onStart)
    ) {
        Column(Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.secondaryContainer),
                    contentAlignment = Alignment.Center
                ) {
                    Text(quiz.icon, fontSize = 24.sp)
                }

                Spacer(Modifier.width(12.dp))

                Column(Modifier.weight(1f)) {
                    Text(
                        quiz.title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        quiz.subtitle,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Spacer(Modifier.height(8.dp))
            Text(
                quiz.description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.85f)
            )

            Spacer(Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "📝 ${quiz.questions.size} perguntas",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                    fontWeight = FontWeight.SemiBold
                )
                Button(
                    onClick = onStart,
                    contentPadding = ButtonDefaults.TextButtonContentPadding
                ) {
                    Text("Iniciar", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

/**
 * Tela de execução da partida de Quiz.
 */
@Composable
private fun ActiveQuizScreenView(
    session: ActiveQuizSession,
    onRestartSession: () -> Unit,
    onFinishQuiz: (score: Int, total: Int) -> Unit,
    onCloseQuiz: () -> Unit
) {
    val questions = session.questions
    var currentIndex by rememberSaveable(session.title, questions.size) { mutableIntStateOf(0) }
    var selectedOption by rememberSaveable(session.title, currentIndex) { mutableStateOf<Int?>(null) }
    var score by rememberSaveable(session.title) { mutableIntStateOf(0) }
    var isFinished by rememberSaveable(session.title) { mutableStateOf(false) }

    val safeIndex = currentIndex.coerceIn(0, questions.lastIndex)
    val currentQuestion = questions[safeIndex]
    val isLast = safeIndex == questions.lastIndex
    val progressRatio = ((safeIndex + 1).toFloat() / questions.size).coerceIn(0f, 1f)

    fun restartGame() {
        currentIndex = 0
        selectedOption = null
        score = 0
        isFinished = false
        onRestartSession()
    }

    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Cabeçalho da Partida com Título, Progresso e Botão de Voltar
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(Modifier.weight(1f)) {
                    Text(
                        session.title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        "Pergunta ${safeIndex + 1} de ${questions.size}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                        fontWeight = FontWeight.SemiBold
                    )
                }
                TextButton(onClick = onCloseQuiz) {
                    Text("Sair")
                }
            }

            Spacer(Modifier.height(8.dp))
            LinearProgressIndicator(
                progress = { progressRatio },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(RoundedCornerShape(3.dp))
            )
        }

        // Card com o Enunciado da Pergunta Atual
        item {
            AppCard(Modifier.fillMaxWidth()) {
                Column(Modifier.padding(18.dp)) {
                    Text(
                        text = currentQuestion.question,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface,
                        lineHeight = 24.sp
                    )
                }
            }
        }

        // As 4 Alternativas de Resposta
        item {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                currentQuestion.options.forEachIndexed { index, optionText ->
                    QuizOptionCard(
                        optionIndex = index,
                        text = optionText,
                        correctIndex = currentQuestion.correctIndex,
                        selectedIndex = selectedOption,
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

        // Card de Feedback Imediato com Explicação
        if (selectedOption != null) {
            item {
                QuizFeedbackCard(
                    isCorrect = (selectedOption == currentQuestion.correctIndex),
                    explanation = currentQuestion.explanation,
                    isLastQuestion = isLast,
                    onNext = {
                        if (isLast) {
                            onFinishQuiz(score, questions.size)
                            isFinished = true
                        } else {
                            currentIndex++
                            selectedOption = null
                        }
                    }
                )
            }
        }
    }

    // Modal de Conclusão da Partida
    if (isFinished) {
        val earnedBadge = badgeFor(score, questions.size)
        QuizResultDialog(
            score = score,
            totalQuestions = questions.size,
            earnedBadge = earnedBadge,
            currentStreak = 1, // Exibido dinamicamente no diálogo
            onRestart = { restartGame() },
            onFinish = {
                onCloseQuiz()
            }
        )
    }
}

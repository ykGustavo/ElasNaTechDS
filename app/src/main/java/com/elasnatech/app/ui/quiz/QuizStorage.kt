package com.elasnatech.app.ui.quiz

import android.content.Context
import android.content.SharedPreferences
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

/**
 * Informações de um dia específico na semana corrente.
 */
data class DayProgress(
    val dayName: String,     // "Seg", "Ter", "Qua", "Qui", "Sex", "Sáb", "Dom"
    val date: String,        // "yyyy-MM-dd"
    val isCompleted: Boolean,
    val isToday: Boolean
)

/**
 * Estado completo do Streak e Progresso Semanal salvo localmente no aparelho.
 */
data class StreakProgress(
    val currentStreak: Int,
    val bestStreak: Int,
    val isActiveToday: Boolean,
    val lastCompletedDate: String,
    val completedDaysOfWeek: Int,
    val totalDaysOfWeek: Int = 7,
    val daysOfWeek: List<DayProgress>,
    val totalQuizzesCompleted: Int,
    val bestScore: Int
)

/**
 * Gerenciador de persistência local para o Quiz e Desafios (Pessoa 4).
 * Salva no SharedPreferences local do aparelho de forma persistente,
 * garantindo que os dados permaneçam salvos mesmo após fechar e reabrir o app.
 */
class QuizStorage(private val context: Context) {

    private val prefs: SharedPreferences by lazy {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    companion object {
        const val PREFS_NAME = "elasnatech_quiz_prefs"
        private const val KEY_CURRENT_STREAK = "quiz_current_streak"
        private const val KEY_BEST_STREAK = "quiz_best_streak"
        private const val KEY_LAST_DATE = "quiz_last_completed_date"
        private const val KEY_COMPLETED_DATES = "quiz_completed_dates"
        private const val KEY_TOTAL_COMPLETED = "quiz_total_completed"
        private const val KEY_BEST_SCORE = "quiz_best_score"

        val DATE_FORMAT = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        fun formatToday(date: Date = Date()): String = DATE_FORMAT.format(date)

        /**
         * Retorna a data anterior (ontem) no formato "yyyy-MM-dd".
         */
        fun getYesterday(referenceDateStr: String): String {
            return try {
                val parsed = DATE_FORMAT.parse(referenceDateStr) ?: return ""
                val cal = Calendar.getInstance().apply {
                    time = parsed
                    add(Calendar.DAY_OF_YEAR, -1)
                }
                DATE_FORMAT.format(cal.time)
            } catch (e: Exception) {
                ""
            }
        }

        /**
         * Gera os 7 dias da semana (de Segunda a Domingo) para a data de referência informada.
         */
        fun generateWeekDays(
            referenceDateStr: String,
            completedDates: Set<String>
        ): List<DayProgress> {
            val parsedDate = try {
                DATE_FORMAT.parse(referenceDateStr) ?: Date()
            } catch (e: Exception) {
                Date()
            }

            val cal = Calendar.getInstance().apply {
                time = parsedDate
            }

            // No Calendar do Java: Domingo = 1, Segunda = 2 ... Sábado = 7
            val dayOfWeek = cal.get(Calendar.DAY_OF_WEEK)
            val daysFromMonday = if (dayOfWeek == Calendar.SUNDAY) 6 else dayOfWeek - Calendar.MONDAY
            cal.add(Calendar.DAY_OF_YEAR, -daysFromMonday)

            val dayLabels = listOf("Seg", "Ter", "Qua", "Qui", "Sex", "Sáb", "Dom")
            val list = mutableListOf<DayProgress>()

            for (i in 0 until 7) {
                val dateString = DATE_FORMAT.format(cal.time)
                list.add(
                    DayProgress(
                        dayName = dayLabels[i],
                        date = dateString,
                        isCompleted = completedDates.contains(dateString),
                        isToday = (dateString == referenceDateStr)
                    )
                )
                cal.add(Calendar.DAY_OF_YEAR, 1)
            }

            return list
        }
    }

    /**
     * Obtém o progresso atual consolidado do Streak e da Semana.
     */
    fun getStreakProgress(todayDate: String = formatToday()): StreakProgress {
        val lastDate = prefs.getString(KEY_LAST_DATE, "") ?: ""
        val savedStreak = prefs.getInt(KEY_CURRENT_STREAK, 0)
        val bestStreak = prefs.getInt(KEY_BEST_STREAK, 0)
        val completedDates = getCompletedDates()
        val totalCompleted = prefs.getInt(KEY_TOTAL_COMPLETED, 0)
        val bestScore = prefs.getInt(KEY_BEST_SCORE, 0)

        val yesterday = getYesterday(todayDate)
        val isActiveToday = (lastDate == todayDate)

        // Se jogou hoje ou ontem, o streak segue válido. Se faz mais de 1 dia, quebrou e vai para 0.
        val effectiveStreak = when {
            lastDate.isEmpty() -> 0
            lastDate == todayDate -> savedStreak
            lastDate == yesterday -> savedStreak
            else -> 0
        }

        val weekDays = generateWeekDays(todayDate, completedDates)
        val completedDaysCount = weekDays.count { it.isCompleted }

        return StreakProgress(
            currentStreak = effectiveStreak,
            bestStreak = bestStreak,
            isActiveToday = isActiveToday,
            lastCompletedDate = lastDate,
            completedDaysOfWeek = completedDaysCount,
            totalDaysOfWeek = 7,
            daysOfWeek = weekDays,
            totalQuizzesCompleted = totalCompleted,
            bestScore = bestScore
        )
    }

    /**
     * Registra o término de uma partida de quiz com pontuação e atualiza streak e dias semanais.
     */
    fun recordQuizCompletion(
        score: Int,
        totalQuestions: Int,
        todayDate: String = formatToday()
    ): StreakProgress {
        val lastDate = prefs.getString(KEY_LAST_DATE, "") ?: ""
        val savedStreak = prefs.getInt(KEY_CURRENT_STREAK, 0)
        var bestStreak = prefs.getInt(KEY_BEST_STREAK, 0)
        val totalCompleted = prefs.getInt(KEY_TOTAL_COMPLETED, 0) + 1
        val previousBestScore = prefs.getInt(KEY_BEST_SCORE, 0)
        val newBestScore = maxOf(previousBestScore, score)

        val yesterday = getYesterday(todayDate)

        val newStreak = when {
            lastDate == todayDate -> savedStreak // Já jogou hoje, não incrementa de novo no mesmo dia
            lastDate == yesterday -> savedStreak + 1 // Jogou em dias consecutivos!
            else -> 1 // Primeiro dia ou streak quebrado
        }

        bestStreak = maxOf(bestStreak, newStreak)

        val updatedDates = getCompletedDates().toMutableSet().apply {
            add(todayDate)
        }

        prefs.edit()
            .putInt(KEY_CURRENT_STREAK, newStreak)
            .putInt(KEY_BEST_STREAK, bestStreak)
            .putString(KEY_LAST_DATE, todayDate)
            .putStringSet(KEY_COMPLETED_DATES, updatedDates)
            .putInt(KEY_TOTAL_COMPLETED, totalCompleted)
            .putInt(KEY_BEST_SCORE, newBestScore)
            .apply()

        val weekDays = generateWeekDays(todayDate, updatedDates)
        val completedDaysCount = weekDays.count { it.isCompleted }

        return StreakProgress(
            currentStreak = newStreak,
            bestStreak = bestStreak,
            isActiveToday = true,
            lastCompletedDate = todayDate,
            completedDaysOfWeek = completedDaysCount,
            totalDaysOfWeek = 7,
            daysOfWeek = weekDays,
            totalQuizzesCompleted = totalCompleted,
            bestScore = newBestScore
        )
    }

    private fun getCompletedDates(): Set<String> {
        return prefs.getStringSet(KEY_COMPLETED_DATES, emptySet()) ?: emptySet()
    }

    /**
     * Limpa os dados salvos do quiz (útil para testes ou reset).
     */
    fun clear() {
        prefs.edit().clear().apply()
    }
}

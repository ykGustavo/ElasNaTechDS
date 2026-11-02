package com.elasnatech.app

import com.elasnatech.app.ui.quiz.QuizQuestions
import com.elasnatech.app.ui.quiz.QuizStorage
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Testes unitários para o módulo Quiz e Desafios (Pessoa 4).
 * Valida:
 * - Quantidade de perguntas (mínimo 10).
 * - Estrutura de cada pergunta (4 alternativas e 1 resposta correta).
 * - Embaralhamento de perguntas.
 * - Regras de streak (consecutivo, mesmo dia, dia pulado).
 * - Progresso semanal (7 dias de Segunda a Domingo).
 * - Atribuição de selos/badges conforme a pontuação.
 */
class QuizUnitTest {

    @Test
    fun quizQuestions_hasAtLeastTenQuestions() {
        val questions = QuizQuestions.allQuestions
        assertTrue("O banco de perguntas deve conter pelo menos 10 perguntas", questions.size >= 10)
    }

    @Test
    fun quizQuestions_eachHasFourOptionsAndValidCorrectIndex() {
        val questions = QuizQuestions.allQuestions
        for (q in questions) {
            assertEquals("Cada pergunta deve conter exatamente 4 alternativas", 4, q.options.size)
            assertTrue("O índice correto deve estar entre 0 e 3", q.correctIndex in 0..3)
            assertTrue("A pergunta não pode estar vazia", q.question.isNotBlank())
            assertTrue("A explicação não pode estar vazia", q.explanation.isNotBlank())
            for (option in q.options) {
                assertTrue("A opção de resposta não pode estar vazia", option.isNotBlank())
            }
        }
    }

    @Test
    fun quizQuestions_shuffledReturnsRequestedCount() {
        val shuffled10 = QuizQuestions.getShuffledQuiz(10)
        assertEquals(10, shuffled10.size)
    }

    @Test
    fun quizQuestions_shuffledOrderVaries() {
        // Testa que duas chamadas ao gerador de quiz embaralhado conseguem ordens distintas
        var hasDifferentOrder = false
        val first = QuizQuestions.getShuffledQuiz(10)
        for (attempt in 1..20) {
            val second = QuizQuestions.getShuffledQuiz(10)
            if (first.map { it.question } != second.map { it.question }) {
                hasDifferentOrder = true
                break
            }
        }
        assertTrue("A ordem das perguntas deve ser embaralhada aleatoriamente", hasDifferentOrder)
    }

    @Test
    fun weeklyDays_generatesSevenDaysFromMondayToSunday() {
        val completedDates = setOf("2026-11-02", "2026-11-04")
        // 2026-11-04 é uma Quarta-feira
        val weekDays = QuizStorage.generateWeekDays("2026-11-04", completedDates)

        assertEquals("A semana deve conter 7 dias", 7, weekDays.size)
        val expectedLabels = listOf("Seg", "Ter", "Qua", "Qui", "Sex", "Sáb", "Dom")
        assertEquals(expectedLabels, weekDays.map { it.dayName })

        // Verifica marcação de hoje e de dias completados
        val qua = weekDays.find { it.dayName == "Qua" }
        assertNotNull(qua)
        assertTrue(qua!!.isToday)
        assertTrue(qua.isCompleted)

        val seg = weekDays.find { it.dayName == "Seg" }
        assertNotNull(seg)
        assertFalse(seg!!.isToday)
        assertTrue(seg.isCompleted)

        val ter = weekDays.find { it.dayName == "Ter" }
        assertNotNull(ter)
        assertFalse(ter!!.isToday)
        assertFalse(ter.isCompleted)
    }

    @Test
    fun streak_getYesterdayReturnsCorrectDate() {
        assertEquals("2026-11-01", QuizStorage.getYesterday("2026-11-02"))
        assertEquals("2026-10-31", QuizStorage.getYesterday("2026-11-01"))
    }

    @Test
    fun badgeCalculation_assignsCorrectTitles() {
        // 80% ou mais: Especialista
        assertEquals("🏆 Especialista", badgeFor(10, 10))
        assertEquals("🏆 Especialista", badgeFor(8, 10))

        // 40% a 79%: Exploradora
        assertEquals("🌟 Exploradora", badgeFor(7, 10))
        assertEquals("🌟 Exploradora", badgeFor(4, 10))

        // Menos de 40%: Iniciante
        assertEquals("🌱 Iniciante", badgeFor(3, 10))
        assertEquals("🌱 Iniciante", badgeFor(0, 10))
    }
}

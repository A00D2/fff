package com.example.myapplication

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView

class Screen2Activity : Activity() {

    private var questionIndex = 0
    private lateinit var questionTextView: TextView
    private lateinit var button1: Button
    private lateinit var button2: Button
    private lateinit var button3: Button
    private lateinit var resultTextView: TextView
    private lateinit var attemptsTextView: TextView
    private lateinit var homeButton: Button  // Кнопка "Домой"
    private lateinit var levelCompleteTextView: TextView  // Сообщение "Вы прошли первый уровень"

    private var remainingAttempts = 2
    private lateinit var sharedPreferences: SharedPreferences

    private val questions = listOf(
        Question("Что 2 + 2?", listOf("3", "4", "5"), 1),
        Question("Какая столица Франции?", listOf("Лондон", "Париж", "Берлин"), 1),
        Question("Сколько континентов на Земле?", listOf("5", "6", "7"), 2)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Инициализация SharedPreferences
        sharedPreferences = getSharedPreferences("GamePrefs", Context.MODE_PRIVATE)
        remainingAttempts = sharedPreferences.getInt("remainingAttempts", 2) // Получаем сохраненное количество жизней

        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
        }

        questionTextView = TextView(this).apply {
            textSize = 20f
            setPadding(20, 50, 20, 20)
        }

        resultTextView = TextView(this).apply {
            textSize = 16f
            setPadding(20, 20, 20, 20)
        }

        attemptsTextView = TextView(this).apply {
            textSize = 16f
            setPadding(20, 20, 20, 20)
        }

        button1 = Button(this).apply {
            text = "1"
            setOnClickListener { onAnswerSelected(0) }
        }

        button2 = Button(this).apply {
            text = "2"
            setOnClickListener { onAnswerSelected(1) }
        }

        button3 = Button(this).apply {
            text = "3"
            setOnClickListener { onAnswerSelected(2) }
        }

        // Кнопка для получения жизни
        val addLifeButton = Button(this).apply {
            text = "Добавить жизнь"
            setOnClickListener { onAddLifeClicked() }
        }

        // Кнопка "Домой"
        homeButton = Button(this).apply {
            text = "Домой"
            setOnClickListener { goHome() }
            visibility = Button.GONE  // Кнопка изначально скрыта
        }

        // Текстовое сообщение "Вы прошли первый уровень"
        levelCompleteTextView = TextView(this).apply {
            textSize = 18f
            setPadding(20, 50, 20, 20)
            text = "Вы прошли первый уровень"
            visibility = TextView.GONE  // Скрыто по умолчанию
        }

        layout.addView(questionTextView)
        layout.addView(button1)
        layout.addView(button2)
        layout.addView(button3)
        layout.addView(resultTextView)
        layout.addView(attemptsTextView)
        layout.addView(addLifeButton)
        layout.addView(homeButton)
        layout.addView(levelCompleteTextView) // Добавляем сообщение

        setContentView(layout)

        showQuestion()
    }

    private fun showQuestion() {
        if (questionIndex < questions.size) {
            val currentQuestion = questions[questionIndex]
            questionTextView.text = currentQuestion.text
            button1.text = currentQuestion.answers[0]
            button2.text = currentQuestion.answers[1]
            button3.text = currentQuestion.answers[2]
            resultTextView.text = ""
            resetButtonColors()

            // Изменяем текст на attemptsTextView в зависимости от оставшихся попыток
            if (remainingAttempts > 0) {
                attemptsTextView.text = "Оставшиеся попытки: $remainingAttempts"
                attemptsTextView.visibility = TextView.VISIBLE
            } else {
                attemptsTextView.visibility = TextView.GONE
            }
        } else {
            resultTextView.text = "Поздравляем! Вы ответили на все вопросы."
            homeButton.visibility = Button.VISIBLE  // Показываем кнопку "Домой" на последнем вопросе

            // Скрываем все вопросы и кнопки
            questionTextView.visibility = TextView.GONE
            button1.visibility = Button.GONE
            button2.visibility = Button.GONE
            button3.visibility = Button.GONE
            resultTextView.visibility = TextView.GONE
            attemptsTextView.visibility = TextView.GONE
            // Показываем сообщение о завершении уровня
            levelCompleteTextView.visibility = TextView.VISIBLE
        }
    }

    private fun onAnswerSelected(selectedIndex: Int) {
        val currentQuestion = questions[questionIndex]

        if (remainingAttempts > 0) {
            if (selectedIndex == currentQuestion.correctAnswerIndex) {
                resultTextView.text = "Правильно!"
                resultTextView.setTextColor(getColor(android.R.color.holo_green_dark))
                updateButtonColors(selectedIndex, true)
                questionIndex++
                showQuestion()
            } else {
                remainingAttempts--
                resultTextView.text = "Неправильно. Попробуйте снова!"
                resultTextView.setTextColor(getColor(android.R.color.holo_red_dark))
                updateButtonColors(selectedIndex, false)
            }
        }

        if (remainingAttempts == 0) {
            resultTextView.text = "Попытки закончились!"
        }

        // Сохраняем количество жизней
        sharedPreferences.edit().putInt("remainingAttempts", remainingAttempts).apply()

        // Обновляем видимость и текст attemptsTextView
        if (remainingAttempts > 0) {
            attemptsTextView.text = "Оставшиеся попытки: $remainingAttempts"
            attemptsTextView.visibility = TextView.VISIBLE
        } else {
            attemptsTextView.visibility = TextView.GONE
        }
    }

    private fun onAddLifeClicked() {
        // Увеличиваем количество жизней
        remainingAttempts++
        attemptsTextView.text = "Оставшиеся попытки: $remainingAttempts"

        // Сохраняем новое количество жизней
        sharedPreferences.edit().putInt("remainingAttempts", remainingAttempts).apply()

        // Обновляем видимость attemptsTextView
        if (remainingAttempts > 0) {
            attemptsTextView.visibility = TextView.VISIBLE
        }
    }

    private fun updateButtonColors(selectedIndex: Int, isCorrect: Boolean) {
        when (selectedIndex) {
            0 -> button1.setBackgroundColor(if (isCorrect) getColor(android.R.color.holo_green_light) else getColor(android.R.color.holo_red_light))
            1 -> button2.setBackgroundColor(if (isCorrect) getColor(android.R.color.holo_green_light) else getColor(android.R.color.holo_red_light))
            2 -> button3.setBackgroundColor(if (isCorrect) getColor(android.R.color.holo_green_light) else getColor(android.R.color.holo_red_light))
        }
    }

    private fun resetButtonColors() {
        button1.setBackgroundColor(getColor(android.R.color.holo_blue_dark))
        button2.setBackgroundColor(getColor(android.R.color.holo_blue_dark))
        button3.setBackgroundColor(getColor(android.R.color.holo_blue_dark))
    }

    private fun goHome() {
        // Переход на главный экран
        val intent = Intent(this, MainActivity::class.java)  // Здесь MainActivity - главный экран вашего приложения
        startActivity(intent)
        finish()  // Закрываем текущую активность
    }
}

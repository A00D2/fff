package com.example.myapplication


import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.content.SharedPreferences
class ShopActivity: Activity() {


    private lateinit var lifeButton: Button
    private lateinit var remainingLivesTextView: TextView
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Инициализация SharedPreferences
        sharedPreferences = getSharedPreferences("GamePrefs", Context.MODE_PRIVATE)

        // Создаем layout
        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
        }

        // Создаем кнопку
        lifeButton = Button(this).apply {
            text = "Дать жизнь"
            setOnClickListener { onGiveLifeClicked() }
        }

        // Создаем TextView для отображения оставшихся жизней
        remainingLivesTextView = TextView(this).apply {
            textSize = 20f
            setPadding(20, 50, 20, 20)
        }

        // Добавляем все элементы в layout
        layout.addView(remainingLivesTextView)
        layout.addView(lifeButton)

        setContentView(layout)

        // Отображаем текущее количество жизней
        updateLivesDisplay()
    }

    private fun onGiveLifeClicked() {
        // Получаем текущее количество жизней
        var remainingAttempts = sharedPreferences.getInt("remainingAttempts", 0)

        // Увеличиваем количество жизней на 1
        remainingAttempts++

        // Сохраняем новое количество жизней
        sharedPreferences.edit().putInt("remainingAttempts", remainingAttempts).apply()

        // Обновляем отображение количества жизней
        updateLivesDisplay()
    }

    private fun updateLivesDisplay() {
        // Получаем текущее количество жизней
        val remainingAttempts = sharedPreferences.getInt("remainingAttempts", 0)

        // Обновляем текст на экране в зависимости от количества жизней
        if (remainingAttempts > 0) {
            remainingLivesTextView.text = "Оставшиеся попытки: $remainingAttempts"
        } else {
            remainingLivesTextView.text = "Попыток нет"
        }
    }
}
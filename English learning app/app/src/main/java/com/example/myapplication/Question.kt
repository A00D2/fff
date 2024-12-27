package com.example.myapplication

data class Question(
    val text: String,
    val answers: List<String>,
    val correctAnswerIndex: Int
)

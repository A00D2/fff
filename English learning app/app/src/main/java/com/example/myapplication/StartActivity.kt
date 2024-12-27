package com.example.myapplication

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout

class StartActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
        }

        val button1 = Button(this).apply {
            text = "первый уровень (он самый легкий, лучше начать с него, чисто для казуалов)"
            setOnClickListener {
                val intent = Intent(this@StartActivity, Screen1Activity::class.java)
                startActivity(intent)
            }
        }

        val button2 = Button(this).apply {
            text = "второй уровень сложности(он труднее чем первый)"
            setOnClickListener {
                val intent = Intent(this@StartActivity, Screen2Activity::class.java)
                startActivity(intent)
            }
        }

        val button3 = Button(this).apply {
            text = "третий уровень сложности (Не лезь, она тебя сожрёт)"
            setOnClickListener {
                val intent = Intent(this@StartActivity, Screen3Activity::class.java)
                startActivity(intent)
            }
        }

        layout.addView(button1)
        layout.addView(button2)
        layout.addView(button3)

        setContentView(layout)
    }
}

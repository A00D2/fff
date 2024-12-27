package com.example.myapplication

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL

        val startButton = Button(this).apply {
            text = "Начать"
            setOnClickListener {
                val intent = Intent(this@MainActivity, StartActivity::class.java)
                startActivity(intent)
            }
        }

        val shopButton = Button(this).apply {
            text = "Магазин"
            setOnClickListener {
                val intent = Intent(this@MainActivity, ShopActivity::class.java)
                startActivity(intent)
            }
        }

        layout.addView(startButton)
        layout.addView(shopButton)

        setContentView(layout)
    }
}

package com.example.tutorials

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_text.*

class TextActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text)

        var count = 0
        btnCount.setOnClickListener {
            count++
            tvCount.text = "Let's count together: $count"
        }
    }
}
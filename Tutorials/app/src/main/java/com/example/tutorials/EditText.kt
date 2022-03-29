package com.example.tutorials

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_edit_text.*

class EditText : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_text)

        btnAdd.setOnClickListener {
            val firstNumber = etFirstNumber.text.toString().toInt()
            val secondNumber = etSecondNumber.text.toString().toInt()
            var result = firstNumber + secondNumber
            tvResult.text = result.toString()
        }
    }
}
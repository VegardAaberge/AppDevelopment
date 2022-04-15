package com.example.tutorials

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RadioButton
import kotlinx.android.synthetic.main.activity_checkbox.*

class Checkbox : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkbox)

        btnOrder.setOnClickListener {
            val checkedMeatRadioButtonId = rgMeat.checkedRadioButtonId
            val meat = findViewById<RadioButton>(checkedMeatRadioButtonId)

            val cheese = cbCheese.isChecked
            val onions = cbOnions.isChecked
            val salad = cbSalad.isChecked

            val orderString = "You ordered a burger with:\n" +
                    "${meat.text}" +
                    (if(cheese) "\nCheese" else "") +
                    (if(onions) "\nOnions" else "") +
                    (if(salad) "\nSalad" else "")

            tvOrder.text = orderString
        }
    }
}
package com.example.tutorials

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class LetAlsoApplyRunWith : AppCompatActivity() {

    private var number: Int? = null

    private var i = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_let_also_apply_run_with)

        // LET: Will only invoke if number is not null
        val x = number?.let {
            val number2 = it + 1
            number2
        } ?: 3

        // APPLY: Allow to modify object without writing object name
        val intent1 = Intent().apply {
            putExtra("", "")
        }

        // RUN: Will return the last line, not the object
        val intent2 = Intent().run {
            putExtra("", "")
            this
        }

        // WITH: same as run
        val intent3 = with(Intent()) {
            this
        }
    }

    // ALSO: let us do two things with i
    fun getSquaredI() = (i * i).also {
        i + 1
    }
}
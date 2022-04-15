package com.example.tutorials

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tutorials.data.Constants
import kotlinx.android.synthetic.main.activity_navigation.*
import kotlinx.android.synthetic.main.activity_shared_preferences.*

class SharedPreferences : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shared_preferences)

        val sharedPref = getSharedPreferences("myPref", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()

        btnSavePreferences.setOnClickListener {
            val name = etNamePreferences.text.toString()
            val age = etAgePreferences.text.toString().toInt()
            val isAdult = cbAdultPreferences.isChecked

            editor.apply {
                putString(Constants.PREF_NAME, name)
                putInt(Constants.PREF_AGE, age)
                putBoolean(Constants.PREF_IS_ADULT, isAdult)
                apply()
            }
        }

        btnLoadPreferences.setOnClickListener {
            val name = sharedPref.getString(Constants.PREF_NAME, null)
            val age = sharedPref.getInt(Constants.PREF_AGE, 0)
            val isAdult = sharedPref.getBoolean(Constants.PREF_IS_ADULT, false)

            etNamePreferences.setText(name)
            etAgePreferences.setText(age.toString())
            cbAdultPreferences.isChecked = isAdult
        }
    }
}
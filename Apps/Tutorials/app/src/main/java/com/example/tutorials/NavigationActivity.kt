package com.example.tutorials

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tutorials.data.Constants
import com.example.tutorials.data.Person
import kotlinx.android.synthetic.main.activity_navigation.*

class NavigationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)

        btnApply.setOnClickListener {
            val name = etName.text.toString()
            val age = etAge.text.toString().toInt()
            val country = etCountry.text.toString()
            val person = Person(name, age, country)

            Intent(this, ImageView::class.java).also {
                it.putExtra(Constants.EXTRA_NAME, name)
                it.putExtra(Constants.EXTRA_AGE, age)
                it.putExtra(Constants.EXTRA_COUNTRY, country)
                it.putExtra(Constants.EXTRA_PERSON, person)
                startActivity(it)
            }
        }
    }
}
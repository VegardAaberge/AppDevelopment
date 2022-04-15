package com.example.tutorials

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tutorials.data.Constants
import com.example.tutorials.data.Person
import kotlinx.android.synthetic.main.activity_image_view.*

class ImageView : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_view)

        val name = intent.getStringExtra(Constants.EXTRA_NAME)
        val age = intent.getIntExtra(Constants.EXTRA_AGE, 0)
        val country = intent.getStringExtra(Constants.EXTRA_COUNTRY)
        val person = intent.getSerializableExtra(Constants.EXTRA_PERSON) as Person

        tvPerson.text = "$name is $age years old and lives in ${person.country}"

        btnAddImage.setOnClickListener {
            ivImage.setImageResource(R.drawable.kermit)
        }

        btnGoBack.setOnClickListener {
            finish()
        }
    }
}
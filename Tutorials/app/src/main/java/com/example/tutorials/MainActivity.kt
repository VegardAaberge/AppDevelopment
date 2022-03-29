package com.example.tutorials

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnApply.setOnClickListener {
            val firstName = etFirstName.text.toString()
            val lastName = etLastName.text.toString()
            val birthDate = etBirthDate.text.toString()
            val country = etCountry.text.toString()
            Log.d("MainActivity", "$firstName $lastName, born on $birthDate from $country just applied to the formula")
        }

        val resultContract = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            if(it.resultCode == Activity.RESULT_OK){
                ivPhoto.setImageURI(it.data?.data)
            }else{
                Log.e("MainActivity", "Failed to get photos")
            }
        }

        val choosePicture = Intent().apply {
            action = Intent.ACTION_GET_CONTENT
            type = "image/*"
        }

        btnTakePhoto.setOnClickListener {
            resultContract.launch(choosePicture)
        }

        val intent = Intent(this, DragAndDrop::class.java)
        startActivity(intent)
    }

    private fun LetAlsoApplyRunWith(){

    }

    private fun SortListAndLog(){
        var list = listOf(5, 3, 6, 7, 9, 1)
        sortList(list)
        Log.d("MainActivity", "Hello this is our first log message")
        Log.d("MainActivity", list.toString())
    }

    private fun sortList(list: List<Int>) {
        for (i in 0..list.size - 1){
            for(j in 0..list.size - 2){
                if(list[j] > list[j+1]){
                    Collections.swap(list, j, j+1)
                }
            }
        }
    }
}
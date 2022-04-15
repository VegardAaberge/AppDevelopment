package com.example.coroutinestutorials

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_firebase_firestore.*
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await

data class Person(
    val name: String = "",
    val age: Int = -1
)

class FirebaseFirestore : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_firebase_firestore)

        val tutorialDocument = Firebase.firestore.collection("sample")
            .document("document")
        val peter = Person("Person", 25)

        GlobalScope.launch(Dispatchers.IO) {
            tutorialDocument.set(peter).await()
            val person = tutorialDocument.get().await().toObject(Person::class.java)
            withContext(Dispatchers.Main){
                tvData.text = person.toString()
            }
        }
    }
}
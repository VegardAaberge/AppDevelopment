package com.example.coroutinestutorials

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

class AsyncAwait : AppCompatActivity() {

    val TAG = "AsyncAwait"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_async_await)

        GlobalScope.launch(Dispatchers.IO) {
            val time = measureTimeMillis {
                val answer1 = async { networkCall1() }
                val answer2 = async { networkCall2() }

                Log.d(TAG, "Answer1 is ${answer1.await()}")
                Log.d(TAG, "Answer2 is ${answer2.await()}")
            }
            Log.d(TAG,"Request took $time ms.")
        }
    }

    suspend fun networkCall1() : String{
        delay(3000L)
        return "Answer 1"
    }

    suspend fun networkCall2() : String{
        delay(3000L)
        return "Answer 2"
    }
}
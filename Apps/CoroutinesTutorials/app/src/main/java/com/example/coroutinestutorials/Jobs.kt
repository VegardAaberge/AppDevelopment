package com.example.coroutinestutorials

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.coroutines.*

class Jobs : AppCompatActivity() {

    val TAG = "Jobs"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jobs)

        val job = GlobalScope.launch(Dispatchers.Default) {

            Log.d(TAG, "Starting long running calculation")
            repeat(5){
                // Timeout if calculation take more than 3 seconds
                withTimeout(3000L){
                    for (i in 30..40){
                        // Check if job is cancelled
                        if(isActive){
                            Log.d(TAG, "Result for i = $i: ${fib(i)}")
                        }
                    }
                }
            }
            Log.d(TAG, "Ending long running calculation")
        }

        runBlocking {
            // Waited for the finish of our coroutine
            //job.join()

            delay(2000L)
            job.cancel()
            Log.d(TAG, "Cancelled job!")
        }

    }

    fun fib(n: Int): Long {
        return if(n == 0) 0
        else if(n == 1) 1
        else fib(n -1) + fib(n - 2)
    }
}
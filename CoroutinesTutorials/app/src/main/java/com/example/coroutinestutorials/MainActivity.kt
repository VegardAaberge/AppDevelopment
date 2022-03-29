package com.example.coroutinestutorials

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Dispatcher decide what kind of thread it will be started in
        // Main = Main thread
        // IO - Networking, writing to database and files
        // Default - Long running operations that would block the main thread
        // Unconfined - Stay in the same thread
        // newSingleThreadContext("MyThread") - Create a new thread
        GlobalScope.launch(Dispatchers.IO) {

            Log.d(TAG, "Starting coroutine in thread ${Thread.currentThread().name}")

            // This will block the thread, similar to Thread.sleep()
            Log.d(TAG, "Before nunBlocking")
            runBlocking {

                Log.d(TAG, "Starting runblocking in thread ${Thread.currentThread().name}")

                // Wlll run async
                launch(Dispatchers.IO) {
                    val answer = doNetworkCall()

                    // Switch to main thread to do UI changes
                    withContext(Dispatchers.Main){
                        Log.d(TAG, "Setting text in thread ${Thread.currentThread().name}")
                        tvHelloWorld.text = answer
                    }
                    Log.d(TAG, "Finished IO Coroutine 1")
                }
                launch(Dispatchers.IO) {
                    val answer = doNetworkCall()
                    Log.d(TAG, "Finished IO Coroutine 2")
                }

                Log.d(TAG, "Start of nunBlocking")
                delay(5000L)
                Log.d(TAG, "End of nunBlocking")
            }
            Log.d(TAG, "After nunBlocking")
        }

        Log.d(TAG, "Hello from thread ${Thread.currentThread().name}")

        // If use GlobalScope, it will cause memory leak as the task never end
        lifecycleScope.launch {
            while (true){
                delay(200)
                Log.d(TAG, "Still running")
            }
        }

        GlobalScope.launch {
            delay(2000)
            Intent(this@MainActivity, FirebaseFirestore::class.java).also {
                startActivity(it)
                finish()
            }
        }

    }

    suspend fun doNetworkCall() : String{
        delay(3000L)
        return "This is the answer"
    }
}
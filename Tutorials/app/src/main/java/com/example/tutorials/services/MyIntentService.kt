package com.example.tutorials.services

import android.app.IntentService
import android.content.Intent
import android.util.Log

// Run seperate thread, won't block the main thread
// Does not support multi threading
class MyIntentService : IntentService("MyIntentService") {

    init {
        instance = this
    }

    companion object {
        private lateinit var instance: MyIntentService
        var isRunning = false

        fun stopService() {
            Log.d("MyService", "Service ia stopping...")
            isRunning = false
            instance.stopSelf()
        }
    }

    override fun onHandleIntent(p0: Intent?) {
        try {
            isRunning = true
            while (isRunning){
                Log.d("MyService", "Service is running")
                Thread.sleep(1000)
            }
        }catch (e: InterruptedException){
            Thread.currentThread().interrupt()
        }
    }
}
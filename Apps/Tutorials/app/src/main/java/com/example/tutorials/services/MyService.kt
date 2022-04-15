package com.example.tutorials.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.example.tutorials.data.Constants

// Support multithreading
// Run on UI thread by default
class MyService : Service() {
    val TAG = "MyService"

    init {
        Log.d(TAG, "Service is running...")
    }

    override fun onBind(p0: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        val dataString = intent?.getStringExtra(Constants.EXTRA_DATA)
        dataString?.let{
            Log.d(TAG, dataString)
        }

        //Thread{
            //while (true) { }
        //}.start()

        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "Service is being killed")
    }
}
package com.example.tutorials

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatActivity
import com.example.tutorials.data.Constants
import com.example.tutorials.services.MyIntentService
import com.example.tutorials.services.MyService
import kotlinx.android.synthetic.main.activity_intent_service.*


class IntentService : AppCompatActivity() {

    var isIntentService : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intent_service)

        swIntent.setOnCheckedChangeListener { _, isToggled ->
            isIntentService = isToggled
            Log.d("MyService", "Intent service is $isIntentService")
        }

        btnStartService.setOnClickListener {
            getServiceClass().also {
                startService(it)
                tvServiceInfo.text = "Service running"
            }
        }

        btnStopService.setOnClickListener {
            if(isIntentService){
                MyIntentService.stopService()
            }else{
                Intent(this, MyService::class.java).also {
                    stopService(it)
                    tvServiceInfo.text = "Service stopped"
                }
            }
        }

        btnSendData.setOnClickListener {
            Intent(this, MyService::class.java).also {
                val dataString = etData.text.toString()
                it.putExtra(Constants.EXTRA_DATA, dataString)
                startService(it)
            }
        }
    }

    private fun getServiceClass() : Intent {
        return if(isIntentService){
            Intent(this, MyIntentService::class.java)
        }else{
            Intent(this, MyService::class.java)
        }
    }
}
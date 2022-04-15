package com.example.tutorials.Receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_drag_and_drop.*

class AirplaneModeChangedReceiver(dw: View) : BroadcastReceiver() {

    private var dragView : View = dw

    override fun onReceive(context: Context?, intent: Intent?) {
        val isAirplaneModeEnabled = intent?.getBooleanExtra("state", false) ?: return
        if(isAirplaneModeEnabled){
            dragView.setBackgroundColor(Color.GREEN)
            Toast.makeText(context, "Airplane Mode enabled", Toast.LENGTH_LONG).show()
        }else{
            dragView.setBackgroundColor(Color.BLACK)
            Toast.makeText(context, "Airplane Mode disabled", Toast.LENGTH_LONG).show()
        }
    }
}
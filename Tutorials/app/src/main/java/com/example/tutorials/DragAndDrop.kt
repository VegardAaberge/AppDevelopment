package com.example.tutorials

import android.content.ClipData
import android.content.ClipDescription
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.DragEvent
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import com.example.tutorials.Receivers.AirplaneModeChangedReceiver
import kotlinx.android.synthetic.main.activity_drag_and_drop.*

class DragAndDrop : AppCompatActivity() {

    val TAG = "DragAndDrop"
    lateinit var receiver: AirplaneModeChangedReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drag_and_drop)

        // Reciever
        receiver = AirplaneModeChangedReceiver(dragView)

        IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED).also {
            registerReceiver(receiver, it)
        }


        // Drag and Drop
        llTop.setOnDragListener(dragListener)
        llBottom.setOnDragListener(dragListener)

        dragView.setOnClickListener {
            Log.d(TAG, "DragView clicked")
        }

        dragView.setOnLongClickListener {
            Log.d(TAG, "dragView.setOnClickListener START")
            val clipText = "This is out ClipData text"
            val item = ClipData.Item(clipText)
            val mimeType = arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN)
            val data = ClipData(clipText, mimeType, item)

            val dragShadowBuilder = View.DragShadowBuilder(it)
            it.startDragAndDrop(data, dragShadowBuilder, it, 0)

            it.visibility = View.INVISIBLE
            Log.d(TAG, "dragView.setOnClickListener END")
            true
        }
    }

    private val dragListener = View.OnDragListener { view, dragEvent ->
        when(dragEvent.action){
            DragEvent.ACTION_DRAG_STARTED -> {
                Log.d(TAG, "ACTION_DRAG_STARTED")
                dragEvent.clipDescription.hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)
            }
            DragEvent.ACTION_DRAG_ENTERED -> {
                Log.d(TAG, "ACTION_DRAG_ENTERED")
                view.invalidate()
                true
            }
            DragEvent.ACTION_DRAG_LOCATION -> true
            DragEvent.ACTION_DRAG_EXITED -> {
                Log.d(TAG, "ACTION_DRAG_EXITED")
                view.invalidate()
                true
            }
            DragEvent.ACTION_DROP -> {
                Log.d(TAG, "ACTION_DROP")
                val item = dragEvent.clipData.getItemAt(0)
                val dragData = item.text
                Toast.makeText(this, dragData, Toast.LENGTH_SHORT).show()

                view.invalidate()

                // Remove the view from the original LL
                val v = dragEvent.localState as View
                val owner = v.parent as ViewGroup
                owner.removeView(v)

                // Add the view to the new LL
                val destination = view as LinearLayout
                destination.addView(v)
                v.visibility = View.VISIBLE

                true
            }
            DragEvent.ACTION_DRAG_ENDED -> {
                Log.d(TAG, "ACTION_DRAG_ENDED")
                view.invalidate()
                true
            }
            else -> false
        }
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(receiver)
    }
}
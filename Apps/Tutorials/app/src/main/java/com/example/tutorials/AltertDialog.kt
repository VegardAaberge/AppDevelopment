package com.example.tutorials

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_altert_dialog.*

class AltertDialog : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_altert_dialog)

        val addContactDialog = AlertDialog.Builder(this)
            .setTitle("Add Contact")
            .setMessage("Do you want to add Mr to the contacts list?")
            .setIcon(R.drawable.ic_add_contacts_custom)
            .setPositiveButton("Yes"){ _, _ ->
                Toast.makeText(this, "You added Mr to your contact list", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("No"){_, _ ->
                Toast.makeText(this, "You didn't add Mr to your contact list", Toast.LENGTH_SHORT).show()
            }
            .create()

        val options = arrayOf("First item", "Second item", "Third item")
        val singleChoiceDialog = AlertDialog.Builder(this)
            .setTitle("Choose one of these options")
            .setSingleChoiceItems(options, 0) {_, i ->
                Toast.makeText(this, "You clicked on ${options[i]}", Toast.LENGTH_SHORT).show()
            }
            .setPositiveButton("Accept"){ _, _ ->
                Toast.makeText(this, "You accepted the SingleChoiceDialog", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Decline"){_, _ ->
                Toast.makeText(this, "You declined the SingleChoiceDialog", Toast.LENGTH_SHORT).show()
            }
            .create()

        val multiChoiceDialog = AlertDialog.Builder(this)
            .setTitle("Choose one of these options")
            .setMultiChoiceItems(options, booleanArrayOf(false, false, false)) {_, i, icChecked ->
                if(icChecked) {
                    Toast.makeText(this, "You checked ${options[i]}", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this, "You unchecked ${options[i]}", Toast.LENGTH_SHORT).show()
                }
            }
            .setPositiveButton("Accept"){ _, _ ->
                Toast.makeText(this, "You accepted the MultiChoiceDialog", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Decline"){_, _ ->
                Toast.makeText(this, "You declined the MultiChoiceDialog", Toast.LENGTH_SHORT).show()
            }
            .create()

        btnDialog1.setOnClickListener {
            addContactDialog.show()
        }
        btnDialog2.setOnClickListener {
            singleChoiceDialog.show()
        }
        btnDialog3.setOnClickListener {
            multiChoiceDialog.show()
        }
    }
}
package com.example.bingoassistant

import BingoDatabaseHelper
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    // Instantiate com.example.bingoassistant.BingoDatabaseHelper
    private val databaseHelper by lazy { BingoDatabaseHelper(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.BTN_viewBingos).setOnClickListener {
            startActivity(Intent(this, ViewBingosActivity::class.java))
        }

        findViewById<Button>(R.id.BTN_newBingo).setOnClickListener {
            startActivity(Intent(this, NewBingoFormActivity::class.java))
        }

        findViewById<Button>(R.id.BTN_DELETE_ALL_BINGOS).setOnClickListener {
            showFirstConfirmationDialog()
        }
    }

    private fun showFirstConfirmationDialog() {
        AlertDialog.Builder(this)
            .setMessage("Are you sure you want to delete all bingos?")
            .setPositiveButton("Yes") { dialog, _ ->
                showSecondConfirmationDialog()
                dialog.dismiss()
            }
            .setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun showSecondConfirmationDialog() {
        AlertDialog.Builder(this)
            .setMessage("This action cannot be undone. Are you absolutely sure?")
            .setPositiveButton("Yes, delete all") { dialog, _ ->
                databaseHelper.deleteAllData()
                Toast.makeText(this, "All bingos deleted successfully", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
}

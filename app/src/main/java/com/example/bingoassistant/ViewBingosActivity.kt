package com.example.bingoassistant

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ViewBingosActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_bingos)

        // Find the floating action button
        val fab: FloatingActionButton = findViewById(R.id.BTN_addNewBingoFloat)

        // Set OnClickListener on the floating action button
        fab.setOnClickListener {
            // Create an Intent to start NewBingoFormActivity
            val intent = Intent(this, NewBingoFormActivity::class.java)

            // Start the activity
            startActivity(intent)
        }
    }
}
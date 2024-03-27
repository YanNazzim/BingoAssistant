package com.example.bingoassistant

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewBingosBTN = findViewById<Button>(R.id.BTN_viewBingos)
        val newBingoBTN = findViewById<Button>(R.id.BTN_newBingo)
        val deleteAllBingosBTN = findViewById<Button>(R.id.BTN_DELETE_ALL_BINGOS)

        viewBingosBTN.setOnClickListener {
            startActivity(Intent(this, ViewBingosActivity::class.java))
        }

        newBingoBTN.setOnClickListener{
            startActivity(Intent(this, NewBingoFormActivity::class.java))
        }



    }
}

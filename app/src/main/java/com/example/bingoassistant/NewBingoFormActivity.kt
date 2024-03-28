package com.example.bingoassistant

import BingoDatabaseHelper
import android.content.ContentValues
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class NewBingoFormActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_bingo_form)

        val submitBTN = findViewById<Button>(R.id.BTN_submit)
        val prizeEditText = findViewById<EditText>(R.id.newbingo_prize)
        val priceEditText = findViewById<EditText>(R.id.newbingo_price)

        //Home Button
        val backHomeBTN = findViewById<Button>(R.id.BTN_backToHome)
        backHomeBTN.setOnClickListener {
            finish()
        }


        submitBTN.setOnClickListener {
            val prize = prizeEditText.text.toString().trim()
            val priceText = priceEditText.text.toString().trim()

            if (prize.isEmpty()) {
                prizeEditText.error = "Prize cannot be empty"
                return@setOnClickListener
            }

            if (priceText.isEmpty()) {
                priceEditText.error = "Price cannot be empty"
                return@setOnClickListener
            }

            val price = try {
                priceText.toDouble()
            } catch (e: NumberFormatException) {
                priceEditText.error = "Invalid price format"
                return@setOnClickListener
            }

            val databaseHelper = BingoDatabaseHelper(this)
            val db = databaseHelper.writableDatabase

            val values = ContentValues().apply {
                put(BingoDatabaseHelper.COLUMN_PRIZE, prize)
                put(BingoDatabaseHelper.COLUMN_PRICE, price)
            }

            val newRowId = db.insert(BingoDatabaseHelper.TABLE_BINGOS, null, values)

            if (newRowId != -1L) {
                Toast.makeText(this, "Bingo added successfully", Toast.LENGTH_SHORT).show()
                prizeEditText.text.clear()
                priceEditText.text.clear()
            } else {
                Toast.makeText(this, "Failed to add bingo", Toast.LENGTH_SHORT).show()
            }

            db.close()
        }
    }
}

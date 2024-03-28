package com.example.bingoassistant

import BingoDatabaseHelper
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class EditBingoActivity : AppCompatActivity(), BingoEntriesAdapter.OnItemClickListener {

    private lateinit var bingoDatabaseHelper: BingoDatabaseHelper
    private lateinit var recyclerViewBingoEntries: RecyclerView
    private lateinit var bingoEntriesAdapter: BingoEntriesAdapter

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_bingo)

        // Initialize the database helper
        bingoDatabaseHelper = BingoDatabaseHelper(this)

        // Retrieve bingo data from intent extras
        val bingoId = intent.getIntExtra("bingo_id", -1)
        val bingoPrize = intent.getStringExtra("bingo_prize")
        val bingoPrice = intent.getDoubleExtra("bingo_price", 0.0)

        // Find TextViews
        val bingoIdNumTextView = findViewById<TextView>(R.id.bingoNumText)
        val bingoPrizeTextView = findViewById<TextView>(R.id.bingoNameText)
        val bingoPriceTextView = findViewById<TextView>(R.id.bingoPriceText)

        // Set text of TextViews with bingo data
        bingoIdNumTextView.text = bingoId.toString()
        bingoPrizeTextView.text = bingoPrize
        bingoPriceTextView.text = bingoPrice.toString()

        // Set up RecyclerView for Bingo Entries
        recyclerViewBingoEntries = findViewById(R.id.recyclerViewBingoEntries)
        recyclerViewBingoEntries.layoutManager = LinearLayoutManager(this)

        // Initialize and set up the adapter for BingoEntries RecyclerView
        bingoEntriesAdapter = BingoEntriesAdapter(ArrayList(), this)
        recyclerViewBingoEntries.adapter = bingoEntriesAdapter

        // Load Bingo entries from the database
        loadBingoEntries()

        // Handle Save button click
        val saveButton = findViewById<Button>(R.id.btn_submitEdit)
        saveButton.setOnClickListener {
            saveChanges()
        }
    }

    private fun loadBingoEntries() {
        val bingoEntries = bingoDatabaseHelper.getAllBingoEntries()
        bingoEntriesAdapter.updateData(bingoEntries)
    }

    private fun saveChanges() {
        val bingoEntries = bingoDatabaseHelper.getAllBingoEntries()
        bingoDatabaseHelper.saveChangesToEntries(bingoEntries)
        Toast.makeText(this, "Changes saved successfully", Toast.LENGTH_SHORT).show()
    }

    override fun onItemClick(entry: BingoEntry) {
        showEditDialog(entry)
    }

    private fun showEditDialog(entry: BingoEntry) {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_edit_entry_name, null)
        val editTextNewName = dialogView.findViewById<EditText>(R.id.editTextNewName)
        editTextNewName.setText(entry.name)

        AlertDialog.Builder(this)
            .setTitle("Edit Entry Name")
            .setView(dialogView)
            .setPositiveButton("Save") { dialog, _ ->
                val newName = editTextNewName.text.toString()
                entry.name = newName
                bingoDatabaseHelper.updateBingoEntry(entry)
                Toast.makeText(this, "Entry updated successfully", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
                loadBingoEntries() // Reload entries after updating
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
}

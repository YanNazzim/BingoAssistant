package com.example.bingoassistant

import BingoDatabaseHelper
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ViewBingosActivity : AppCompatActivity(), BingoAdapter.OnItemClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var bingoAdapter: BingoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_bingos)

        recyclerView = findViewById(R.id.recyclerViewBingos)

        // Set up RecyclerView with GridLayout for Bingos
        recyclerView.layoutManager = GridLayoutManager(this, 3)

        // Fetch data for Bingos from the database
        val databaseHelper = BingoDatabaseHelper(this)
        val bingos = databaseHelper.getAllBingos()

        // Initialize and set up the adapter for Bingos RecyclerView
        bingoAdapter = BingoAdapter(bingos, this)
        recyclerView.adapter = bingoAdapter
    }

    override fun onItemClick(bingo: Bingo) {
        val intent = Intent(this, EditBingoActivity::class.java).apply {
            putExtra("bingo_id", bingo.id) // Pass the ID of the bingo
            putExtra("bingo_prize", bingo.prize) // Pass the prize of the bingo
            putExtra("bingo_price", bingo.price) // Pass the price of the bingo
        }
        startActivity(intent)
    }
}

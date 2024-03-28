package com.example.bingoassistant

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class BingoEntriesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
    private val bingoNameTextView: TextView = itemView.findViewById(R.id.textViewBingoName)
    private val bingoEntryNumberTextView: TextView = itemView.findViewById(R.id.textViewEntryNumber)
    private lateinit var bingoEntry: BingoEntry

    init {
        itemView.setOnClickListener(this)
    }

    fun bind(bingoEntry: BingoEntry) {
        this.bingoEntry = bingoEntry
        bingoNameTextView.text = bingoEntry.name
        bingoEntryNumberTextView.text = bingoEntry.entryNumber.toString()
    }

    override fun onClick(view: View?) {
        // Pass the clicked bingo entry to the click listener
        itemView.context?.let { context ->
            (context as? BingoEntriesAdapter.OnItemClickListener)?.onItemClick(bingoEntry)
        }
    }
}

package com.example.bingoassistant

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class BingoEntriesAdapter(private var bingoEntries: List<BingoEntry>, private val listener: OnItemClickListener) :
    RecyclerView.Adapter<BingoEntriesAdapter.BingoEntriesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BingoEntriesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_bingo_entry, parent, false)
        return BingoEntriesViewHolder(view)
    }

    override fun onBindViewHolder(holder: BingoEntriesViewHolder, position: Int) {
        val bingoEntry = bingoEntries[position]
        holder.bind(bingoEntry)
        holder.itemView.setOnClickListener {
            listener.onItemClick(bingoEntry)
        }
    }

    override fun getItemCount(): Int {
        return bingoEntries.size
    }

    class BingoEntriesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val bingoNameTextView: TextView = itemView.findViewById(R.id.textViewBingoName)
        private val bingoEntryNumberTextView: TextView = itemView.findViewById(R.id.textViewEntryNumber)

        fun bind(bingoEntry: BingoEntry) {
            bingoNameTextView.text = bingoEntry.name
            bingoEntryNumberTextView.text = bingoEntry.entryNumber.toString()
        }
    }

    // Interface to handle item clicks
    interface OnItemClickListener {
        fun onItemClick(bingoEntry: BingoEntry)
    }

    fun updateData(newEntries: List<BingoEntry>) {
        bingoEntries = newEntries
        this.notifyDataSetChanged()
    }
}

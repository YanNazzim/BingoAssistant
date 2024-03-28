package com.example.bingoassistant

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class BingoAdapter(private val bingos: List<Bingo>, private val listener: OnItemClickListener) :
    RecyclerView.Adapter<BingoAdapter.BingoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BingoViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.bingo_item, parent, false)
        return BingoViewHolder(view)
    }

    override fun onBindViewHolder(holder: BingoViewHolder, position: Int) {
        val bingo = bingos[position]
        holder.bind(bingo)
        holder.itemView.setOnClickListener {
            listener.onItemClick(bingo) // Notify the listener when an item is clicked
        }
    }

    override fun getItemCount(): Int {
        return bingos.size
    }

    class BingoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textViewBingo: TextView = itemView.findViewById(R.id.textViewBingo)

        fun bind(bingo: Bingo) {
            textViewBingo.text = bingo.prize
        }
    }

    interface OnItemClickListener {
        fun onItemClick(bingo: Bingo)
    }
}

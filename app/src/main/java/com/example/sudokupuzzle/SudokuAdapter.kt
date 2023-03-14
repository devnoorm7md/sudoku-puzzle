package com.example.sudokupuzzle

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SudokuAdapter (private val numbers: Array<Int?>) : RecyclerView.Adapter<SudokuAdapter.CellViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CellViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.sudoku_cell_layout, parent, false)
        return CellViewHolder(view)
    }

    override fun onBindViewHolder(holder: CellViewHolder, position: Int) {
        holder.bind(numbers[position])
    }

    override fun getItemCount(): Int {
        return numbers.size
    }

    class CellViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val numberTextView: TextView = itemView.findViewById(R.id.number_text_view)

        fun bind(number: Int?) {
            numberTextView.text = number?.toString() ?: ""
            if (number != null) {
                numberTextView.setTextColor(Color.BLACK)
            } else {
                numberTextView.setTextColor(Color.BLUE)
            }
        }
    }
}

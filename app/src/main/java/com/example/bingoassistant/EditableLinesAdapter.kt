// EditableLinesAdapter.kt
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bingoassistant.R

class EditableLinesAdapter(private val listener: OnEditListener) :
    RecyclerView.Adapter<EditableLinesAdapter.LineViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LineViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_editable_line, parent, false)
        return LineViewHolder(view)
    }

    override fun onBindViewHolder(holder: LineViewHolder, position: Int) {
        holder.bind(position + 1)
    }

    override fun getItemCount(): Int {
        return 15 // Assuming 15 editable lines
    }

    inner class LineViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textViewEntryNumber: TextView = itemView.findViewById(R.id.textViewEntryNumber)
        private val editTextName: EditText = itemView.findViewById(R.id.editTextName)

        fun bind(entryNumber: Int) {
            textViewEntryNumber.text = entryNumber.toString()
            editTextName.setText("") // Clear any existing text
            editTextName.setOnFocusChangeListener { _, hasFocus ->
                if (!hasFocus) {
                    val newName = editTextName.text.toString()
                    listener.onEdit(entryNumber, newName)
                }
            }
        }
    }

    interface OnEditListener {
        fun onEdit(entryNumber: Int, newName: String)
    }
}

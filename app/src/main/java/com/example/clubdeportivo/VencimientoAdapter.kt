import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.clubdeportivo.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class VencimientoAdapter(private val vencimientos: List<Vencimiento>) :
    RecyclerView.Adapter<VencimientoAdapter.VencimientoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VencimientoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_vencimiento, parent, false)
        return VencimientoViewHolder(view)
    }

    override fun onBindViewHolder(holder: VencimientoViewHolder, position: Int) {
        val vencimiento = vencimientos[position]
        holder.bind(vencimiento)
    }

    override fun getItemCount(): Int = vencimientos.size

    inner class VencimientoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val socioNameTextView: TextView = itemView.findViewById(R.id.socioName)
        private val fechaVencimientoTextView: TextView = itemView.findViewById(R.id.fechaVencimiento)
        //private val whatsappIcon: ImageView = itemView.findViewById(R.id.whatsappIcon)

        fun bind(vencimiento: Vencimiento) {
            socioNameTextView.text = vencimiento.socioNombre

            // Convertir el timestamp a fecha en formato dd/MM/yyyy
            val date = Date(vencimiento.fechaVencimiento)
            val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val formattedDate = dateFormat.format(date)

            fechaVencimientoTextView.text = "Fecha Vencimiento: $formattedDate"
        }
    }
}

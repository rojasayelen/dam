package com.example.clubdeportivo

import Socio
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SocioAdapter(
    private var socios: List<Socio>,
    private val onItemClick: (Socio) -> Unit // Listener para el clic en cada elemento
) : RecyclerView.Adapter<SocioAdapter.SocioViewHolder>() {

    private var selectedPosition: Int = RecyclerView.NO_POSITION

    inner class SocioViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val socioName: TextView = itemView.findViewById(R.id.socioName)
        val socioRole: TextView = itemView.findViewById(R.id.socioRole)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    // Actualizar la selección del ítem
                    notifyItemChanged(selectedPosition) // Desmarcar el elemento anterior
                    selectedPosition = position // Actualizar la posición seleccionada
                    notifyItemChanged(selectedPosition) // Marcar el nuevo elemento
                    onItemClick(socios[position])
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SocioViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_socio, parent, false)
        return SocioViewHolder(view)
    }

    override fun onBindViewHolder(holder: SocioViewHolder, position: Int) {
        val socio = socios[position]
        holder.socioName.text = "${socio.nombre} ${socio.apellido}"
        holder.socioRole.text = socio.tipo

        // Cambia el fondo del item si está seleccionado
        holder.itemView.setBackgroundResource(
            if (position == selectedPosition) R.color.selected_item_background else R.color.default_item_background
        )
    }

    override fun getItemCount() = socios.size

    fun actualizarLista(nuevaLista: List<Socio>) {
        socios = nuevaLista
        notifyDataSetChanged()
    }
}

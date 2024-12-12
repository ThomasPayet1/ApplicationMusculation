package com.ismin.android

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SalleMusculationAdapter(
    private var salleMusculations: List<SalleMusculation>,
    private val listener: (SalleMusculation) -> Unit
) : RecyclerView.Adapter<SalleMusculationAdapter.SalleMusculationViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SalleMusculationViewHolder {
        val rowView = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_salle_musculation, parent, false)
        return SalleMusculationViewHolder(rowView)
    }

    override fun onBindViewHolder(holder: SalleMusculationViewHolder, position: Int) {
        holder.bind(salleMusculations[position], listener)
    }

    override fun getItemCount(): Int {
        return salleMusculations.size
    }

    // Met à jour la liste des salles et trie les favoris en premier
    fun updateData(newData: List<SalleMusculation>) {
        salleMusculations = newData
        notifyDataSetChanged() // Rafraîchit l'affichage
    }

    class SalleMusculationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.r_salle_musculation_txv_name)
        private val comNameTextView: TextView = itemView.findViewById(R.id.r_salle_musculation_txv_comName)
        private val dateCreationTextView: TextView = itemView.findViewById(R.id.r_salle_musculation_txv_dateCreation)
        private val idTextView: TextView = itemView.findViewById(R.id.r_salle_musculation_txv_id)
        private val starImageView: ImageView = itemView.findViewById(R.id.r_salle_musculation_star)

        fun bind(salle: SalleMusculation, listener: (SalleMusculation) -> Unit) {
            nameTextView.text = salle.name
            comNameTextView.text = salle.comName
            dateCreationTextView.text = salle.dateCreation
            idTextView.text = salle.id

            // Affiche ou masque l'étoile selon l'état des favoris
            starImageView.visibility = if (salle.favoris) View.VISIBLE else View.GONE

            // Ajoute un clic sur l'élément
            itemView.setOnClickListener {
                listener(salle)
            }
        }
    }
}
package com.ismin.android

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment

class SalleMusculationDetailFragment : Fragment() {

    private var salle: SalleMusculation? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Charge le layout du fragment
        return inflater.inflate(R.layout.fragment_salle_musculation_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Récupère l'objet SalleMusculation transmis via arguments
        salle = arguments?.getSerializable("selectedSalle") as? SalleMusculation

        // Affiche toutes les informations si les données sont disponibles
        salle?.let {
            view.findViewById<TextView>(R.id.detail_id).text = "ID : ${it.id}"
            view.findViewById<TextView>(R.id.detail_name).text = "Nom : ${it.name}"
            view.findViewById<TextView>(R.id.detail_addr).text = "Adresse : ${it.addr}"
            view.findViewById<TextView>(R.id.detail_date_creation).text = "Date de création : ${it.dateCreation}"
            view.findViewById<TextView>(R.id.detail_com_name).text = "Commune : ${it.comName}"
            view.findViewById<TextView>(R.id.detail_equip_type_name).text = "Type d'équipement : ${it.equipTypeName}"
            view.findViewById<TextView>(R.id.detail_equip_type_famille).text = "Famille d'équipement : ${it.equipTypeFamille}"
            view.findViewById<TextView>(R.id.detail_coordonnees).text = "Coordonnées : ${it.coordonnees.lat}, ${it.coordonnees.lon}"
            view.findViewById<TextView>(R.id.detail_equip_aps_name).text = "Equipements associés : ${it.equipApsname.joinToString(", ")}"
            view.findViewById<TextView>(R.id.detail_reg_name).text = "Région : ${it.regName}"
            view.findViewById<TextView>(R.id.detail_favoris).text = if (it.favoris) "Favoris : Oui" else "Favoris : Non"

            // Configure le bouton de favoris
            val favorisButton = view.findViewById<Button>(R.id.detail_favoris_button)
            updateFavorisButton(favorisButton, it.favoris)

            favorisButton.setOnClickListener { _ ->
                // Inverse le statut des favoris
                it.favoris = !it.favoris
                view.findViewById<TextView>(R.id.detail_favoris).text =
                    if (it.favoris) "Favoris : Oui" else "Favoris : Non"
                updateFavorisButton(favorisButton, it.favoris)

                // Notifie le fragment parent des modifications
                notifyParent(it)
            }
        }
    }

    private fun updateFavorisButton(button: Button, isFavori: Boolean) {
        if (isFavori) {
            button.text = "Retirer des favoris"
        } else {
            button.text = "Ajouter aux favoris"
        }
    }

    private fun notifyParent(updatedSalle: SalleMusculation) {
        // Cherche l'activité parente et appelle la méthode pour mettre à jour les favoris
        (activity as? MainActivity)?.updateFavorisList(updatedSalle)
    }
}
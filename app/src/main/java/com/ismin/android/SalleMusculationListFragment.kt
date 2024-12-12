package com.ismin.android

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

private const val SALLEMUSCULATIONS = "salleMusculations"

class SalleMusculationListFragment(
    private val onSalleSelected: (SalleMusculation) -> Unit
) : Fragment() {
    private lateinit var salleMusculations: ArrayList<SalleMusculation>
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: SalleMusculationAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            salleMusculations = it.getSerializable(SALLEMUSCULATIONS) as ArrayList<SalleMusculation>
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_salle_musculation_list, container, false)

        // Configure le RecyclerView
        recyclerView = rootView.findViewById(R.id.f_salle_musculation_list_rcv_salle_musculations)
        adapter = SalleMusculationAdapter(salleMusculations) { salle ->
            onSalleSelected(salle) // Appeler le callback lorsque l'utilisateur clique
        }

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

        return rootView
    }

    fun refreshList(updatedSalleMusculations: List<SalleMusculation>) {
        val sortedList = updatedSalleMusculations.sortedByDescending { it.favoris }

        adapter.updateData(sortedList)
    }

    companion object {
        fun newInstance(
            salleMusculations: ArrayList<SalleMusculation>,
            onSalleSelected: (SalleMusculation) -> Unit
        ) = SalleMusculationListFragment(onSalleSelected).apply {
            arguments = Bundle().apply {
                putSerializable(SALLEMUSCULATIONS, salleMusculations)
            }
        }
    }
}
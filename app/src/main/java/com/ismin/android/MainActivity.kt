package com.ismin.android

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.UnknownHostException

const val SERVER_BASE_URL = "https://app-a45a5aa3-ec16-4a35-a2c9-0283016b801f.cleverapps.io"

class MainActivity : AppCompatActivity(), SalleMusculationCreator {
    private val salleMuscuL = SalleMusculationL()

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(SERVER_BASE_URL)
        .build()

    private val salleMusculationService = retrofit.create(SalleMusculationService::class.java)

    private val btnRefreshPage: FloatingActionButton by lazy {
        findViewById(R.id.a_main_btn_refreshPage)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Chargement initial des données
        salleMusculationService.getAllSalleMusculation()
            .enqueue(object : Callback<List<SalleMusculation>> {
                override fun onResponse(
                    call: Call<List<SalleMusculation>>,
                    response: Response<List<SalleMusculation>>
                ) {
                    val allSalleMusculations: List<SalleMusculation>? = response.body()
                    allSalleMusculations?.forEach { salleMuscuL.addSalleMusculation(it) }
                    displaySalleListFragment()
                }

                override fun onFailure(call: Call<List<SalleMusculation>>, t: Throwable) {
                    if (t is UnknownHostException) {
                        Log.e("Network Error", "Unable to resolve host: ${t.message}")
                    } else {
                        Log.e("Retrofit", "Erreur : ${t.message}")
                    }
                }
            })

        btnRefreshPage.setOnClickListener {
            displaySalleListFragment()
        }
    }

    // Affiche les détails d'une salle
    private fun displaySalleDetailFragment(salleMusculation: SalleMusculation) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        val detailFragment = SalleMusculationDetailFragment().apply {
            arguments = Bundle().apply {
                putSerializable("selectedSalle", salleMusculation)
            }
        }
        fragmentTransaction.replace(R.id.a_main_lyt_container, detailFragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
        btnRefreshPage.visibility = View.GONE
    }

    // Affiche les détails d'une salle
    private fun displaySalleListFragment() {
        val sortedList = salleMuscuL.getAllSalleMusculation().sortedByDescending { it.favoris }

        val fragmentTransaction = supportFragmentManager.beginTransaction()
        val salleListFragment = SalleMusculationListFragment.newInstance(
            ArrayList(sortedList) // Tri avant d'envoyer les données
        ) { salle ->
            displaySalleDetailFragment(salle) // Naviguer vers le détail au clic
        }
        fragmentTransaction.replace(R.id.a_main_lyt_container, salleListFragment)
        fragmentTransaction.commit()
        btnRefreshPage.visibility = View.VISIBLE
    }

    // Met à jour la liste des favoris
    fun updateFavorisList(updatedSalle: SalleMusculation) {
        // Met à jour l'état de la salle dans la liste
        salleMuscuL.getAllSalleMusculation().find { it.id == updatedSalle.id }?.favoris = updatedSalle.favoris

        // Trie les salles musculations pour placer les favoris en haut
        val sortedList = salleMuscuL.getAllSalleMusculation().sortedByDescending { it.favoris }

        // Recharge le fragment parent avec la liste triée
        val currentFragment = supportFragmentManager.findFragmentById(R.id.a_main_lyt_container)
        if (currentFragment is SalleMusculationListFragment) {
            currentFragment.refreshList(sortedList)
        }
    }

    // Affiche le fragment Info
    private fun displayInfoFragment() {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        val infoFragment = InfoFragment()
        fragmentTransaction.replace(R.id.a_main_lyt_container, infoFragment)
        fragmentTransaction.commit()
        btnRefreshPage.visibility = View.GONE
    }

    // Ouvre l'activité pour la carte
    private fun displayMapActivity() {
        val intent = Intent(this, MapActivity::class.java)
        intent.putExtra("SalleMuscuListe", salleMuscuL.getAllSalleMusculation())
        startActivity(intent)
        btnRefreshPage.visibility = View.GONE
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_liste -> {
                displaySalleListFragment()
                true
            }
            R.id.action_map -> {
                displayMapActivity()
                true
            }
            R.id.action_info -> {
                displayInfoFragment()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSalleMusculationCreated(salleMusculation: SalleMusculation) {
        salleMuscuL.addSalleMusculation(salleMusculation)
        displayInfoFragment()
    }
}
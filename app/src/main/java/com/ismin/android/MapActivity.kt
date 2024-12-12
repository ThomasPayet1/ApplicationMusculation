package com.ismin.android

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
class MapActivity : AppCompatActivity(), OnMapReadyCallback {

    private val btnLeave: FloatingActionButton by lazy {
        findViewById(R.id.a_map_btn_leave)
    }

    private val salleMusculations: ArrayList<SalleMusculation>
        get() {
            return intent.getSerializableExtra("SalleMuscuListe") as? ArrayList<SalleMusculation> ?: ArrayList()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        val supportMapFragment = SupportMapFragment.newInstance()
        supportFragmentManager.beginTransaction()
            .replace(R.id.a_map_layout,supportMapFragment)
            .commit()
        supportMapFragment.getMapAsync(this)
        btnLeave.setOnClickListener {
            finish()
        }
    }
    override fun onMapReady(map:GoogleMap){
        for(item in this.salleMusculations){
            if (item.coordonnees !=null) {
                val ville = LatLng(item.coordonnees.get(0), item.coordonnees.get(1))
                map.addMarker(
                    MarkerOptions()
                        .position(ville)
                        .title(item.name)
                        .snippet(item.addr)
                )
                map.moveCamera(CameraUpdateFactory.newLatLng(ville))
            }
        }
        map.moveCamera(CameraUpdateFactory.zoomTo(4F))
    }


}
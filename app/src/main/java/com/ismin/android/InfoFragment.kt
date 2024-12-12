package com.ismin.android

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class InfoFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_info, container, false)

        val appDescription: TextView = rootView.findViewById(R.id.info_app_description)
        appDescription.text =
            "Cette application permet de localiser et informer sur les salles de musculations proches de votre emplacement."

        val appAuthors: TextView = rootView.findViewById(R.id.info_app_authors)
        appAuthors.text = "ABDUL Bilal et PAYET Thomas"

        val appSource: TextView = rootView.findViewById(R.id.info_app_source)
        appSource.text = "https://data.opendatasoft.com/explore/dataset/data-es%40ra-normandie-dataeducation/table/"

        return rootView
    }
}

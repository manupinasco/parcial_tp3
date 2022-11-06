package ar.edu.ort.parcialtp3.settings

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.findNavController
import androidx.preference.PreferenceManager
import ar.edu.ort.parcialtp3.R

class SettingsFragment : Fragment() {

    lateinit var v : View
    lateinit var btnSettings : Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_settings, container, false)

        btnSettings = v.findViewById(R.id.btnSettings)

        return v
    }

    override fun onStart() {
        super.onStart()

        val prefs = PreferenceManager.getDefaultSharedPreferences(requireContext())

        Log.d("Test",prefs.getBoolean("sync",false).toString())
        prefs.getString("reply_string","")?.let { Log.d("Test", it) }
        prefs.getString("signature_string","default signature")?.let { Log.d("Test", it) }
        prefs.getString("edit_text_preference_1","aca no hay nada")?.let { Log.d("Test", it) }

        //El boton que te lleva a los setings
        btnSettings.setOnClickListener {

            val action = SettingsFragmentDirections.actionSettingsFragmentToSettingsActivity()
            v.findNavController().navigate(action)

        }
    }


}
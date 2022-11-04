package ar.edu.ort.parcialtp3.characters

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import ar.edu.ort.parcialtp3.R
import ar.edu.ort.parcialtp3.databinding.FragmentDetailsBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton

class DetailsFragment : Fragment() {

    //Realizamos el binding
    private var _binding: FragmentDetailsBinding?   = null
    private val binding get()                       = _binding!!

    //Declaramos las variables necesarias
    private lateinit var character_name             : TextView
    private lateinit var character_status           : TextView
    private lateinit var character_origin           : TextView
    private lateinit var character_specie           : TextView
    private lateinit var character_image            : ImageView
    private lateinit var favourite_button           : FloatingActionButton




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding          = FragmentDetailsBinding.inflate(inflater, container, false)
        character_status  = binding.characterStatus
        character_name    = binding.nameCharacter
        character_origin  = binding.originCharacter
        character_specie  = binding.characterSpecies
        character_image   = binding.characterImage
        favourite_button  = binding.addCharacter

        return binding.root
    }
}
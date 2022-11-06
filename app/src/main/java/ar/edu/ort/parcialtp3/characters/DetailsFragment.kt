package ar.edu.ort.parcialtp3.characters

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import ar.edu.ort.parcialtp3.R
import ar.edu.ort.parcialtp3.databinding.FragmentDetailsBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.squareup.picasso.Picasso

class DetailsFragment : Fragment() {

    //Realizamos el binding
    private var _binding: FragmentDetailsBinding?   = null
    private val binding get()                       = _binding!!

    //Declaramos las variables necesarias
    private lateinit var characterName             : TextView
    private lateinit var characterStatus           : TextView
    private lateinit var characterOrigin           : TextView
    private lateinit var characterSpecie           : TextView
    private lateinit var characterImage            : ImageView
    private lateinit var favourite_button           : FloatingActionButton
    private lateinit var colorLive                  : TextView




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding          = FragmentDetailsBinding.inflate(inflater, container, false)
        characterStatus  = binding.characterStatus
        characterName    = binding.nameCharacter
        characterOrigin  = binding.originCharacter
        characterSpecie  = binding.characterSpecies
        characterImage   = binding.characterImage
        favourite_button  = binding.addCharacter
        colorLive         = binding.circle



        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let{
            val personage = DetailsFragmentArgs.fromBundle(it).personage
            val origin = DetailsFragmentArgs.fromBundle(it).origin


            characterName.text   = personage.name
            characterStatus.text = personage.status
            characterOrigin.text = "Origen: " + origin.name
            characterSpecie.text = "Especie: " + personage.species
            Picasso.get().load(personage.image).into(characterImage)

            if(characterStatus.text == "Dead" ) {
                //colorLive.setBackgroundColor(Color.parseColor("#FF0000"))
                colorLive.setBackgroundResource(R.drawable.circle_status_red)
            }
            else if(characterStatus.text == "unknown"){
                colorLive.setBackgroundResource(R.drawable.circle_status_yellow)

            }else{
                colorLive.setBackgroundResource(R.drawable.circle_status)

            }


        }

    }
}
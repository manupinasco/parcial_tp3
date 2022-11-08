package ar.edu.ort.parcialtp3.characters

import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.preference.PreferenceManager
import ar.edu.ort.parcialtp3.R
import ar.edu.ort.parcialtp3.backmethod.IOnBackPressed
import ar.edu.ort.parcialtp3.databinding.FragmentDetailsBinding
import ar.edu.ort.parcialtp3.model.CharactersUsers
import ar.edu.ort.parcialtp3.model.User
import ar.edu.ort.parcialtp3.repository.UserRepository
import ar.edu.ort.parcialtp3.repository.CharactersUsersRepository
import ar.edu.ort.parcialtp3.usersession.UserSession

import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch

class DetailsFragment : Fragment(), IOnBackPressed{

    //Realizamos el binding
    private var _binding: FragmentDetailsBinding?   = null
    private val binding get()                       = _binding!!

    //Declaramos las variables necesarias
    private lateinit var characterName             : TextView
    private lateinit var characterStatus           : TextView
    private lateinit var characterOrigin           : TextView
    private lateinit var characterSpecie           : TextView
    private lateinit var characterImage            : ImageView
    private lateinit var favouriteButton           : FloatingActionButton
    private lateinit var colorLive                  : TextView
    private lateinit var charactersUsersRepository: CharactersUsersRepository


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding          = FragmentDetailsBinding.inflate(inflater, container, false)
        context?.let { charactersUsersRepository = CharactersUsersRepository.getInstance(it) }
        characterStatus  = binding.characterStatus
        characterName    = binding.nameCharacter
        characterOrigin  = binding.originCharacter
        characterSpecie  = binding.characterSpecies
        characterImage   = binding.characterImage
        favouriteButton  = binding.addCharacter
        colorLive        = binding.circle

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let{
            var personage = DetailsFragmentArgs.fromBundle(it).personage
            val origin = DetailsFragmentArgs.fromBundle(it).origin

            characterName.text   = personage.name
            characterStatus.text = personage.status
            characterOrigin.text = "Origen: " + origin.name
            characterSpecie.text = "Especie: " + personage.species
            Picasso.get().load(personage.image).into(characterImage)

            if(characterStatus.text.equals("Dead")) {
                colorLive.setBackgroundResource(R.drawable.circle_status_red)
            }
            else if(characterStatus.text.equals("unknown")){
                colorLive.setBackgroundResource(R.drawable.circle_status_yellow)

            }else{
                colorLive.setBackgroundResource(R.drawable.circle_status)

            }

            lifecycleScope.launch {
                val userId = UserSession.idUser
                if (userId != null) {
                    val characterUser =
                        charactersUsersRepository.getCharacterUserByIdUserAndByIdCharacter(
                            userId,
                            personage.id
                        )
                    if (characterUser != null)
                        favouriteButton.setImageResource(R.drawable.ic_favorite)
                }
                val prefs = PreferenceManager.getDefaultSharedPreferences(requireContext())

                if (prefs.getBoolean("enableFav",true)) {
                    favouriteButton.setOnClickListener {
                        lifecycleScope.launch {
                            val userId = UserSession.idUser
                            if (userId != null) {
                                val characterUser =
                                    charactersUsersRepository.getCharacterUserByIdUserAndByIdCharacter(
                                        userId,
                                        personage.id
                                    )
                                if (characterUser == null) {
                                    favouriteButton.setImageResource(R.drawable.ic_favorite)
                                    charactersUsersRepository.addCharacterUser(
                                        CharactersUsers(
                                            idUser = userId,
                                            idCharacter = personage.id
                                        )
                                    )
                                    Toast.makeText(
                                        context,
                                        "Personaje agregado a favoritos",
                                        Toast.LENGTH_SHORT
                                    )
                                        .show()
                                } else {
                                    favouriteButton.setImageResource(R.drawable.ic_favorite_not_selected)
                                    charactersUsersRepository.removeCharacterUser(characterUser)
                                    Toast.makeText(
                                        context,
                                        "Personaje removido de favoritos",
                                        Toast.LENGTH_SHORT
                                    )
                                        .show()
                                }

                            }


                        }
                    }
                } else {
                    favouriteButton.setOnClickListener{
                        Toast.makeText(
                        context,
                        "Deshabilitado agregar a Favoritos",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                    }

                }
            }
        }
    }


    override fun onBackPressed(): Boolean {
        return true
    }


}
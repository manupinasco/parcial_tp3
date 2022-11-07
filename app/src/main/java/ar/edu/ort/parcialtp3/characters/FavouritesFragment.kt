package ar.edu.ort.parcialtp3.characters

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ar.edu.ort.jefud_notifying_system.listener.onItemClickListener
import ar.edu.ort.parcialtp3.R
import ar.edu.ort.parcialtp3.adapter.CharacterAdapter
import ar.edu.ort.parcialtp3.databinding.FragmentFavouritesBinding
import ar.edu.ort.parcialtp3.databinding.FragmentHomeBinding
import ar.edu.ort.parcialtp3.model.ApiData
import ar.edu.ort.parcialtp3.model.Personage
import ar.edu.ort.parcialtp3.model.PersonageWithOrigin
import ar.edu.ort.parcialtp3.repository.CharactersUsersRepository
import ar.edu.ort.parcialtp3.service.ApiBuilder
import ar.edu.ort.parcialtp3.usersession.UserSession
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FavouritesFragment : Fragment(), onItemClickListener {
    private var _binding: FragmentFavouritesBinding? = null
    private val binding get()= _binding!!
    private lateinit var recFavouriteCharacter: RecyclerView
    private lateinit var gridLayoutManager: GridLayoutManager
    private lateinit var characterListAdapter: CharacterAdapter
    private var charactersList: List<PersonageWithOrigin> = arrayListOf<PersonageWithOrigin>()
    private lateinit var charactersUsersRepository: CharactersUsersRepository


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavouritesBinding.inflate(inflater, container, false)
        context?.let { charactersUsersRepository = CharactersUsersRepository.getInstance(it) }
        getCharacters()
        recFavouriteCharacter = binding.recyclerViewFavourites
        recFavouriteCharacter.setHasFixedSize(true)
        gridLayoutManager = GridLayoutManager(context,2)
        recFavouriteCharacter.layoutManager = gridLayoutManager
        return binding.root
    }

    private fun getCharacters() {
        val service = ApiBuilder.create()
        var ids = ""
        lifecycleScope.launch {
            val userId = UserSession.idUser
            if(userId != null){
                val charactersUsers = charactersUsersRepository.getCharacterUserByIdUser(userId)
                for (charactersUser in charactersUsers){
                    ids = ids + (charactersUser.idCharacter) + ","
                }
                Log.d("Hola",ids)
                service.getCharactersById(ids).enqueue(object : Callback<List<PersonageWithOrigin>> {
                    override fun onResponse(call: Call<List<PersonageWithOrigin>>, response: Response<List<PersonageWithOrigin>>) {
                        if (response.isSuccessful){
                            recFavouriteCharacter.visibility = View.VISIBLE
                            charactersList = response.body()!!
                            recFavouriteCharacter.adapter = CharacterAdapter(charactersList,this@FavouritesFragment)
                        }
                    }

                    override fun onFailure(call: Call<List<PersonageWithOrigin>>, t: Throwable) {
                        Log.e("Example", t.stackTraceToString())
                    }

                })
            }
        }
    }

    override fun onViewItemDetail(personageWithOrigin: PersonageWithOrigin) {
        val personage = Personage(personageWithOrigin.id,personageWithOrigin.name,personageWithOrigin.status,personageWithOrigin.species,personageWithOrigin.image)
        findNavController().navigate(FavouritesFragmentDirections.actionFavouritesFragmentToDetailsFragment(personage,personageWithOrigin.origin))
    }
}
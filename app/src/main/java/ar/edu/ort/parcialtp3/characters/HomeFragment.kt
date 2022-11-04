package ar.edu.ort.parcialtp3.characters

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ar.edu.ort.parcialtp3.adapter.CharacterAdapter
import ar.edu.ort.parcialtp3.databinding.FragmentHomeBinding
import ar.edu.ort.parcialtp3.model.ApiData
import ar.edu.ort.parcialtp3.model.Personaje
import ar.edu.ort.parcialtp3.service.ApiBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get()= _binding!!
    private lateinit var recCharacter: RecyclerView
    private lateinit var gridLayoutManager: GridLayoutManager
    private lateinit var characterListAdapter: CharacterAdapter
    private var charactersList: List<Personaje> = arrayListOf<Personaje>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        getCharacters()
        recCharacter = binding.characterRecyclerView
        recCharacter.setHasFixedSize(true)
        gridLayoutManager = GridLayoutManager(context,2)
        recCharacter.layoutManager = gridLayoutManager
        characterListAdapter = CharacterAdapter(charactersList)

        return binding.root
    }



    private fun getCharacters(){
        val service = ApiBuilder.create()

        service.getAllCharacters().enqueue(object : Callback<ApiData>{
            override fun onResponse(call: Call<ApiData>, response: Response<ApiData>) {
                if (response.isSuccessful){
                    charactersList = response.body()!!.results
                    recCharacter.adapter = CharacterAdapter(charactersList)
                }
            }

            override fun onFailure(call: Call<ApiData>, t: Throwable) {
                Log.e("Example", t.stackTraceToString())
            }

        })
    }
}
package ar.edu.ort.parcialtp3.characters

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ar.edu.ort.jefud_notifying_system.listener.onItemClickListener
import ar.edu.ort.parcialtp3.adapter.CharacterAdapter
import ar.edu.ort.parcialtp3.databinding.FragmentHomeBinding
import ar.edu.ort.parcialtp3.model.ApiData
import ar.edu.ort.parcialtp3.model.Personaje
import ar.edu.ort.parcialtp3.service.ApiBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeFragment : Fragment(), onItemClickListener {

    private var _binding: FragmentHomeBinding? = null
    private val binding get()= _binding!!
    private lateinit var recCharacter: RecyclerView
    private lateinit var searchEditText: EditText
    private lateinit var gridLayoutManager: GridLayoutManager
    private lateinit var characterListAdapter: CharacterAdapter
    private var charactersList: List<Personaje> = arrayListOf<Personaje>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        searchEditText = binding.searchEditText
        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                if(s.toString().length < 3){
                    recCharacter.visibility = View.INVISIBLE
                } else{
                    getCharacters(s.toString())
                }
            }
        })
        recCharacter = binding.characterRecyclerView
        recCharacter.setHasFixedSize(true)
        gridLayoutManager = GridLayoutManager(context,2)
        recCharacter.layoutManager = gridLayoutManager

        return binding.root
    }



    private fun getCharacters(text: String) {
        val service = ApiBuilder.create()

        service.getCharacters(text).enqueue(object : Callback<ApiData>{
            override fun onResponse(call: Call<ApiData>, response: Response<ApiData>) {
                if (response.isSuccessful){
                    recCharacter.visibility = View.VISIBLE
                    charactersList = response.body()!!.results
                    recCharacter.adapter = CharacterAdapter(charactersList,this@HomeFragment)
                } else{
                    recCharacter.visibility = View.INVISIBLE
                }
            }

            override fun onFailure(call: Call<ApiData>, t: Throwable) {
                Log.e("Example", t.stackTraceToString())
            }

        })
    }

    override fun onViewItemDetail(personaje: Personaje) {
        findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDetailsFragment())
    }
}
package ar.edu.ort.parcialtp3.characters

import android.content.DialogInterface
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ar.edu.ort.jefud_notifying_system.listener.onItemClickListener
import ar.edu.ort.parcialtp3.adapter.CharacterAdapter
import ar.edu.ort.parcialtp3.backmethod.IOnBackPressed
import ar.edu.ort.parcialtp3.databinding.FragmentHomeBinding
import ar.edu.ort.parcialtp3.model.ApiData
import ar.edu.ort.parcialtp3.model.PersonageWithOrigin
import ar.edu.ort.parcialtp3.model.Personage
import ar.edu.ort.parcialtp3.service.ApiBuilder
import ar.edu.ort.parcialtp3.usersession.UserSession
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeFragment : Fragment(), onItemClickListener, IOnBackPressed {

    private var _binding: FragmentHomeBinding? = null
    private val binding get()= _binding!!
    private lateinit var recCharacter: RecyclerView
    private lateinit var searchEditText: EditText
    private lateinit var alertText: TextView
    private lateinit var gridLayoutManager: GridLayoutManager
    private lateinit var adapter: CharacterAdapter
    private var charactersList: List<PersonageWithOrigin> = arrayListOf<PersonageWithOrigin>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        alertText = binding.alertText

        searchEditText = binding.searchEditText
        getAllCharacters()



        alertText.visibility = View.VISIBLE
        alertText.text = "Complete el buscador"

            searchEditText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
                override fun afterTextChanged(s: Editable) {
                    val prefs = PreferenceManager.getDefaultSharedPreferences(requireContext())
                    if(prefs.getString("defaultCharacter","").toString() == "" || s.toString().length > 0) {
                        if(s.toString().length < 3){
                            alertText.visibility = View.VISIBLE
                            recCharacter.visibility = View.INVISIBLE
                            if(s.toString().length == 2){
                                alertText.text = "Escriba 1 caracter mas"
                            }else if(s.toString().length == 1){
                                alertText.text = "Escriba 2 caracteres mas"
                            }else{
                                alertText.visibility = View.INVISIBLE
                                getAllCharacters()
                            }
                        } else{
                            getCharacters(s.toString())
                        }
                    } else {
                        getCharacters(prefs.getString("defaultCharacter","").toString())
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
                    alertText.visibility = View.INVISIBLE
                    recCharacter.visibility = View.VISIBLE
                    charactersList = response.body()!!.results
                    recCharacter.adapter = CharacterAdapter(charactersList,this@HomeFragment)
                } else{
                    recCharacter.visibility = View.INVISIBLE
                    alertText.text = "Busqueda no encontrada"
                }
            }

            override fun onFailure(call: Call<ApiData>, t: Throwable) {
                Log.e("Example", t.stackTraceToString())
            }

        })
    }

    private fun getAllCharacters() {
        val service = ApiBuilder.create()
        var ids = ""
        for (i in 1..826) {
            ids = "$ids$i,"
        }
        service.getCharactersById(ids).enqueue(object : Callback<List<PersonageWithOrigin>> {
            override fun onResponse(call: Call<List<PersonageWithOrigin>>, response: Response<List<PersonageWithOrigin>>) {
                if (response.isSuccessful){
                    recCharacter.visibility = View.VISIBLE
                    charactersList = response.body()!!
                    recCharacter.adapter = CharacterAdapter(charactersList,this@HomeFragment)
                } else{

                }
            }

            override fun onFailure(call: Call<List<PersonageWithOrigin>>, t: Throwable) {
                Log.e("Example", t.stackTraceToString())
            }

        })
    }

    /*Al tocar el card crea un personaje al partir del personaje con origen
    * y lo pasa como parametro junto al origen como parcelables*/
    override fun onViewItemDetail(personageWithOrigin : PersonageWithOrigin) {
        val personage = Personage(personageWithOrigin.id,personageWithOrigin.name,personageWithOrigin.status,personageWithOrigin.species,personageWithOrigin.image)
        findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDetailsFragment(personage,personageWithOrigin.origin))
    }

    override fun onBackPressed(): Boolean {
        showAlertDialogLogout()
        return false
    }


    private fun showAlertDialogLogout() {
        val builder = AlertDialog.Builder(requireContext())

        with(builder)
        {
            setTitle("Rick y Morty")
            setMessage("¿Confirmar cierre de sesión?")
            setNegativeButton("Sí") { _: DialogInterface, _: Int ->
                logout()
            }
            setPositiveButton("Cancelar") { _: DialogInterface, _: Int ->

            }
            show()
        }
    }

    private fun logout() {

        UserSession.idUser = -1
        val action = HomeFragmentDirections.actionHomeFragmentToLoginFragment(false)
        findNavController().navigate(action)
    }
}
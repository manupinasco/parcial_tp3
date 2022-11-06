package ar.edu.ort.parcialtp3.characters

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ar.edu.ort.parcialtp3.R

class FavouritesFragment : Fragment() {

    private lateinit var recyclerViewFavourites: RecyclerView
    //LISTA DE PERSONAJES - OJO CON CHARACTER QUE NO LE HABLA A LA CLASE NUESTRA
    private lateinit var favouritesList: List<Character>
    private lateinit var title: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favourites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerViewFavourites = view.findViewById(R.id.recyclerViewFavourites)
        title = view.findViewById(R.id.favourites_title)

        // Pongo el nombre del usuario en el titulo.
        // Advertencia: Al momento de mostrar un texto al usuario siempre usar un String resource. Nunca hardcodear de
        // esta manera.
        title.text = "Hola, ${UserSession.userName}"
        fillProductList()
    }

    private fun fillProductList() {

        // Lleno una lista con productos que fueron agregados desde home
        favouritesList = listOf()

        // Configuro el recyclerview y le paso un Adapter
        val layoutManager = LinearLayoutManager(context)
        recyclerViewFavourites.layoutManager = layoutManager
                                        //ACA IRIA FAVOURITES ADAPTER
        recyclerViewFavourites.adapter = FavouritesAdapter(favouritesList, this)
    }


}
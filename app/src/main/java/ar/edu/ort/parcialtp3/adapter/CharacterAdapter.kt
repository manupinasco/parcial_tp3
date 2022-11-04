package ar.edu.ort.parcialtp3.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ar.edu.ort.parcialtp3.R
import ar.edu.ort.parcialtp3.model.Personaje

class CharacterAdapter(private val characterList: List<Personaje>): RecyclerView.Adapter<CharacterViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.character_item, parent, false)
        return CharacterViewHolder(view)    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        //Obtengo la posicion del character en el recylcer
        val character = characterList[position]

        //Uso el metodo bind del viewHolder para mostrar sus datos
        holder.bind(character)
    }

    override fun getItemCount(): Int {
        return characterList.size
    }

}
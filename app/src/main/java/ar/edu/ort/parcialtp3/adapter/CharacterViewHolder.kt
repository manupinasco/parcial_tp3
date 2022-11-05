package ar.edu.ort.parcialtp3.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import ar.edu.ort.parcialtp3.R
import ar.edu.ort.parcialtp3.model.Personaje
import com.squareup.picasso.Picasso

class CharacterViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    private val name: TextView = itemView.findViewById(R.id.characterNameCard)
    private val status: TextView = itemView.findViewById(R.id.characterStatusCard)
    private val image: ImageView = itemView.findViewById(R.id.imageView)

    fun bind(personaje: Personaje){
        name.text = personaje.name
        status.text = "Status: " + personaje.status
        Picasso.get().load(personaje.image).into(image)
    }

    fun getCardLayout(): CardView {
        return itemView.findViewById(R.id.cardItem)
    }
}
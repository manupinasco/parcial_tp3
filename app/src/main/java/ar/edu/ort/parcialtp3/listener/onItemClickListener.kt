package ar.edu.ort.jefud_notifying_system.listener

import ar.edu.ort.parcialtp3.model.Personaje


interface onItemClickListener {

    /**      * Se invoca cuando se selecciona un producto de la lista      */
fun onViewItemDetail(personaje : Personaje) }
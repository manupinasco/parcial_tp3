package ar.edu.ort.parcialtp3.model

data class PersonageWithOrigin (
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val image: String,
    val origin: Origin
        )

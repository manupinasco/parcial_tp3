package ar.edu.ort.parcialtp3.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CharactersUsers(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @NonNull @ColumnInfo(name= "idUser") val idUser: Int,
    @NonNull @ColumnInfo(name= "idCharacter") val idCharacter: Int,

    )
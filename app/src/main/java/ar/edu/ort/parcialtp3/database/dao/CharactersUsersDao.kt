package ar.edu.ort.parcialtp3.database.dao

import androidx.room.*
import ar.edu.ort.parcialtp3.model.CharactersUsers

@Dao
interface CharactersUsersDao {
    @Query("SELECT * FROM charactersUsers")
    fun getAll(): List<CharactersUsers>

    @Query("SELECT * from charactersUsers WHERE  idUser = :idUser LIMIT 1")
    fun getCharacterUserByIdUser(idUser: String): CharactersUsers

    @Query("SELECT * from charactersUsers WHERE  idCharacter = :idCharacter LIMIT 1")
    fun getCharacterUserByIdCharacter(idCharacter: String): CharactersUsers

    @Delete
    fun delete(characterUser: CharactersUsers)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(characterUser: CharactersUsers)
}

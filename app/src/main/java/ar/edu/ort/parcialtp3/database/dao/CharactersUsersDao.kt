package ar.edu.ort.parcialtp3.database.dao

import androidx.room.*
import ar.edu.ort.parcialtp3.model.CharactersUsers

@Dao
interface CharactersUsersDao {
    @Query("SELECT * FROM charactersUsers")
    suspend fun getAll(): List<CharactersUsers>

    @Query("SELECT * from charactersUsers WHERE  idUser = :idUser AND idCharacter = :idCharacter LIMIT 1")
    suspend fun getCharacterUserByIdUserAndByIdCharacter(idUser: String, idCharacter: String): CharactersUsers


    @Query("SELECT * from charactersUsers WHERE idUser = :idUser ")
    suspend fun getCharacterUserByIdUser(idUser: String): List<CharactersUsers>

    @Delete
    suspend fun delete(characterUser: CharactersUsers)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(characterUser: CharactersUsers)
}

package ar.edu.ort.parcialtp3.repository

import android.content.Context
import androidx.room.Room
import ar.edu.ort.parcialtp3.database.AppDatabase
import ar.edu.ort.parcialtp3.database.dao.CharactersUsersDao
import ar.edu.ort.parcialtp3.model.CharactersUsers

class CharactersUsersRepository private constructor(appDatabase: AppDatabase) {

    private val charactersUsersDao: CharactersUsersDao = appDatabase.charactersUsersDao()

    suspend fun addCharacterUser(characterUser: CharactersUsers) {
        charactersUsersDao.insert(characterUser)
    }

    suspend fun removeCharacterUser(characterUser: CharactersUsers) {
        charactersUsersDao.delete(characterUser)
    }

    suspend fun getCharacterUserByIdUser(userId : Int): List<CharactersUsers> {
        return charactersUsersDao.getCharacterUserByIdUser(userId.toString())

    }

    suspend fun getCharacterUserByIdUserAndByIdCharacter(userId : Int, characterId: Int): CharactersUsers? {
        return charactersUsersDao.getCharacterUserByIdUserAndByIdCharacter(userId.toString(), characterId.toString())

    }

    companion object {
        private var charactersUsersRepository: CharactersUsersRepository? = null

        fun getInstance(context: Context):  CharactersUsersRepository {
            return charactersUsersRepository ?: kotlin.run {

                val db = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java, "tp3-database"
                ).build()

                val createdCharactersUsersRepository =  CharactersUsersRepository(db)
                charactersUsersRepository =  CharactersUsersRepository(db)
                createdCharactersUsersRepository
            }
        }
    }
}
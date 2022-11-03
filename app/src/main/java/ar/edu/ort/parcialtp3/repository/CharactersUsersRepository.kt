package ar.edu.ort.parcialtp3.repository

import android.content.Context
import androidx.room.Room
import ar.edu.ort.parcialtp3.database.AppDatabase
import ar.edu.ort.parcialtp3.database.dao.CharactersUsersDao
import ar.edu.ort.parcialtp3.model.CharactersUsers

class CharactersUsersRepository private constructor(appDatabase: AppDatabase) {

    private val charactersUsersDao: CharactersUsersDao = appDatabase.charactersUsersDao()

    fun addCharacterUser(characterUser: CharactersUsers) {
        charactersUsersDao.insert(characterUser)
    }

    fun removeCharacterUser(characterUser: CharactersUsers) {
        charactersUsersDao.delete(characterUser)
    }

    fun getAllCharactersUsers(): List<CharactersUsers> {
        return charactersUsersDao.getAll()

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
package ar.edu.ort.parcialtp3.repository

import android.content.Context
import androidx.room.Room
import ar.edu.ort.parcialtp3.database.AppDatabase
import ar.edu.ort.parcialtp3.database.dao.UserDao
import ar.edu.ort.parcialtp3.model.User

class UserRepository private constructor(appDatabase: AppDatabase) {

    private val userDao: UserDao = appDatabase.userDao()

    fun addUser(user: User) {
        userDao.insert(user)
    }

    fun removeUser(user: User) {
        userDao.delete(user)
    }

    fun getAllUser(): List<User> {
        return userDao.getAll()

    }

    companion object {
        private var userRepository: UserRepository? = null

        fun getInstance(context: Context): UserRepository {
            return userRepository ?: kotlin.run {

                val db = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java, "tp3-database"
                ).build()

                val createdUserRepository = UserRepository(db)
                userRepository = UserRepository(db)
                createdUserRepository
            }
        }
    }
}
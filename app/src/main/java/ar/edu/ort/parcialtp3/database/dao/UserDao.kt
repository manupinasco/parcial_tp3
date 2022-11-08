package ar.edu.ort.parcialtp3.database.dao

import androidx.room.*
import ar.edu.ort.parcialtp3.model.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    suspend fun getAll(): List<User>

    @Query("SELECT * from user WHERE name = :name LIMIT 1")
    suspend fun getUser(name: String): User

    @Query("SELECT * from user WHERE id = :id LIMIT 1")
    suspend fun getUserById(id: Int): User

    @Delete
    suspend fun delete(user: User)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(user: User)
}

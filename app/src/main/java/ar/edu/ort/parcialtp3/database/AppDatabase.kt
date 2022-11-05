package ar.edu.ort.parcialtp3.database

import android.app.Application
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ar.edu.ort.parcialtp3.database.dao.*
import ar.edu.ort.parcialtp3.model.*

@Database(entities = [User::class, CharactersUsers::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun charactersUsersDao(): CharactersUsersDao
}



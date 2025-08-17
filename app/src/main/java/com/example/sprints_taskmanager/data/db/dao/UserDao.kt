package com.example.sprints_taskmanager.data.db.dao

import androidx.room.*
import com.example.sprints_taskmanager.data.db.entities.User

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: User): Long

    @Query("SELECT * FROM users")
    suspend fun getAll(): List<User>

    @Query("SELECT * FROM users WHERE id = :id")
    suspend fun getById(id: Int): User?
}
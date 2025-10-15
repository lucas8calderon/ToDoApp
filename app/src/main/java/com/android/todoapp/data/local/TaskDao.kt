package com.android.todoapp.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Query("SELECT * FROM todos ORDER BY id DESC")
    fun getAllTodos(): Flow<List<TaskEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(todo: TaskEntity)

    @Update
    suspend fun update(todo: TaskEntity)

    @Delete
    suspend fun delete(todo: TaskEntity)
}
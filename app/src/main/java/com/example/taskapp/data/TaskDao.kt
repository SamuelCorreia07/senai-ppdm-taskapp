package com.example.taskapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    // C-Create (Insert)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(task: TaskEntity)

    // R- Read (Select All)
    @Query("SELECT * FROM task_table ORDER BY creation_date DESC")
    fun getAllTasks(): Flow<List<TaskEntity>>

    // R - Read (Select por Status - Filtro)
    @Query("SELECT * FROM task_table WHERE is_completed = :isCompleted ORDER BY creation_date DESC")
    fun getTasksByStatus(isCompleted: Boolean): Flow<List<TaskEntity>>

    // U- Update
    @Update
    suspend fun update(task: TaskEntity)

    // D - Delete
    @Delete
    suspend fun delete(task: TaskEntity)

    // D - Delete All
    @Query("DELETE FROM task_table")
    suspend fun deleteAll()

    // U - Update Title (Apenas o título)
    @Query("UPDATE task_table SET title = :newTitle WHERE id = :taskId")
    suspend fun updateTitle(taskId: Int, newTitle: String)


}
package com.example.taskapp.repository

import com.example.taskapp.data.TaskDao
import com.example.taskapp.data.TaskEntity
import kotlinx.coroutines.flow.Flow

class TaskRepository(private val taskDao: TaskDao) {

    // O Flow do DAO já é assíncrono e reativo
    val allTasks: Flow<List<TaskEntity>> = taskDao.getAllTasks()

    // Funções suspend para operações de escrita
    suspend fun insert(task: TaskEntity) {
        taskDao.insert(task)
    }

    suspend fun update(task: TaskEntity) {
        taskDao.update(task)
    }

    suspend fun delete(task: TaskEntity) {
        taskDao.delete(task)
    }

    fun getTasksByStatus(isCompleted: Boolean): Flow<List<TaskEntity>> {
        return taskDao.getTasksByStatus(isCompleted)
    }

    suspend fun updateTitle(taskId: Int, newTitle: String) {
        taskDao.updateTitle(taskId, newTitle)
    }
}
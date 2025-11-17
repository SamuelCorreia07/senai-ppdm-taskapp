package com.example.taskapp

import android.app.Application
import com.example.taskapp.data.TaskDatabase
import com.example.taskapp.repository.TaskRepository

class TaskApplication : Application() {

    // Inicialização preguiçosa (lazy) do Database e Repository
    // O banco de dados só será criado quando for acessado pela primeira vez
    val database by lazy { TaskDatabase.getDatabase(this) }
    // O repositório só será criado quando for acessado pela primeira vez
    val repository by lazy { TaskRepository(database.taskDao()) }
}
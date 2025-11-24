package com.example.taskapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.taskapp.data.TaskEntity
import com.example.taskapp.repository.TaskRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch

// Enum para representar os estados do filtro
enum class TaskFilterState {
    ALL, PENDING, COMPLETED
}

class TaskViewModel(private val repository: TaskRepository) : ViewModel() {

    // 1. Um StateFlow privado para guardar o estado do filtro
    private val _filterState = MutableStateFlow(TaskFilterState.ALL)

    // 2. O LiveData de tarefas agora é REATIVO ao _filterState
    val allTasks = _filterState.flatMapLatest { filter ->
        when (filter) {
            TaskFilterState.ALL -> repository.allTasks
            TaskFilterState.PENDING -> repository.getTasksByStatus(isCompleted = false)
            TaskFilterState.COMPLETED -> repository.getTasksByStatus(isCompleted = true)
        }
    }.asLiveData() // Convertido para LiveData para a Activity

    // 3. Função pública para a Activity mudar o filtro
    fun setFilter(filter: TaskFilterState) {
        _filterState.value = filter
    }

    // --- Operações CRUD ---
    fun insert(title: String) = viewModelScope.launch {
        val newTask = TaskEntity(title = title)
        repository.insert(newTask)
    }

    fun update(task: TaskEntity) = viewModelScope.launch {
        repository.update(task)
    }

    fun delete(task: TaskEntity) = viewModelScope.launch {
        repository.delete(task)
    }

    fun updateTaskTitle(taskId: Int, newTitle: String) = viewModelScope.launch {
        repository.updateTitle(taskId, newTitle)
    }
}

// Factory (sem alteração)
class TaskViewModelFactory(private val repository: TaskRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TaskViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TaskViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
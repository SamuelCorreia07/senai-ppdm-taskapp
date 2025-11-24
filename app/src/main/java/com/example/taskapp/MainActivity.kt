package com.example.taskapp

import android.app.AlertDialog
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.taskapp.data.TaskEntity
import com.example.taskapp.viewmodel.TaskFilterState
import com.example.taskapp.viewmodel.TaskViewModel
import com.example.taskapp.viewmodel.TaskViewModelFactory

class MainActivity : AppCompatActivity() {

    // 1. Inicializa o ViewModel usando a Factory
    // A Factory injeta o Repository (que pegamos da TaskApplication)
    private val taskViewModel: TaskViewModel by viewModels {
        TaskViewModelFactory((application as TaskApplication).repository)
    }

    private lateinit var adapter: TaskAdapter
    private lateinit var spFilter: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Referências das Views
        val etTaskTitle: EditText = findViewById(R.id.etTaskTitle)
        val btnAddTask: Button = findViewById(R.id.btnAddTask)
        val rvTasks: RecyclerView = findViewById(R.id.rvTasks)
        spFilter = findViewById(R.id.spFilter)

        // 2. Configurações
        setupAdapter(rvTasks)
        setupObservers()
        setupClickListeners(etTaskTitle, btnAddTask)

        // 3. Chamar a nova função de setup do Spinner
        setupFilterSpinner()
    }

    private fun setupAdapter(rvTasks: RecyclerView) {
        adapter = TaskAdapter(
            onTaskChecked = { task -> taskViewModel.update(task) },
            onDeleteClicked = { task -> taskViewModel.delete(task) },
            // 3. Implementação do Clique Longo
            onTaskLongClicked = { task ->
                showEditTitleDialog(task)
            }
        )
        rvTasks.adapter = adapter
    }

    private fun showEditTitleDialog(task: TaskEntity) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Editar Tarefa")

        // Cria um campo de texto dentro do dialog
        val input = EditText(this)
        input.setText(task.title)
        builder.setView(input)

        // Botão Salvar
        builder.setPositiveButton("Salvar") { _, _ ->
            val newTitle = input.text.toString().trim()
            if (newTitle.isNotEmpty()) {
                // Chama o ViewModel para atualizar apenas o título
                taskViewModel.updateTaskTitle(task.id, newTitle)
            }
        }

        // Botão Cancelar
        builder.setNegativeButton("Cancelar") { dialog, _ ->
            dialog.cancel()
        }

        builder.show()
    }

    private fun setupObservers() {
        // taskViewModel.allTasks é o LiveData
        // O observer será acionado sempre que a lista de tarefas mudar no banco de dados
        taskViewModel.allTasks.observe(this) { tasks ->
            // A UI é notificada. O 'let' verifica se 'tasks' não é nulo.
            tasks?.let {
                // O ListAdapter (com DiffUtil) calcula a diferença
                // e atualiza o RecyclerView de forma eficiente.
                adapter.submitList(it)
            }
        }
    }

    private fun setupClickListeners(etTaskTitle: EditText, btnAddTask: Button) {
        btnAddTask.setOnClickListener {
            val title = etTaskTitle.text.toString().trim()

            if (title.isNotEmpty()) {
                taskViewModel.insert(title)
                etTaskTitle.setText("") // Limpa o texto
                etTaskTitle.error = null // Remove o erro (caso estivesse aparecendo antes)
            } else {
                // Configura a mensagem de erro no input
                etTaskTitle.error = "Por favor, digite uma tarefa!"
                etTaskTitle.requestFocus() // Dá foco ao campo para o usuário digitar
            }
        }
    }

    private fun setupFilterSpinner() {
        // Cria o adapter para o Spinner
        val filterOptions = listOf("Todas", "Pendentes", "Concluídas")
        val spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, filterOptions)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spFilter.adapter = spinnerAdapter

        // Define o listener
        spFilter.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                // Pega a opção selecionada e informa o ViewModel
                when (position) {
                    0 -> taskViewModel.setFilter(TaskFilterState.ALL)
                    1 -> taskViewModel.setFilter(TaskFilterState.PENDING)
                    2 -> taskViewModel.setFilter(TaskFilterState.COMPLETED)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Não faz nada
            }
        }
    }
}
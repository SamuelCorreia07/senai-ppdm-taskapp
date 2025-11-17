package com.example.taskapp

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.taskapp.data.TaskEntity

class TaskAdapter(
    // Parâmetros: Duas funções lambda que receberão os eventos de clique
    private val onTaskChecked: (TaskEntity) -> Unit,
    private val onDeleteClicked: (TaskEntity) -> Unit
) : ListAdapter<TaskEntity, TaskAdapter.TaskViewHolder>(TaskDiffCallback()) {

    // Cria o ViewHolder (a representação visual de cada item)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view)
    }

    // Vincula os dados (TaskEntity) ao ViewHolder
    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val currentTask = getItem(position)
        holder.bind(currentTask)
    }

    // Classe interna que representa a view de cada item
    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvTitle: TextView = itemView.findViewById(R.id.tvTaskTitle)
        private val cbCompleted: CheckBox = itemView.findViewById(R.id.cbTaskCompleted)
        private val btnDelete: ImageButton = itemView.findViewById(R.id.btnDeleteTask)

        fun bind(task: TaskEntity) {
            tvTitle.text = task.title
            cbCompleted.isChecked = task.isCompleted

            // Aplica ou remove o risco (strikethrough) no texto
            updateStrikeThrough(tvTitle, task.isCompleted)

            // Listener para o CheckBox (operação de Update)
            cbCompleted.setOnCheckedChangeListener { _, isChecked ->
                // Cria uma nova TaskEntity com o status atualizado
                val updatedTask = task.copy(isCompleted = isChecked)
                // Chama a função lambda passada para o adapter
                onTaskChecked(updatedTask)
            }

            // Listener para o botão de Delete (operação de Delete)
            btnDelete.setOnClickListener {
                // Chama a função lambda passada para o adapter
                onDeleteClicked(task)
            }
        }

        private fun updateStrikeThrough(tv: TextView, isCompleted: Boolean) {
            if (isCompleted) {
                tv.paintFlags = tv.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            } else {
                tv.paintFlags = tv.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            }
        }
    }

    // Classe para otimizar a atualização do RecyclerView
    class TaskDiffCallback : DiffUtil.ItemCallback<TaskEntity>() {
        override fun areItemsTheSame(oldItem: TaskEntity, newItem: TaskEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: TaskEntity, newItem: TaskEntity): Boolean {
            return oldItem == newItem
        }
    }
}
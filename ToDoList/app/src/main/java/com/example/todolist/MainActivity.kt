package com.example.todolist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private  lateinit var todoAdapter: ToDoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        todoAdapter = ToDoAdapter(mutableListOf())

        rvToDoItems.adapter = todoAdapter
        rvToDoItems.layoutManager = LinearLayoutManager(this)

        btnAddToDo.setOnClickListener {
            val toDoTitle = etToDoTitle.text.toString()
            if(toDoTitle.isNotEmpty()){
                val todo = ToDo(toDoTitle)
                todoAdapter.addToDo(todo)
                etToDoTitle.text.clear()
            }
        }

        btnDeleteDoneToDos.setOnClickListener {
            todoAdapter.deleteDoneTodos()
        }
    }
}
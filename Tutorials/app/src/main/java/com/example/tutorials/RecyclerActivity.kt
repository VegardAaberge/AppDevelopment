package com.example.tutorials

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tutorials.Adapters.TodoAdapter
import com.example.tutorials.data.Todo
import kotlinx.android.synthetic.main.activity_recycler_view.*

class RecyclerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view)

        var todosList = mutableListOf(
            Todo("Follow AndroidDevs", false),
            Todo("Learn about recycler views", true),
            Todo("Feed my cat", false),
            Todo("Prank my boss", false),
            Todo("Eat some curry", true),
            Todo("Ask", false),
            Todo("Take a shower", false),
        )

        val adapter = TodoAdapter(todosList)
        rvTodos.adapter = adapter
        rvTodos.layoutManager = LinearLayoutManager(this)

        btnAddTodo.setOnClickListener {
            val title = etTodo.text.toString()
            val todo = Todo(title, false)
            todosList.add((todo))
            adapter.notifyItemInserted(todosList.size - 1)
        }
    }
}
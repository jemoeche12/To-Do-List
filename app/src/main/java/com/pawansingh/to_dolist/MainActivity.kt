package com.pawansingh.to_dolist

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.ItemTouchHelper
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity(), SwipeToActionCallback.SwipeActionListener {

    private lateinit var tvFecha: TextView
    private lateinit var etNuevaTarea: EditText
    private lateinit var btnAgregar: Button
    private lateinit var recyclerViewTareas: RecyclerView
    private lateinit var adapter: MyAdapter
    private var fechaSeleccionada: String = ""
    private val listaTareas = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        tvFecha = findViewById(R.id.tvFecha)
        etNuevaTarea = findViewById(R.id.etNuevaTarea)
        btnAgregar = findViewById(R.id.btnAgregar)
        recyclerViewTareas = findViewById(R.id.recyclerViewTareas)

        fechaSeleccionada = intent.getStringExtra("fechaSeleccionada") ?: "Fecha no seleccionada"

        tvFecha.text = "Tareas para: $fechaSeleccionada"

        adapter = MyAdapter(listaTareas)
        recyclerViewTareas.adapter = adapter
        recyclerViewTareas.layoutManager = LinearLayoutManager(this)

        val callback = SwipeToActionCallback(this)
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(recyclerViewTareas)

        btnAgregar.setOnClickListener {
            agregarTarea()
        }
    }

    private fun agregarTarea() {
        val nuevaTarea = etNuevaTarea.text.toString().trim()

        if (nuevaTarea.isEmpty()) {
            Toast.makeText(this, "@/string/placeHolder", Toast.LENGTH_SHORT).show()
            return
        }

        listaTareas.add(nuevaTarea)
        adapter.notifyItemInserted(listaTareas.size - 1)

        etNuevaTarea.setText("")

        Toast.makeText(this, "@string/tareaAdd", Toast.LENGTH_SHORT).show()
    }

    override fun onSwipeLeft(position: Int) {
        Toast.makeText(this, "Elemento aceptado: " + listaTareas[position], Toast.LENGTH_SHORT).show()

        listaTareas[position] = listaTareas[position] + " ✓"
        adapter.notifyItemChanged(position)
    }

    override fun onSwipeRight(position: Int) {
        val removedItem = listaTareas[position]
        listaTareas.removeAt(position)
        adapter.notifyItemRemoved(position)

        Snackbar.make(recyclerViewTareas, "Tarea eliminada: $removedItem", Snackbar.LENGTH_LONG)
            .setAction("Deshacer") {
                listaTareas.add(position, removedItem)
                adapter.notifyItemInserted(position)
            }
            .show()
    }
}
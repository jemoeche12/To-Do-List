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
import android.util.Pair
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity(), SwipeToActionCallback.SwipeActionListener {

    private lateinit var tvFecha: TextView
    private lateinit var etNuevaTarea: EditText
    private lateinit var btnAgregar: Button
    private lateinit var recyclerViewTareas: RecyclerView
    private lateinit var adapter: MyAdapter

    private lateinit var btnCocina: Button
    private lateinit var btnSala: Button
    private lateinit var btnOtrasTareas: Button
    private lateinit var btnTodas: Button

    private lateinit var categoryButtons: List<Button>

    private var fechaSeleccionada: String = ""
    private val listaTareas = ArrayList<Pair<String, String>>()
    private var listaTareasFiltradas = ArrayList<Pair<String, String>>()
    private var currentCategory: String = "Todas"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        tvFecha = findViewById(R.id.tvFecha)
        etNuevaTarea = findViewById(R.id.etNuevaTarea)
        btnAgregar = findViewById(R.id.btnAgregar)
        recyclerViewTareas = findViewById(R.id.recyclerViewTareas)

        btnCocina = findViewById(R.id.btnCocina)
        btnSala = findViewById(R.id.btnSala)
        btnOtrasTareas = findViewById(R.id.btnOtrasTareas)
        btnTodas = findViewById(R.id.btnTodas)

        categoryButtons = listOf(btnCocina, btnSala, btnOtrasTareas, btnTodas)

        fechaSeleccionada = intent.getStringExtra("fechaSeleccionada") ?: "Fecha no seleccionada"
        tvFecha.text = "Tareas para: $fechaSeleccionada"

        adapter = MyAdapter(listaTareasFiltradas)
        recyclerViewTareas.adapter = adapter
        recyclerViewTareas.layoutManager = LinearLayoutManager(this)

        val callback = SwipeToActionCallback(this)
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(recyclerViewTareas)

        btnAgregar.setOnClickListener {
            agregarTarea(currentCategory)
        }

        btnCocina.setOnClickListener { filterTasks("COOCK"); updateCategoryButtonState("COOCK") }
        btnSala.setOnClickListener { filterTasks("SALA"); updateCategoryButtonState("SALA") }
        btnOtrasTareas.setOnClickListener { filterTasks("OTRAS TAREAS"); updateCategoryButtonState("OTRAS TAREAS") }
        btnTodas.setOnClickListener { filterTasks("Todas"); updateCategoryButtonState("Todas") }

        filterTasks("Todas")
        updateCategoryButtonState("Todas")
    }

    private fun agregarTarea(category: String) {
        val nuevaTareaTexto = etNuevaTarea.text.toString().trim()

        if (nuevaTareaTexto.isEmpty()) {
            Toast.makeText(this, R.string.placeHolder, Toast.LENGTH_SHORT).show()
            return
        }

        listaTareas.add(Pair(nuevaTareaTexto, category))
        etNuevaTarea.setText("")
        Toast.makeText(this, R.string.tareaAdd, Toast.LENGTH_SHORT).show()

        filterTasks(currentCategory)
    }

    private fun filterTasks(category: String) {
        currentCategory = category
        listaTareasFiltradas.clear()

        if (category == "Todas") {
            listaTareasFiltradas.addAll(listaTareas)
        } else {
            listaTareas.forEach { taskPair ->
                if (taskPair.second == category) {
                    listaTareasFiltradas.add(taskPair)
                }
            }
        }
        adapter.notifyDataSetChanged()
    }

    override fun onSwipeLeft(position: Int) {
        val originalItem = listaTareasFiltradas[position]
        val originalIndex = listaTareas.indexOf(originalItem)

        if (originalIndex != -1) {
            Toast.makeText(this, "Elemento aceptado: " + listaTareas[originalIndex].first, Toast.LENGTH_SHORT).show()
            listaTareas[originalIndex] = Pair(listaTareas[originalIndex].first + " ✓", listaTareas[originalIndex].second)
            filterTasks(currentCategory)
        }
    }

    override fun onSwipeRight(position: Int) {
        val removedItem = listaTareasFiltradas[position]
        val originalIndex = listaTareas.indexOf(removedItem)

        if (originalIndex != -1) {
            listaTareas.removeAt(originalIndex)
            filterTasks(currentCategory)

            Snackbar.make(recyclerViewTareas, "Tarea eliminada: ${removedItem.first}", Snackbar.LENGTH_LONG)
                .setAction("Deshacer") {
                    listaTareas.add(originalIndex, removedItem)
                    filterTasks(currentCategory)
                }
                .show()
        }
    }

    private fun updateCategoryButtonState(selectedCategory: String) {
        categoryButtons.forEach { button ->
            button.setBackgroundColor(ContextCompat.getColor(this, R.color.purple_500))
            button.setTextColor(ContextCompat.getColor(this, android.R.color.white))
        }

        val buttonToSelect = when (selectedCategory) {
            "COOCK" -> btnCocina
            "SALA" -> btnSala
            "OTRAS TAREAS" -> btnOtrasTareas
            "Todas" -> btnTodas
            else -> null
        }

        buttonToSelect?.let { button ->
            button.setBackgroundColor(ContextCompat.getColor(this, R.color.purple_200))
            button.setTextColor(ContextCompat.getColor(this, android.R.color.white))
        }
    }

}
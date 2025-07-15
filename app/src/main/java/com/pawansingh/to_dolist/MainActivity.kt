package com.pawansingh.to_dolist

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    // Variables que vamos a usar
    private lateinit var tvFecha: TextView
    private lateinit var etNuevaTarea: EditText
    private lateinit var btnAgregar: Button
    private lateinit var layoutTareas: LinearLayout
    private var fechaSeleccionada: String = ""
    private val listaTareas = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        // Paso 1: Conectar con las vistas del XML
        tvFecha = findViewById(R.id.tvFecha)
        etNuevaTarea = findViewById(R.id.etNuevaTarea)
        btnAgregar = findViewById(R.id.btnAgregar)
        layoutTareas = findViewById(R.id.layoutTareas)

        fechaSeleccionada = intent.getStringExtra("fechaSeleccionada") ?: "Fecha no seleccionada"

        // Paso 3: Mostrar la fecha en pantalla
        tvFecha.text = "Tareas para: $fechaSeleccionada"

        // Paso 4: Configurar el botón para agregar tareas
        btnAgregar.setOnClickListener {
            agregarTarea()
        }
    }

    // Método para agregar una nueva tarea
    private fun agregarTarea() {
        // Obtener el texto que escribió el usuario
        val nuevaTarea = etNuevaTarea.text.toString().trim()

        // Verificar que no esté vacío
        if (nuevaTarea.isEmpty()) {
            Toast.makeText(this, "Escribe una tarea primero", Toast.LENGTH_SHORT).show()
            return
        }

        // Agregar la tarea a nuestra lista
        listaTareas.add(nuevaTarea)

        // Crear un TextView para mostrar la tarea
        val tvTarea = TextView(this)
        tvTarea.text = nuevaTarea
        tvTarea.textSize = 16f
        tvTarea.setPadding(20, 20, 20, 20)
        tvTarea.setBackgroundResource(android.R.drawable.editbox_background)

        // Agregar la tarea al layout
        layoutTareas.addView(tvTarea)

        // Limpiar el campo de texto
        etNuevaTarea.setText("")

        // Mostrar mensaje de confirmación
        Toast.makeText(this, "Tarea agregada", Toast.LENGTH_SHORT).show()
    }
}
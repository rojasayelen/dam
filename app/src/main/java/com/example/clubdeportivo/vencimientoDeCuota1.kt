package com.example.clubdeportivo

import VencimientoAdapter
import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.clubdeportivo.DataBaseHelper
import com.example.clubdeportivo.R
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class VencimientoDeCuota1 : AppCompatActivity() {
    private lateinit var dbHelper: DataBaseHelper
    private lateinit var recyclerView: RecyclerView
    private lateinit var vencimientoAdapter: VencimientoAdapter

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vencimiento_de_cuota1)

        dbHelper = DataBaseHelper(this)
        recyclerView = findViewById(R.id.recyclerViewSocios)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val editTextDesde = findViewById<EditText>(R.id.editTextDesde)
        val editTextHasta = findViewById<EditText>(R.id.editTextHasta)
        val backButton = findViewById<Button>(R.id.backButton)

        backButton.setOnClickListener {
            finish()
        }

        editTextDesde.setOnClickListener {
            showDatePickerDialog(editTextDesde)
        }

        editTextHasta.setOnClickListener {
            showDatePickerDialog(editTextHasta)
        }

        // Agregar un TextWatcher a ambos EditText para actualizar los resultados cuando ambas fechas estén seleccionadas
        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                val fechaDesde = editTextDesde.text.toString()
                val fechaHasta = editTextHasta.text.toString()

                if (fechaDesde.isNotEmpty() && fechaHasta.isNotEmpty()) {
                    // Convierte las fechas a timestamp
                    val fechaDesdeTimestamp = convertirFechaATimestamp(fechaDesde)
                    val fechaHastaTimestamp = convertirFechaATimestamp(fechaHasta)

                    // Ejecutar la consulta y actualizar el RecyclerView
                    val vencimientos = dbHelper.obtenerVencimientosEntreFechas(fechaDesdeTimestamp, fechaHastaTimestamp)
                    vencimientoAdapter = VencimientoAdapter(vencimientos)
                    recyclerView.adapter = vencimientoAdapter
                }
            }
        }

        editTextDesde.addTextChangedListener(textWatcher)
        editTextHasta.addTextChangedListener(textWatcher)
    }

    private fun showDatePickerDialog(editText: EditText) {
        val calendar = Calendar.getInstance()
        val datePickerDialog = DatePickerDialog(
            this,
            { _, year, month, dayOfMonth ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(year, month, dayOfMonth)

                // Formato de la fecha
                val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                editText.setText(dateFormat.format(selectedDate.time))
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )

        datePickerDialog.show()
    }

    private fun convertirFechaATimestamp(fecha: String): Long {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return try {
            dateFormat.parse(fecha)?.time ?: 0L
        } catch (e: Exception) {
            e.printStackTrace()
            0L
        }
    }

}

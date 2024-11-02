package com.example.clubdeportivo

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.clubdeportivo.R
import java.text.SimpleDateFormat
import java.util.*

class vencimientosCuota : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_vencimientos_cuota)

        val editTextDesde = findViewById<EditText>(R.id.editTextDesde)
        val editTextHasta = findViewById<EditText>(R.id.editTextHasta)

        editTextDesde.setOnClickListener {
            showDatePickerDialog(editTextDesde)
        }

        editTextHasta.setOnClickListener {
            showDatePickerDialog(editTextHasta)
        }
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
}

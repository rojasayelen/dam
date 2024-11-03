package com.example.clubdeportivo

import Socio
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class IngresarPagoActivity : AppCompatActivity() {

    private lateinit var socioAdapter: SocioAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchNameEditText: EditText
    private lateinit var payButton: Button
    private lateinit var backButton: Button
    private lateinit var dbHelper: DataBaseHelper
    private var socios: List<Socio> = listOf()
    private var socioSeleccionado: Socio? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ingresar_pago)

        dbHelper = DataBaseHelper(this)
        recyclerView = findViewById(R.id.recyclerViewSocios)
        searchNameEditText = findViewById(R.id.searchName)
        payButton = findViewById(R.id.payButton)
        backButton = findViewById(R.id.backButton) // Obtener referencia al botón de "Volver"

        // Configurar RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        socios = dbHelper.obtenerTodosLosSocios()

        // Configurar el adaptador con el listener
        socioAdapter = SocioAdapter(socios) { socio ->
            socioSeleccionado = socio
            payButton.isEnabled = true // Habilitar el botón cuando se selecciona un socio
            payButton.text = "Pagar - ${socio.nombre} ${socio.apellido}"
            Log.d("IngresarPagoActivity", "Socio seleccionado: ${socio.nombre} ${socio.apellido}")
        }
        recyclerView.adapter = socioAdapter

        searchNameEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                filtrarSocios(s.toString())
            }
        })

        payButton.isEnabled = false // Desactivar al inicio

        payButton.setOnClickListener {
            socioSeleccionado?.let { socio ->
                registrarCuotaYPago(socio)
            } ?: run {
                Toast.makeText(this, "Por favor, selecciona un socio primero", Toast.LENGTH_SHORT).show()
            }
        }

        // Configurar el listener del botón "Volver"
        backButton.setOnClickListener {
            finish() // Finalizar la actividad actual y regresar a la pantalla anterior
        }
    }

    private fun filtrarSocios(query: String) {
        val sociosFiltrados = socios.filter { socio ->
            socio.nombre.contains(query, ignoreCase = true) || socio.apellido.contains(query, ignoreCase = true)
        }
        socioAdapter.actualizarLista(sociosFiltrados)
    }

    private fun registrarCuotaYPago(socio: Socio) {
        val fechaPago = Date()
        val diasVencimiento = if (socio.tipo == "Socio") 30 else 1
        val fechaVencimiento = Calendar.getInstance().apply {
            time = fechaPago
            add(Calendar.DAY_OF_YEAR, diasVencimiento)
        }.time

        val numeroCuota = findViewById<EditText>(R.id.cuotaNumber).text.toString().toIntOrNull() ?: 1

        if (dbHelper.cuotaPagada(socio.idPersona, numeroCuota)) {
            Toast.makeText(this, "Esta cuota ya ha sido pagada.", Toast.LENGTH_LONG).show()
        } else {
            val idCuota = dbHelper.registrarCuota(socio.idPersona, fechaVencimiento, numeroCuota)
            dbHelper.registrarPago(idCuota, fechaPago)

            Toast.makeText(this, "Pago registrado exitosamente para ${socio.nombre}.", Toast.LENGTH_SHORT).show()

            val intent = Intent(this, PagoExitosoActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}

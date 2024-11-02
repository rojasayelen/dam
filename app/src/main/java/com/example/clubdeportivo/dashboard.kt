package com.example.clubdeportivo

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class dashboard : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        // Encuentra el botón por su ID y configura el click listener
        val btnNuevoSocio = findViewById<Button>(R.id.btn_nuevo_socio)
        btnNuevoSocio.setOnClickListener {
            val intent = Intent(this, NuevoSocio::class.java)
            startActivity(intent)
        }

        val btnvencimientosCuota = findViewById<Button>(R.id.btn_consultar_vencimiento)
        btnvencimientosCuota.setOnClickListener {
            val intent = Intent(this, vencimientosCuota::class.java)
            startActivity(intent)
        }

        val btnIngresarpago = findViewById<Button>(R.id.btn_nuevo_pago)
        btnIngresarpago.setOnClickListener {
            val intent = Intent(this, IngresarPagoActivity::class.java)
            startActivity(intent)
        }
    }
}

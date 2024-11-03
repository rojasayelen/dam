package com.example.clubdeportivo

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class PagoExitosoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pago_exitoso)

        findViewById<Button>(R.id.registrarNuevoPagoButton).setOnClickListener {
            val intent = Intent(this, IngresarPagoActivity::class.java)
            startActivity(intent)
            finish()
        }

        findViewById<TextView>(R.id.cargarNuevoSocioText).setOnClickListener {
            val intent = Intent(this, NuevoSocio::class.java)
            startActivity(intent)
            finish()
        }
    }
}

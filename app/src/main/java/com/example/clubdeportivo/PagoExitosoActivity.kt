package com.example.clubdeportivo

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

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

        // Configuración del BottomNavigationView
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_logout -> {
                    // Navegar a la actividad de inicio de sesión
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()  // Cierra la actividad actual
                    true
                }
                R.id.action_home -> {
                    // Navegar al dashboard
                    val intent = Intent(this, dashboard::class.java)
                    startActivity(intent)
                    finish()
                    true
                }
                R.id.action_vencimientos -> {
                    // Navegar a la actividad de Vencimientos
                    val intent = Intent(this, VencimientoDeCuota1::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
    }
}

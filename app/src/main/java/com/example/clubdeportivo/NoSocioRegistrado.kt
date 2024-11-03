package com.example.clubdeportivo

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class NoSocioRegistrado : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.alta_no_socio) // Asegúrate de que el layout se llame así

        // Configuración de los botones
        val buttonRegistrarPago = findViewById<Button>(R.id.button_registrar_pago)
        val buttonCargarNuevoSocio = findViewById<Button>(R.id.button_cargar_nuevo_socio)

        // Navegar a la actividad de registro de pago al hacer clic en "Registrar pago"
        buttonRegistrarPago.setOnClickListener {
            val intent = Intent(this, IngresarPagoActivity::class.java) // Reemplaza con la actividad que maneja pagos
            startActivity(intent)
        }

        // Navegar a la actividad de registro de un nuevo socio al hacer clic en "Cargar un nuevo socio"
        buttonCargarNuevoSocio.setOnClickListener {
            val intent = Intent(this, NuevoSocio::class.java) // Reemplaza con la actividad de registro de socios
            startActivity(intent)
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

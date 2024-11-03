package com.example.clubdeportivo

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class SocioExistente : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_socio_existente)

        // Botón de regreso
        val buttonBackToForm = findViewById<Button>(R.id.buttonBackToForm)
        buttonBackToForm.setOnClickListener {
            finish()
        }

        // Configuración del BottomNavigationView
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_logout -> {
                    // Navega a la actividad de inicio de sesión
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()  // Cierra la actividad actual para no volver al presionar atrás
                    true
                }
                R.id.action_home -> {
                    // Navega al dashboard
                    val intent = Intent(this, dashboard::class.java)
                    startActivity(intent)
                    finish()
                    true
                }
                R.id.action_vencimientos -> {
                    // Navega a la actividad de Vencimientos
                    val intent = Intent(this, VencimientoDeCuota1::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
    }
}

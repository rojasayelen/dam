package com.example.clubdeportivo

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class SocioRegistradoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.alta_socio)

        // Obtener el nombre del socio registrado del intent
        val socioNombre = intent.getStringExtra("NOMBRE_SOCIO")

        // Mostrar el nombre del socio en un TextView (asegúrate de tener este TextView en tu layout)
        val socioNombreTextView = findViewById<TextView>(R.id.socioNombreTextView)
        socioNombreTextView.text = socioNombre ?: "Nombre del Socio"

        // Configurar los botones
        val registerPaymentButton = findViewById<Button>(R.id.register_payment_button)
        val printCardButton = findViewById<Button>(R.id.print_card_button)
        val newMemberButton = findViewById<Button>(R.id.new_member_button)

        // Acción para el botón "Registrar Pago"
        registerPaymentButton.setOnClickListener {
            // Crear el intent para IngresarPagoActivity y pasar el nombre del socio
            val intent = Intent(this, IngresarPagoActivity::class.java)
            intent.putExtra("NOMBRE_SOCIO", socioNombre)
            startActivity(intent)
        }

        // Acción para el botón "Imprimir Carnet"
        printCardButton.setOnClickListener {
            // Agrega aquí la acción para imprimir carnet, si tienes una actividad o funcionalidad para esto
            Toast.makeText(this, "Funcionalidad de impresión pendiente", Toast.LENGTH_SHORT).show()
        }

        // Acción para el botón "Cargar un nuevo socio"
        newMemberButton.setOnClickListener {
            // Regresa a la actividad de registro de un nuevo socio
            startActivity(Intent(this, NuevoSocio::class.java))
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

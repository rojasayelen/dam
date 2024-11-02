package com.example.clubdeportivo

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class SocioRegistradoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.alta_socio) // Asegúrate de que el nombre del layout sea correcto

        // Configurar los botones
        val registerPaymentButton = findViewById<Button>(R.id.register_payment_button)
        val printCardButton = findViewById<Button>(R.id.print_card_button)
        val newMemberButton = findViewById<Button>(R.id.new_member_button)

        // Acción para el botón "Registrar Pago"
        registerPaymentButton.setOnClickListener {
            // Navegar a la actividad de registro de pago (debes crear esta actividad si aún no existe)
            startActivity(Intent(this, IngresarPagoActivity::class.java))
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
    }
}

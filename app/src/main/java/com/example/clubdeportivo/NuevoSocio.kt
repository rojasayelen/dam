package com.example.clubdeportivo

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class NuevoSocio : AppCompatActivity() {

    private lateinit var dbHelper: DataBaseHelper

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.nuevo_socio_1) // Asegúrate de que el nombre del archivo XML esté correcto

        dbHelper = DataBaseHelper(this)

        // Referencias a los campos de entrada
        val dniEditText = findViewById<EditText>(R.id.dniEditText)
        val nameEditText = findViewById<EditText>(R.id.nameEditText)
        val lastnameEditText = findViewById<EditText>(R.id.lastnameEditText)
        val phoneEditText = findViewById<EditText>(R.id.phoneEditText)
        val addressEditText = findViewById<EditText>(R.id.addressEditText)
        val emailEditText = findViewById<EditText>(R.id.emailEditText)
        val aptPhysicalSwitch = findViewById<Switch>(R.id.aptPhysicalSwitch)

        // Verificación automática del DNI
        dniEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                val dni = s.toString().toIntOrNull()
                if (dni != null && dbHelper.socioExiste(dni)) {
                    // Redirigir a la pantalla de socio existente
                    val intent = Intent(this@NuevoSocio, SocioExistente::class.java)
                    startActivity(intent)
                }
            }
        })

        // Botón "Dar de alta"
        val nextButton = findViewById<Button>(R.id.nextButton)
        nextButton.setOnClickListener {
            // Capturar los datos ingresados
            val dni = dniEditText.text.toString().toIntOrNull()
            val nombre = nameEditText.text.toString().trim()
            val apellido = lastnameEditText.text.toString().trim()
            val telefono = phoneEditText.text.toString().trim() // Cambiado a String
            val direccion = addressEditText.text.toString().trim()
            val email = emailEditText.text.toString().trim()
            val aptoFisico = aptPhysicalSwitch.isChecked

            // Validar los datos obligatorios y mostrar mensajes específicos
            when {
                dni == null -> {
                    Toast.makeText(this, "Por favor, ingresa un DNI válido", Toast.LENGTH_SHORT).show()
                }
                nombre.isBlank() -> {
                    Toast.makeText(this, "Por favor, ingresa el nombre", Toast.LENGTH_SHORT).show()
                }
                apellido.isBlank() -> {
                    Toast.makeText(this, "Por favor, ingresa el apellido", Toast.LENGTH_SHORT).show()
                }
                telefono.isBlank() -> {
                    Toast.makeText(this, "Por favor, ingresa un número de teléfono válido", Toast.LENGTH_SHORT).show()
                }
                direccion.isBlank() -> {
                    Toast.makeText(this, "Por favor, ingresa la dirección", Toast.LENGTH_SHORT).show()
                }
                email.isBlank() -> {
                    Toast.makeText(this, "Por favor, ingresa un email válido", Toast.LENGTH_SHORT).show()
                }
                !aptoFisico -> {
                    Toast.makeText(this, "Debes marcar el campo 'Apto Físico' para registrar el socio", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    // Insertar el nuevo socio en la base de datos
                    val idSocio = dbHelper.insertarNuevoSocio(dni, nombre, apellido, direccion, telefono, email, aptoFisico, "2024-05-19")

                    if (idSocio > 0) {
                        // Navegar a la pantalla de confirmación
                        val intent = Intent(this, SocioRegistradoActivity::class.java)
                        startActivity(intent)
                        finish() // Cierra la actividad actual
                    } else {
                        Toast.makeText(this, "Error al registrar el socio", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        // Botón "Volver"
        val backButton = findViewById<Button>(R.id.backButton)
        backButton.setOnClickListener {
            finish()
        }
    }
}

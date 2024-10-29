package com.example.clubdeportivo

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.clubdeportivo.com.example.clubdeportivo.DataBaseHelper


class MainActivity : AppCompatActivity() {
    private lateinit var dbHelper: DataBaseHelper




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializar dbHelper
        dbHelper = DataBaseHelper(this)

        // Crea la base de datos e inserta el administrador al iniciar la aplicación
        val db = dbHelper.writableDatabase
        insertarAdministradorSiNoExiste(db)



// Referencias a los campos de texto y botón de inicio de sesión
        val editTextUsername = findViewById<EditText>(R.id.editTextUsername)
        val editTextPassword = findViewById<EditText>(R.id.editTextPassword)
        val buttonLogin = findViewById<Button>(R.id.buttonLogin)

        // Configuración del botón de inicio de sesión
        buttonLogin.setOnClickListener {
            val username = editTextUsername.text.toString()
            val password = editTextPassword.text.toString()

            // Verificar credenciales
            val usuarioValido = dbHelper.verificarUsuario(username, password)
            if (usuarioValido) {
                // Inicio de sesión exitoso, redirige a la pantalla principal (DashboardActivity)
                val intent = Intent(this, dashboard::class.java)
                startActivity(intent)
                finish()
            } else {
                // Mostrar mensaje de error
                Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show()
            }
        }


    }
    private fun insertarAdministradorSiNoExiste(db: SQLiteDatabase) {
        // Verificar si el administrador ya existe
        val cursor = db.rawQuery("SELECT * FROM Persona WHERE Nombre = 'ADMINISTRADOR'", null)
        if (cursor.count == 0) {
            // Si no existe, lo insertamos
            dbHelper.insertarAdministrador(db)
        }
        cursor.close()

        val credenciales = db.rawQuery("SELECT * FROM Empleado WHERE Usuario = 'ADMIN'", null)
        if (credenciales.count == 0) {
                // Si no existe, lo insertamos
                dbHelper.insertarCredenciales(db)
            }
        credenciales.close()
    }


    override fun onDestroy() {
        dbHelper.close()
        super.onDestroy()
    }

}

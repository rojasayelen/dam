package com.example.clubdeportivo

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import android.graphics.Rect
import android.view.ViewTreeObserver
import android.widget.ScrollView

class MainActivity : AppCompatActivity() {

    private lateinit var dbHelper: DataBaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //this.deleteDatabase("ClubDeportivo")
        val scrollView = findViewById<ScrollView>(R.id.scrollView)
        // Inicializar dbHelper
        dbHelper = DataBaseHelper(this)

        // Crea la base de datos e inserta el administrador al iniciar la aplicación si no existe
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
                // Redirigir a la pantalla de error de inicio de sesión
                val intent = Intent(this, LoginError::class.java)
                startActivity(intent)
            }
        }
        scrollView.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                val r = Rect()
                scrollView.getWindowVisibleDisplayFrame(r)
                val screenHeight = scrollView.rootView.height
                val keypadHeight = screenHeight - r.bottom

                // Si el teclado está visible (ocupa más del 15% de la pantalla)
                if (keypadHeight > screenHeight * 0.15) {
                    scrollView.scrollTo(0, scrollView.bottom)
                } else {
                    scrollView.scrollTo(0, 0)
                }
            }
        })
    }

    private fun insertarAdministradorSiNoExiste(db: SQLiteDatabase) {
        // Verificar si el administrador ya existe en Persona
        val cursorPersona = db.rawQuery("SELECT * FROM Persona WHERE Nombre = 'ADMINISTRADOR'", null)
        if (cursorPersona.count == 0) {
            // Si no existe, insertar en Persona
            dbHelper.insertarAdministrador(db)
        }
        cursorPersona.close()

        // Verificar si las credenciales de administrador ya existen en Empleado
        val cursorEmpleado = db.rawQuery("SELECT * FROM Empleado WHERE Usuario = 'Admin'", null)
        if (cursorEmpleado.count == 0) {
            // Si no existe, insertar en Empleado
            dbHelper.insertarCredenciales(db)
        }
        cursorEmpleado.close()
    }

    override fun onDestroy() {
        dbHelper.close()
        super.onDestroy()
    }
}

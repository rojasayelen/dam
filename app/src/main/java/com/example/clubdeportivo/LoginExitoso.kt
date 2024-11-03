package com.example.clubdeportivo

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button

class LoginExitoso : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_exitosa)

        // Referencia al botón de continuar
        val btnConnect = findViewById<Button>(R.id.btn_connect)
        btnConnect.setOnClickListener {
            // Redirigir a la pantalla principal (Dashboard)
            val intent = Intent(this, dashboard::class.java)
            startActivity(intent)
            finish()
        }
    }
}

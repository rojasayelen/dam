package com.example.clubdeportivo

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class SocioExistente : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_socio_existente)

        val buttonBackToForm = findViewById<Button>(R.id.buttonBackToForm)
        buttonBackToForm.setOnClickListener {
            finish()
        }
    }
}

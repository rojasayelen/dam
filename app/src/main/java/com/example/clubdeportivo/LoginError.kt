// LoginError.kt
package com.example.clubdeportivo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button

class LoginError : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_error)

        val btnRetry = findViewById<Button>(R.id.btn_connect)
        btnRetry.setOnClickListener {
            finish()
        }
    }
}

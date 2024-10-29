package com.example.clubdeportivo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.clubdeportivo.com.example.clubdeportivo.DataBaseHelper


class nuevoEmpleado1 : AppCompatActivity() {
    private lateinit var dbHelper: DataBaseHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_nuevo_empleado1)

        dbHelper = DataBaseHelper(this)

        /*val empleadoName = findViewById<EditText>(R.id.editTextName)
        val empleadoSurname = findViewById<EditText>(R.id.editTextLastName)
        val empleadoDNI = findViewById<EditText>(R.id.editTextDNI)
        val empleadoTel = findViewById<EditText>(R.id.editTextPhoneNumber)
        val buttonNext = findViewById<Button>(R.id.buttonNext)

        buttonNext.setOnClickListener{
            val name = empleadoName.text.toString()
            val surname = empleadoSurname.text.toString()
            val DNI = empleadoDNI.text.toString()
            val Tel = empleadoTel.text.toString()

            if( name.isNotEmpty() &&
                surname.isNotEmpty() &&
                DNI.isNotEmpty() &&
                Tel.isNotEmpty()
                ){

            }
        }*/

    }


}


package com.example.clubdeportivo
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    private lateinit var dbHelper: DataBaseHelper
    private lateinit var writetabledb: SQLiteDatabase
    private lateinit var readtabledb: SQLiteDatabase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbHelper = DataBaseHelper(this)
        //para escribir la tabla e ingresarlo en la funcion de guardar
        writetabledb = dbHelper.writableDatabase
        //para leer la tabla e ingresarlo en la funcion de consulta
        readtabledb = dbHelper.readableDatabase

    }


    override fun onDestroy() {
        dbHelper.close()
        super.onDestroy()
    }
}

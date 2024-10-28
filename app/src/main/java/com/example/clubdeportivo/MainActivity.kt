package com.example.clubdeportivo
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    private lateinit var dbHelper: DataBaseHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbHelper = DataBaseHelper(this)
        val db = dbHelper.writableDatabase
        val db2 = dbHelper.readableDatabase //Viendo como resolver esto...
    }

    override fun onDestroy() {
        dbHelper.close()
        super.onDestroy()
    }
}

package com.example.clubdeportivo

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.bottomnavigation.BottomNavigationView

class dashboard : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        // Inicializar DrawerLayout
        drawerLayout = findViewById(R.id.drawer_layout)

        // Configurar el ícono de menú de hamburguesa para abrir el menú lateral solo al hacer clic
        val menuIcon = findViewById<ImageView>(R.id.menu_icon)
        menuIcon.setOnClickListener {
            // Verificar si el drawer está abierto o cerrado antes de abrirlo
            if (!drawerLayout.isDrawerOpen(findViewById(R.id.drawer_menu))) {
                drawerLayout.openDrawer(findViewById(R.id.drawer_menu))
            }
        }

        // Configurar el botón de cerrar en el menú lateral
        val closeButton = findViewById<ImageView>(R.id.close_button)
        closeButton.setOnClickListener {
            drawerLayout.closeDrawer(findViewById(R.id.drawer_menu))
        }

        // Configuración de botones de la actividad
        val btnNuevoSocio = findViewById<Button>(R.id.btn_nuevo_socio)
        btnNuevoSocio.setOnClickListener {
            val intent = Intent(this, NuevoSocio::class.java)
            startActivity(intent)
        }

        val btnConsultarVencimiento = findViewById<Button>(R.id.btn_consultar_vencimiento)
        btnConsultarVencimiento.setOnClickListener {
            val intent = Intent(this, VencimientoDeCuota1::class.java)
            startActivity(intent)
        }

        val btnNuevoPago = findViewById<Button>(R.id.btn_nuevo_pago)
        btnNuevoPago.setOnClickListener {
            val intent = Intent(this, IngresarPagoActivity::class.java)
            startActivity(intent)
        }

        val btnActividades = findViewById<Button>(R.id.btn_actividades)
        btnActividades.setOnClickListener {
            val intent = Intent(this, ActividadesActivity::class.java)
            startActivity(intent)
        }

        val btnImprimirCarnet = findViewById<Button>(R.id.btn_imprimir_carnet)
        btnImprimirCarnet.setOnClickListener {
            val intent = Intent(this, carnetSocio::class.java)
            startActivity(intent)
        }

        findViewById<TextView>(R.id.option_new_member).setOnClickListener {
            startActivity(Intent(this, NuevoSocio::class.java))
        }

        findViewById<TextView>(R.id.option_no_member).setOnClickListener {
            startActivity(Intent(this, NuevoSocio::class.java))
        }

        findViewById<TextView>(R.id.VencimientoCliente).setOnClickListener {
            startActivity(Intent(this, VencimientoDeCuota1::class.java))
        }

        findViewById<TextView>(R.id.option_ingresar_pago).setOnClickListener {
            startActivity(Intent(this, IngresarPagoActivity::class.java))
        }

        findViewById<TextView>(R.id.option_carnet_socio).setOnClickListener {
            startActivity(Intent(this, carnetSocio::class.java))
        }

        // Configurar el BottomNavigationView
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_logout -> {
                    // Navega a la actividad de inicio de sesión
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()  // Cierra la actividad actual para no volver al presionar atrás
                    true
                }

                R.id.action_vencimientos -> {
                    // Navega a la actividad de Vencimientos
                    val intent = Intent(this, VencimientoDeCuota1::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
    }

    override fun onBackPressed() {
        // Cierra el menú lateral si está abierto al presionar el botón de retroceso
        if (drawerLayout.isDrawerOpen(findViewById(R.id.drawer_menu))) {
            drawerLayout.closeDrawer(findViewById(R.id.drawer_menu))
        } else {
            super.onBackPressed()
        }
    }
}

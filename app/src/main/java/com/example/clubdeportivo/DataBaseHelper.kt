package com.example.clubdeportivo

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DataBaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "ClubDeportivo"
        private const val DATABASE_VERSION = 1

        // Tables
        private const val TABLE_PERSONA = "Persona"
        private const val TABLE_EMPLEADO = "Empleado"
        private const val TABLE_SOCIO = "Socio"
        private const val TABLE_NO_SOCIO = "NoSocio"
        private const val TABLE_CARNET = "Carnet"
        private const val TABLE_ACTIVIDAD = "Actividad"
        private const val TABLE_TIPO_PAGO = "TipoPago"
        private const val TABLE_CUOTA = "Cuota"
        private const val TABLE_PAGO = "Pago"
        private const val TABLE_SOCIO_ACTIVIDAD = "SocioActividad"

        // Persona columns
        private const val COL_ID_PERSONA = "idPersona"
        private const val COL_DNI = "Dni"
        private const val COL_NOMBRE = "Nombre"
        private const val COL_APELLIDO = "Apellido"
        private const val COL_DIRECCION = "Direccion"
        private const val COL_TELEFONO = "Telefono"
        private const val COL_EMAIL = "Email"
        private const val COL_APTO_FISICO = "AptoFisico"
        private const val COL_FECHA_ALTA = "FechaAlta"
        private const val COL_FECHA_BAJA = "FechaBaja"
        private const val COL_BAJA_PERSONA = "Baja"

        // Empleado columns
        private const val COL_ID_EMPLEADO = "idEmpleado"
        private const val COL_FK_PERSONA = "FkPersona"
        private const val COL_USUARIO = "Usuario"
        private const val COL_PASS = "Contrasena"
        private const val COL_PUESTO = "Puesto"

        // Socio columns
        private const val COL_NUMERO_SOCIO = "NumeroSocio"

        // NoSocio columns
        private const val COL_NUMERO_NO_SOCIO = "NumeroNoSocio"

        // Carnet columns
        private const val COL_ID_CARNET = "idCarnet"
        private const val COL_VTO = "Vencimiento"

        // Actividad columns
        private const val COL_ID_ACTIVIDAD = "idActividad"
        private const val COL_NOMBRE_ACTIVIDAD = "NombreActividad"
        private const val COL_PRECIO_ACTIVIDAD = "PrecioActividad"
        private const val COL_BAJA_ACTIVIDAD = "Baja"

        // TipoPago columns
        private const val COL_ID_TIPO_PAGO = "idTipoPago"
        private const val COL_TIPO_PAGO = "TipoPago"
        private const val COL_MONTO_PAGO = "MontoPago"
        private const val COL_BAJA_TIPO_PAGO = "Baja"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTablePersona = """
            CREATE TABLE IF NOT EXISTS $TABLE_PERSONA(
                $COL_ID_PERSONA INTEGER PRIMARY KEY AUTOINCREMENT,
                $COL_DNI INTEGER NOT NULL,
                $COL_NOMBRE TEXT NOT NULL,
                $COL_APELLIDO TEXT NOT NULL,
                $COL_DIRECCION TEXT NOT NULL,
                $COL_TELEFONO TEXT NOT NULL,  -- Cambiado a TEXT
                $COL_EMAIL TEXT NOT NULL,
                $COL_APTO_FISICO BOOLEAN NOT NULL,
                $COL_FECHA_ALTA TEXT NOT NULL,
                $COL_FECHA_BAJA TEXT,
                $COL_BAJA_PERSONA BOOLEAN NOT NULL
            )
        """.trimIndent()

        val createTableEmpleado = """
            CREATE TABLE IF NOT EXISTS $TABLE_EMPLEADO(
                $COL_ID_EMPLEADO INTEGER PRIMARY KEY AUTOINCREMENT,
                $COL_FK_PERSONA INTEGER NOT NULL,
                $COL_USUARIO TEXT NOT NULL,
                $COL_PASS TEXT NOT NULL,
                $COL_PUESTO TEXT,
                FOREIGN KEY ($COL_FK_PERSONA) REFERENCES $TABLE_PERSONA ($COL_ID_PERSONA)
                    ON DELETE CASCADE
                    ON UPDATE CASCADE
            )
        """.trimIndent()


                val createTableSocio = """
            CREATE TABLE IF NOT EXISTS $TABLE_SOCIO(
                $COL_NUMERO_SOCIO INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                $COL_ID_PERSONA INTEGER NOT NULL,
                FOREIGN KEY ($COL_ID_PERSONA) REFERENCES $TABLE_PERSONA ($COL_ID_PERSONA)
                    ON DELETE CASCADE
                    ON UPDATE CASCADE
            )
        """.trimIndent()

                val createTableNoSocio = """
            CREATE TABLE IF NOT EXISTS $TABLE_NO_SOCIO(
                $COL_NUMERO_NO_SOCIO INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                $COL_ID_PERSONA INTEGER NOT NULL,
                FOREIGN KEY ($COL_ID_PERSONA) REFERENCES $TABLE_PERSONA ($COL_ID_PERSONA)
                    ON DELETE CASCADE
                    ON UPDATE CASCADE
            )
        """.trimIndent()

        val createTableCarnet = """
            CREATE TABLE IF NOT EXISTS $TABLE_CARNET(
                $COL_ID_CARNET INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                $COL_VTO TEXT NOT NULL,
                $COL_NUMERO_SOCIO INTEGER NOT NULL,
                FOREIGN KEY ($COL_NUMERO_SOCIO) REFERENCES $TABLE_SOCIO ($COL_NUMERO_SOCIO)
                    ON DELETE CASCADE
                    ON UPDATE CASCADE
            )
        """.trimIndent()

            val createTableActividades = """
            CREATE TABLE IF NOT EXISTS $TABLE_ACTIVIDAD(
                $COL_ID_ACTIVIDAD INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                $COL_NOMBRE_ACTIVIDAD TEXT NOT NULL,
                $COL_PRECIO_ACTIVIDAD INTEGER NOT NULL,
                $COL_BAJA_ACTIVIDAD BOOLEAN NOT NULL
            )    
            """.trimIndent()

            val createTableTipoPago = """
            CREATE TABLE IF NOT EXISTS $TABLE_TIPO_PAGO(
                $COL_ID_TIPO_PAGO INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                $COL_TIPO_PAGO TEXT NOT NULL,
                $COL_MONTO_PAGO INTEGER NOT NULL,
                $COL_BAJA_TIPO_PAGO BOOLEAN
            )
            """.trimIndent()


            //Creacion de las tablas
            db.execSQL(createTablePersona)
            db.execSQL(createTableEmpleado)
            db.execSQL(createTableSocio)
            db.execSQL(createTableNoSocio)
            db.execSQL(createTableCarnet)
            db.execSQL(createTableActividades)
            db.execSQL(createTableCarnet)
            db.execSQL(createTableTipoPago)
        }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_PERSONA")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_EMPLEADO")
        // Eliminar otras tablas según sea necesario
        onCreate(db!!)
    }

    fun verificarUsuario(username: String, password: String): Boolean {
        val db = this.readableDatabase
        val query = "SELECT * FROM $TABLE_EMPLEADO WHERE $COL_USUARIO = ? AND $COL_PASS = ?"
        val cursor = db.rawQuery(query, arrayOf(username, password))

        val usuarioExiste = cursor.count > 0
        cursor.close()
        db.close()

        return usuarioExiste
    }

    fun insertarAdministrador(db: SQLiteDatabase) {
        val valuesPersona = ContentValues().apply {
            put(COL_DNI, 1)
            put(COL_NOMBRE, "ADMINISTRADOR")
            put(COL_APELLIDO, "CENTRO DEPORTIVO")
            put(COL_DIRECCION, "GRUPO 1 E")
            put(COL_TELEFONO, 8001112222)
            put(COL_EMAIL, "centrodeportivoe1@centrodeportivo.com")
            put(COL_APTO_FISICO, 0)
            put(COL_FECHA_ALTA, "2024-05-19")  // Formato de fecha legible
            put(COL_BAJA_PERSONA, 0)
        }
        db.insert(TABLE_PERSONA, null, valuesPersona)
    }

    fun insertarCredenciales(db: SQLiteDatabase) {
        val valuesEmpleado = ContentValues().apply {
            put(COL_FK_PERSONA, 1) // Asume que la Persona con ID 1 es el administrador
            put(COL_USUARIO, "Admin")
            put(COL_PASS, "admin") // SHA-256 hash de "admin"
            put(COL_PUESTO, "ADMINISTRACION")
        }
        db.insert(TABLE_EMPLEADO, null, valuesEmpleado)
    }

    fun socioExiste(dni: Int): Boolean {
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_PERSONA WHERE $COL_DNI = ?"
        val cursor = db.rawQuery(query, arrayOf(dni.toString()))

        val existe = cursor.count > 0
        cursor.close()
        db.close()

        return existe
    }

    fun insertarNuevoSocio(dni: Int, nombre: String, apellido: String, direccion: String, telefono: String, email: String, aptoFisico: Boolean, fechaAlta: String): Long {

        val db = writableDatabase
        val valuesPersona = ContentValues().apply {
            put(COL_DNI, dni)
            put(COL_NOMBRE, nombre)
            put(COL_APELLIDO, apellido)
            put(COL_DIRECCION, direccion)
            put(COL_TELEFONO, telefono)
            put(COL_EMAIL, email)
            put(COL_APTO_FISICO, if (aptoFisico) 1 else 0)
            put(COL_FECHA_ALTA, fechaAlta)
            put(COL_BAJA_PERSONA, 0)
        }
        val idPersona = db.insert(TABLE_PERSONA, null, valuesPersona)

        return if (idPersona != -1L) {
            val valuesSocio = ContentValues().apply {
                put(COL_ID_PERSONA, idPersona)
            }
            db.insert(TABLE_SOCIO, null, valuesSocio)
        } else {
            -1
        }
    }


}



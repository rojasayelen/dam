package com.example.clubdeportivo

import Socio
import Vencimiento
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

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
        private const val COL_TELEFONO = "Telefono" // Cambiado a TEXT
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

        // Cuota columns
        private const val COL_ID_CUOTA = "IdCuota"
        private const val COL_NRO_CUOTA="NumeroCuota"
        private const val COL_FK_PERSONA_CUOTA = "fkPersona"
        private const val COL_FECHA_VENCIMIENTO = "FechaVencimiento"

        // Pago columns
        private const val COL_ID_PAGO = "IdPago"
        private const val COL_FK_CUOTA = "fkCuota"
        private const val COL_FECHA_PAGO = "FechaPago"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTablePersona = """
            CREATE TABLE IF NOT EXISTS $TABLE_PERSONA(
                $COL_ID_PERSONA INTEGER PRIMARY KEY AUTOINCREMENT,
                $COL_DNI INTEGER NOT NULL,
                $COL_NOMBRE TEXT NOT NULL,
                $COL_APELLIDO TEXT NOT NULL,
                $COL_DIRECCION TEXT NOT NULL,
                $COL_TELEFONO TEXT NOT NULL,  
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

        val createTableCuota = """
            CREATE TABLE IF NOT EXISTS $TABLE_CUOTA(
                $COL_ID_CUOTA INTEGER PRIMARY KEY AUTOINCREMENT,
                $COL_NRO_CUOTA INTEGER,
                $COL_FK_PERSONA INTEGER NOT NULL,
                $COL_FECHA_VENCIMIENTO DATETIME,
                FOREIGN KEY ($COL_FK_PERSONA_CUOTA) REFERENCES $TABLE_PERSONA($COL_ID_PERSONA)
            )
        """.trimIndent()

        val createTablePago = """
            CREATE TABLE IF NOT EXISTS $TABLE_PAGO(
                $COL_ID_PAGO INTEGER PRIMARY KEY AUTOINCREMENT,
                $COL_FK_CUOTA INTEGER NOT NULL,                
                $COL_FECHA_PAGO DATETIME,
                FOREIGN KEY ($COL_FK_CUOTA) REFERENCES $TABLE_CUOTA($COL_ID_CUOTA)
              
            )
        """.trimIndent()

        // Creación de las tablas
        db.execSQL(createTablePersona)
        db.execSQL(createTableEmpleado)
        db.execSQL(createTableSocio)
        db.execSQL(createTableNoSocio)
        db.execSQL(createTableCarnet)
        db.execSQL(createTableActividades)
        db.execSQL(createTableTipoPago)
        db.execSQL(createTableCuota)
        db.execSQL(createTablePago)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_PERSONA")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_EMPLEADO")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_SOCIO")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NO_SOCIO")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_CARNET")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_ACTIVIDAD")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_TIPO_PAGO")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_CUOTA")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_PAGO")
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
            put(COL_TELEFONO, "8001112222")
            put(COL_EMAIL, "centrodeportivoe1@centrodeportivo.com")
            put(COL_APTO_FISICO, 0)
            put(COL_FECHA_ALTA, "2024-05-19")
            put(COL_BAJA_PERSONA, 0)
        }
        db.insert(TABLE_PERSONA, null, valuesPersona)
    }

    fun insertarCredenciales(db: SQLiteDatabase) {
        val valuesEmpleado = ContentValues().apply {
            put(COL_FK_PERSONA, 1) // Asume que la Persona con ID 1 es el administrador
            put(COL_USUARIO, "Admin")
            put(COL_PASS, "admin")
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

    fun insertarPersona(
        dni: Int,
        nombre: String,
        apellido: String,
        direccion: String,
        telefono: String,
        email: String,
        aptoFisico: Boolean,
        fechaAlta: String,
        esSocio: Boolean // Parámetro para verificar si es socio o no
    ): Long {
        val db = writableDatabase

        // Inserción en la tabla Persona
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

        // Verificar si la inserción en Persona fue exitosa
        return if (idPersona != -1L) {
            if (esSocio) {
                // Inserta en la tabla Socio si es socio
                val valuesSocio = ContentValues().apply {
                    put(COL_ID_PERSONA, idPersona)
                }
                db.insert(TABLE_SOCIO, null, valuesSocio)
            } else {
                // Inserta en la tabla NoSocio si no es socio
                val valuesNoSocio = ContentValues().apply {
                    put(COL_ID_PERSONA, idPersona)
                }
                db.insert(TABLE_NO_SOCIO, null, valuesNoSocio)
            }
        } else {
            -1 // Indica un error en la inserción
        }
    }

    fun obtenerFechaActual(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return dateFormat.format(Calendar.getInstance().time)
    }

    fun buscarSociosPorNombreApellido(nombreApellido: String): Cursor {
        val db = readableDatabase
        val query = """
            SELECT $COL_ID_PERSONA, $COL_NOMBRE, $COL_APELLIDO
            FROM $TABLE_PERSONA
            WHERE $COL_NOMBRE LIKE ? OR $COL_APELLIDO LIKE ?
        """
        val likeQuery = "%$nombreApellido%"
        return db.rawQuery(query, arrayOf(likeQuery, likeQuery))
    }

    fun obtenerTodosLosSocios(): List<Socio> {
        val listaSocios = mutableListOf<Socio>()
        val db = readableDatabase

        // Consulta combinada para obtener todos los socios y no socios con su tipo
        val query = """
        SELECT 
            p.$COL_ID_PERSONA, 
            p.$COL_DNI, 
            p.$COL_NOMBRE, 
            p.$COL_APELLIDO, 
            p.$COL_DIRECCION, 
            p.$COL_TELEFONO, 
            p.$COL_EMAIL, 
            p.$COL_APTO_FISICO, 
            p.$COL_FECHA_ALTA,
            CASE 
                WHEN s.$COL_NUMERO_SOCIO IS NOT NULL THEN 'Socio'
                WHEN n.$COL_NUMERO_NO_SOCIO IS NOT NULL THEN 'No Socio'
            END AS tipo
        FROM $TABLE_PERSONA AS p
        LEFT JOIN $TABLE_SOCIO AS s ON p.$COL_ID_PERSONA = s.$COL_ID_PERSONA
        LEFT JOIN $TABLE_NO_SOCIO AS n ON p.$COL_ID_PERSONA = n.$COL_ID_PERSONA
        WHERE s.$COL_NUMERO_SOCIO IS NOT NULL OR n.$COL_NUMERO_NO_SOCIO IS NOT NULL
    """

        val cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            do {
                val idPersona = cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID_PERSONA))
                val dni = cursor.getInt(cursor.getColumnIndexOrThrow(COL_DNI))
                val nombre = cursor.getString(cursor.getColumnIndexOrThrow(COL_NOMBRE))
                val apellido = cursor.getString(cursor.getColumnIndexOrThrow(COL_APELLIDO))
                val direccion = cursor.getString(cursor.getColumnIndexOrThrow(COL_DIRECCION))
                val telefono = cursor.getString(cursor.getColumnIndexOrThrow(COL_TELEFONO))
                val email = cursor.getString(cursor.getColumnIndexOrThrow(COL_EMAIL))
                val aptoFisico = cursor.getInt(cursor.getColumnIndexOrThrow(COL_APTO_FISICO)) == 1
                val fechaAlta = cursor.getString(cursor.getColumnIndexOrThrow(COL_FECHA_ALTA))
                val tipo = cursor.getString(cursor.getColumnIndexOrThrow("tipo")) ?: "Desconocido"

                // Crear el objeto Socio con el tipo
                listaSocios.add(Socio(idPersona, dni, nombre, apellido, direccion, telefono, email, aptoFisico, fechaAlta, tipo))
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()

        return listaSocios
    }

    // Método para insertar una nueva cuota
    fun registrarCuota(idPersona: Int, fechaVencimiento: Date, numeroCuota: Int): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COL_FK_PERSONA_CUOTA, idPersona)
            put(COL_FECHA_VENCIMIENTO, fechaVencimiento.time) // Almacena la fecha en formato timestamp
            put(COL_NRO_CUOTA, numeroCuota) // Aquí almacena el número de cuota
        }

        return db.insert(TABLE_CUOTA, null, values)
    }

    fun registrarPago(fkCuota: Long, fechaPago: Date): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COL_FK_CUOTA, fkCuota)
            put(COL_FECHA_PAGO, fechaPago.time) // Usamos .time para almacenar como timestamp
        }
        return db.insert(TABLE_PAGO, null, values)
    }

    fun cuotaPagada(idPersona: Int, numeroCuota: Int): Boolean {
        val db = readableDatabase
        val query = """
        SELECT p.* 
        FROM $TABLE_PAGO AS p
        JOIN $TABLE_CUOTA AS c ON p.$COL_FK_CUOTA = c.$COL_ID_CUOTA
        WHERE c.$COL_FK_PERSONA_CUOTA = ? AND c.$COL_NRO_CUOTA = ?
    """
        val cursor = db.rawQuery(query, arrayOf(idPersona.toString(), numeroCuota.toString()))

        val pagada = cursor.count > 0
        cursor.close()
        return pagada
    }

    fun obtenerVencimientosEntreFechas(fechaDesde: Long, fechaHasta: Long): List<Vencimiento> {
        val vencimientos = mutableListOf<Vencimiento>()
        val db = this.readableDatabase
        val query = """
        SELECT c.$COL_ID_CUOTA, c.$COL_NRO_CUOTA, c.$COL_FECHA_VENCIMIENTO, 
               p.$COL_NOMBRE || ' ' || p.$COL_APELLIDO AS socioNombre
        FROM $TABLE_CUOTA AS c
        JOIN $TABLE_PERSONA AS p ON c.$COL_FK_PERSONA = p.$COL_ID_PERSONA
        WHERE c.$COL_FECHA_VENCIMIENTO BETWEEN ? AND ?
    """
        val cursor = db.rawQuery(query, arrayOf(fechaDesde.toString(), fechaHasta.toString()))

        if (cursor.moveToFirst()) {
            do {
                val idCuota = cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID_CUOTA))
                val nroCuota = cursor.getInt(cursor.getColumnIndexOrThrow(COL_NRO_CUOTA))
                val fechaVencimiento = cursor.getLong(cursor.getColumnIndexOrThrow(COL_FECHA_VENCIMIENTO))
                val socioNombre = cursor.getString(cursor.getColumnIndexOrThrow("socioNombre"))

                // Agregar el vencimiento a la lista
                vencimientos.add(Vencimiento(idCuota, nroCuota, fechaVencimiento, socioNombre))
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()

        return vencimientos
    }
}

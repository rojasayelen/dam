package com.example.clubdeportivo

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class DataBaseHelper (context: Context):
SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION ){
    companion object {
        private const val DATABASE_NAME = "ClubDeportivo"
        private const val DATABASE_VERSION = 1

        //Tables
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

        //Person columns
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
        private const val COL_BAJA_PERSONA = "Baja" //yo le pondria "BajaPersona"

        //Employee columns
        private const val COL_ID_EMPLEADO = "idEmpleado"
        private const val COL_USUARIO = "Usuario"
        private const val COL_PASS = "Contrasena"
        private const val COL_PUESTO = "Puesto"

        //Socio columns
        private const val COL_NUMERO_SOCIO = "NumeroSocio"

        //NoSocio columns
        private const val COL_NUMERO_NO_SOCIO = "NumeroNoSocio"

        //Carnet columns
        private const val COL_ID_CARNET = "idCarnet"
        private const val COL_VTO = "Vencimiento"

        //Activity columns
        private const val COL_ID_ACTIVIDAD = "idActividad"
        private const val COL_NOMBRE_ACTIVIDAD = "NombreActividad"
        private const val COL_PRECIO_ACTIVIDAD = "PrecioActividad"
        private const val COL_BAJA_ACTIVIDAD = "Baja" //Acá le pondria "BajaActividad"

        //Payment Method
        private const val COL_ID_TIPO_PAGO = "idTipoPago"
        private const val COL_TIPO_PAGO = "TipoPago"
        private const val COL_MONTO_PAGO = "MontoPago"
        private const val COL_BAJA_TIPO_PAGO = "Baja"
    }

    //Definiendo los atributos
    override fun onCreate(db: SQLiteDatabase) {
        val createTablePersona = """
            CREATE TABLE IF NOT EXISTS $TABLE_PERSONA(
                    $COL_ID_PERSONA INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                    $COL_DNI INTEGER NOT NULL,
                    $COL_NOMBRE TEXT NOT NULL,
                    $COL_APELLIDO TEXT NOT NULL,
                    $COL_DIRECCION TEXT NOT NULL,
                    $COL_TELEFONO INTEGER NOT NULL,
                    $COL_EMAIL TEXT NOT NULL,
                    $COL_APTO_FISICO BOOLEAN,
                    $COL_FECHA_ALTA INTEGER,
                    $COL_FECHA_BAJA INTEGER,
                    $COL_BAJA_PERSONA BOOLEAN
                    )    
            """.trimIndent()

        val createTableEmpleado = """
            CREATE TABLE IF NOT EXISTS $TABLE_EMPLEADO(
                $COL_ID_EMPLEADO INTEGER PRIMARY KEY AUTOINCREMENT,
                $COL_USUARIO TEXT,
                $COL_PASS TEXT,
                $COL_PUESTO TEXT,
                FOREIGN KEY ($COL_ID_PERSONA) REFERENCES $TABLE_PERSONA ($COL_ID_PERSONA)
                    ON DELETE CASCADE
                    ON UPDATE CASCADE
                )
            """.trimIndent()

        val createTableSocio = """
            CREATE TABLE IF NOT EXISTS $TABLE_SOCIO(
                $COL_NUMERO_SOCIO INTEGER PRIMARY KEY AUTOINCREMENT,
                FOREIGN KEY ($COL_ID_PERSONA) REFERENCES $TABLE_PERSONA ($COL_ID_PERSONA)
                    ON DELETE CASCADE
                    ON UPDATE CASCADE
                )
            """.trimIndent()

        val createTableNoSocio = """
            CREATE TABLE IF NOT EXISTS $TABLE_NO_SOCIO(
                $COL_NUMERO_NO_SOCIO INTEGER PRIMARY KEY AUTOINCREMENT,
                FOREIGN KEY ($COL_ID_PERSONA) REFERENCES $TABLE_PERSONA ($COL_ID_PERSONA)
                    ON DELETE CASCADE
                    ON UPDATE CASCADE
                )
            """.trimIndent()

        val createTableCarnet = """
            CREATE TABLE IF NOT EXISTS $TABLE_CARNET(
                $COL_ID_CARNET INTEGER PRIMARY KEY AUTOINCREMENT,
                $COL_VTO TEXT,
                FOREIGN KEY ($COL_NUMERO_SOCIO) REFERENCES $TABLE_SOCIO ($COL_NUMERO_SOCIO)
                    ON DELETE CASCADE
                    ON UPDATE CASCADE
            )
            """.trimIndent()

        val createTableActividades = """
            CREATE TABLE IF NOT EXISTS $TABLE_ACTIVIDAD(
                $COL_ID_ACTIVIDAD INTEGER PRIMARY KEY AUTOINCREMENT,
                $COL_NOMBRE_ACTIVIDAD TEXT,
                $COL_PRECIO_ACTIVIDAD INTEGER,
                $COL_BAJA_ACTIVIDAD BOOLEAN
            )    
            """.trimIndent()

        val createTableTipoPago = """
            CREATE TABLE IF NOT EXISTS $TABLE_TIPO_PAGO(
                $COL_ID_TIPO_PAGO INTEGER PRIMARY KEY AUTOINCREMENT,
                $COL_TIPO_PAGO TEXT,
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


    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

    }




}


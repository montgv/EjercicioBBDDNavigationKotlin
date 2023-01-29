package com.example.ejerciciobbddnavigationkotlin.baseDatos

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

//Definimos la clase AdminSQLiteOpenHelper que extiende de SQLiteOpenHelper
class AdminSQLiteOpenHelper(
    context: Context?,
    name: String?,
    factory: SQLiteDatabase.CursorFactory?,
    version: Int
) : SQLiteOpenHelper(context, name, factory, version) {
    //Generamos los dos métodos que nos obliga a implementar donde el primero de ellos es donde tenemos
    //que crear la tabla de la base de datos
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("create table escuela(dni String(9) primary key, nombre String(50), " +
                "apellidos String(50), sexo String(6))")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }
}
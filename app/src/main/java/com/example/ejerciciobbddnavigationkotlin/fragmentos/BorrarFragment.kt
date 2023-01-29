package com.example.ejerciciobbddnavigationkotlin.fragmentos

import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.ejerciciobbddnavigationkotlin.R
import com.example.ejerciciobbddnavigationkotlin.baseDatos.AdminSQLiteOpenHelper
import com.example.ejerciciobbddnavigationkotlin.databinding.FragmentBorrarBinding

class BorrarFragment : Fragment() {
    //Creamos el binding que nos sirve para la vinculacion de vista
    private lateinit var binding: FragmentBorrarBinding

    //Este es el metodo que se llama para que comienze el fragmento
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_borrar, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Definimos un escuchador mediante el método setOnClickListener que esta asociado al boton
        //con el binding, cuando se pulsa el botón, comento línea a línea
        binding.btAceptarBorrado.setOnClickListener {
            //Creamos una instancia de la subclase SQLiteOpenHelper para poder acceder a la base de datos
            val admin = AdminSQLiteOpenHelper(context, "escuela", null, 1)
            //Obtienemos el repositorio de datos en modo de escritura
            val baseDatos: SQLiteDatabase = admin.writableDatabase

            //Guardamos en una variable el dni que lo hemos recuperado del editText con la ayuda
            //del binding
            val dni = binding.edTxtDniBorrado.text.toString()

            //Comprobamos que la variable no esta vacia
            if (dni.isNotEmpty()) {
                //Guardamos en una variable de tipo numero si se borrado algun alumno de la base de datos
                val cantidad = baseDatos.delete("alumnos", "dni=?", arrayOf(dni))
                //Cerramos la base de datos
                baseDatos.close()
                //Comprobamos si cantidad es igual 1 significa que se si ha borrado un alumno,
                //muestra con toast las diferentes opciones
                if (cantidad > 0) {
                    Toast.makeText(requireContext(), "Alumno eliminado correctamente", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), "No exite el alumno", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(requireContext(), "Debes introducir todos los campos.", Toast.LENGTH_SHORT).show()
            }
        }
        //Definimos un escuchador mediante el método setOnClickListener que esta asociado al boton
        //con el binding, cuando se pulsa el botón vuelva al fragment inicial
        binding.btCancelarBorrado.setOnClickListener {
            it.findNavController().navigate(R.id.nav_incio)
        }
    }
}
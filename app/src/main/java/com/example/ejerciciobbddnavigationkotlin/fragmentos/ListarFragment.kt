package com.example.ejerciciobbddnavigationkotlin.fragmentos

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.ejerciciobbddnavigationkotlin.R
import com.example.ejerciciobbddnavigationkotlin.baseDatos.AdminSQLiteOpenHelper
import com.example.ejerciciobbddnavigationkotlin.databinding.FragmentListarBinding
import com.example.ejerciciobbddnavigationkotlin.objetos.Alumno
import com.example.ejerciciobbddnavigationkotlin.objetos.MiAdaptador
import com.example.ejerciciobbddnavigationkotlin.objetos.Sexo

class ListarFragment : Fragment() {
    //Creamos el binding que nos sirve para la vinculacion de vista
    private lateinit var binding: FragmentListarBinding
    //Creamos una lista de la clase Alumno
    var listaAlumnos: List<Alumno?>? = null

    //Este es el metodo que se llama para que comienze el fragmento
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_listar, container, false)
    }

    @SuppressLint("Recycle")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Creamos una instancia de la subclase SQLiteOpenHelper para poder acceder a la base de datos
        val admin = AdminSQLiteOpenHelper(context, "escuela", null, 1)
        //Obtienemos el repositorio de datos en modo de escritura
        val baseDatos = admin.readableDatabase

        //Creamos un cursor para recorrer toda la base de datos y creamos un arrayList donde se van
        //a guardar los diferentes alumnos
        val cursor = baseDatos.rawQuery("select * from alumnos", null)
        var dni: String?
        var nombre: String?
        var apellidos: String?
        var sexo: String
        listaAlumnos = ArrayList()
        //Esta parte es la manera que tiene el cursor para recorrer la base de datos
        if (cursor.moveToFirst()) {
            do {
                dni = cursor.getString(0)
                nombre = cursor.getString(1)
                apellidos = cursor.getString(2)
                sexo = cursor.getString(3)
                if (sexo == "Hombre") {
                    (listaAlumnos as ArrayList<Alumno?>).add(Alumno(dni, nombre, apellidos, Sexo.HOMBRE))
                } else {
                    (listaAlumnos as ArrayList<Alumno?>).add(Alumno(dni, nombre, apellidos, Sexo.MUJER))
                }
            } while (cursor.moveToNext())
        }

        //Estamos creando un adaptador que lo que hace es devolver una vista para cada objeto
        //de una coleccion de objetos de datos, es decir, para los diferentes objetos que tiene
        //el arrayList, y despues, asociamos al binding a la listView para que use ese adaptador
        val miAdaptador = MiAdaptador(view.context, R.layout.alumnos_item, listaAlumnos as List<Alumno>)
        binding.listViewListar.adapter = miAdaptador

        //Definimos un escuchador para cuando pulsemos en un item del listView haga lo que se le indica
        binding.listViewListar.setOnItemClickListener { parent, view, position, id ->
            val alumnoClick = binding.listViewListar.getItemAtPosition(position) as Alumno
            Toast.makeText(requireContext(), "Alumno con DNI: " + alumnoClick.dni, Toast.LENGTH_SHORT).show()
        }
    }
}
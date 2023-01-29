package com.example.ejerciciobbddnavigationkotlin.fragmentos

import android.content.ContentValues
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.ejerciciobbddnavigationkotlin.R
import com.example.ejerciciobbddnavigationkotlin.baseDatos.AdminSQLiteOpenHelper
import com.example.ejerciciobbddnavigationkotlin.databinding.FragmentModificaBinding

class ModificaFragment : Fragment() {
    //Creamos el binding que nos sirve para la vinculacion de vista
    private lateinit var binding: FragmentModificaBinding

    //Este es el metodo que se llama para que comienze el fragmento
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_modifica, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Definimos un escuchador mediante el método setOnClickListener que esta asociado al boton
        //con el binding, cuando se pulsa el botón, comento línea a línea
        binding.btAceptarModificacion.setOnClickListener{
            //Creamos una instancia de la subclase SQLiteOpenHelper para poder acceder a la base de datos
            val admin = AdminSQLiteOpenHelper(context, "escuela", null, 1)
            //Obtienemos el repositorio de datos en modo de escritura
            val baseDatos = admin.writableDatabase

            //Guardamos en cada variable los datos que hemos recuperado de los editText y el
            //spinner con la ayuda del binding
            val dni = binding.edTxtDniModificacion.text.toString()
            val nombre = binding.edTxtNombreModificacion.text.toString()
            val apellidos = binding.edTxtApellidosModificacion.text.toString()
            val genero = binding.spinnnerSexoModificacion.selectedItem.toString()

            //Comprobamos que las variables no estan vacias
            if (dni.isNotEmpty() && nombre.isNotEmpty() && apellidos.isNotEmpty() && genero.isNotEmpty()) {
                //Creamos un nuevo mapa de valores, donde los nombres de las columnas son las claves
                val registro = ContentValues()
                registro.put("dni", dni)
                registro.put("nombre", nombre)
                registro.put("apellidos", apellidos)
                registro.put("sexo", genero)
                //Guardamos en una variable de tipo numero si se modificado algun alumno de la base de datos
                val cantidad = baseDatos.update("alumnos", registro, "dni=$dni", null)
                //Cerramos la base de datos
                baseDatos.close()
                //Comprobamos si cantidad es igual 1 significa que se si ha borrado un alumno,
                //muestra con toast las diferentes opciones
                if (cantidad > 0) {
                    Toast.makeText(requireContext(), "Alumno modificado correctamente.", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), "No existe el alumno.", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(requireContext(), "Debes introducir todos los campos", Toast.LENGTH_SHORT).show()
            }
        }
        //Definimos un escuchador mediante el método setOnClickListener que esta asociado al boton
        //con el binding, cuando se pulsa el botón vuelva al fragment inicial
        binding.btCancelarModificacion.setOnClickListener {
            it.findNavController().navigate(R.id.nav_incio)
        }
    }
}
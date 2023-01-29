package com.example.ejerciciobbddnavigationkotlin.objetos

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.ejerciciobbddnavigationkotlin.R

//Declaramos la clase MiAdaptador que extiende de ArrayAdapter
class MiAdaptador(
    private val ctx: Context,
    private val layoutTemplate: Int,
    private val listaAlumnos: List<Alumno>):
    ArrayAdapter<Any?>(ctx, layoutTemplate, listaAlumnos) {

    @SuppressLint("ViewHolder")
    //Implementamos el metodo getView que obtiene una vista que muestra los datos de la posicion
    //actual del conjunto de datos
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val v = LayoutInflater.from(ctx).inflate(layoutTemplate, parent, false)

        //Obtiene la persona de la posicion actual
        val alumnoActual = listaAlumnos[position]

        //Enlazamos los determinados componentes del adaptador con la vinculacion de datos
        val imageViewPersona = v.findViewById<ImageView>(R.id.iVItemAlumno)
        val textViewDni = v.findViewById<TextView>(R.id.tvDni)
        val textViewNombre = v.findViewById<TextView>(R.id.tvNombre)
        val textViewApellidos = v.findViewById<TextView>(R.id.tvApellidos)

        //Cambiamos el texto de los diferentes componentes por los obtenidos de la persona actual
        textViewDni.text = alumnoActual.dni
        textViewNombre.text = alumnoActual.nombre
        textViewApellidos.text = alumnoActual.apellidos

        //Si el dato de la persona actual es igual al introducido la cambia la imagen por una concreta
        //sino le cambia la imagen por otra
        if (alumnoActual.sexo === Sexo.HOMBRE) {
            imageViewPersona.setImageResource(R.drawable.boy_avatar_icon_148454)
        } else {
            imageViewPersona.setImageResource(R.drawable.girl_avatar_icon_148461)
        }
        //Devuelve la vista
        return v
    }
}
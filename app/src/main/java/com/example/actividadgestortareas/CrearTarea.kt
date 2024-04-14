package com.example.actividadgestortareas

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.CalendarView
import android.widget.CalendarView.OnDateChangeListener
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.example.bdroom.config.TareaDB
import com.example.bdroom.models.Tarea
import java.text.SimpleDateFormat
import java.time.LocalDate


class CrearTarea : AppCompatActivity() {

    private lateinit var db: TareaDB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_tarea)

        val txtCodigo = findViewById<TextView>(R.id.txtCodigo)
        val txtDescripcion = findViewById<TextView>(R.id.txtDescripcion)
        val calendarFecha = findViewById<CalendarView>(R.id.fecha)

        db = Room.databaseBuilder(application, TareaDB::class.java, TareaDB.DATABASE_NAME)
            .allowMainThreadQueries().build()

        val btnCambiar = findViewById<Button>(R.id.btnVolver)
        val btnInsertar = findViewById<Button>(R.id.btnInsertar)

        var fecha = ""
        calendarFecha.setOnDateChangeListener(OnDateChangeListener { view, year, month, dayOfMonth ->
            fecha = year.toString()+"-"+"%02d".format(month+1)+"-"+"%02d".format(dayOfMonth)
            println(fecha)
        })

        btnCambiar.setOnClickListener {
            cambiar()
        }

        btnInsertar.setOnClickListener {
            val nombre = txtCodigo.text.toString()
            val descripcion = txtDescripcion.text.toString()
            val formatter = SimpleDateFormat("yyyy-MM-dd")
            val date = formatter.parse(fecha)

            if(!nombre.isEmpty() && !descripcion.isEmpty() && !fecha.isEmpty()) {
                val tarea = Tarea((0..100000).random().toLong(), nombre, descripcion, date, "Pendiente")
                try {
                    db.tareaDao.insert(tarea)

                    txtCodigo.setText("")
                    txtDescripcion.setText("")
                    Toast.makeText(this, "¡Creado con éxito!", Toast.LENGTH_LONG).show()
                } catch(ex: Exception){
                    Toast.makeText(this, ex.toString(), Toast.LENGTH_LONG).show()
                }
            }else{
                Toast.makeText(this, "Hay campos vacíos", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun cambiar(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
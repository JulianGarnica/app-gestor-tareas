package com.example.actividadgestortareas

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.example.bdroom.config.TareaDB
import com.example.bdroom.models.Tarea
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import java.text.SimpleDateFormat


class MainActivity : AppCompatActivity() {

    private lateinit var db: TareaDB
    private var position: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnCambiar = findViewById<Button>(R.id.btnCrear)
        val tabLayout = findViewById<TabLayout>(R.id.tabLayout)
        db = Room.databaseBuilder(application, TareaDB::class.java, TareaDB.DATABASE_NAME)
            .allowMainThreadQueries().fallbackToDestructiveMigration().build()
        val chat_area = findViewById<LinearLayout>(R.id.linearLayoutInfo)

        buscarTabla(chat_area, db)

        btnCambiar.setOnClickListener {
            cambiar()
        }

        tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                position = tab.position
                buscarTabla(chat_area, db, position)
            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {
            }

            override fun onTabReselected(p0: TabLayout.Tab?) {
            }
        })
    }

    fun buscarTabla(chat_area:LinearLayout, db: TareaDB, option: Int = 0, datoQuery: String = "") {
        chat_area.removeAllViews();

        if(datoQuery.isNullOrBlank()){
            var datos = db.tareaDao.getPendientes()
            when (option) {
                1->{
                    datos = db.tareaDao.getCompletados()
                }
                2->{
                    datos = db.tareaDao.getAtrasadas()
                }
            }

            for (dato in datos) {

                val msg_view: View = layoutInflater.inflate(R.layout.card, chat_area, false)
                val title = msg_view.findViewById<TextView>(R.id.title)
                val fechaTarea = msg_view.findViewById<TextView>(R.id.subhead)
                val descripcion = msg_view.findViewById<TextView>(R.id.body)

                title.setText(dato.nombre)
                fechaTarea.setText("Entregar: "+ SimpleDateFormat("dd/MM/yyy").format(dato.fechaTarea))
                descripcion.setText(dato.descripcion)

                msg_view.setOnClickListener {
                    val builder = AlertDialog.Builder(this)
                    builder.setTitle(dato.nombre)
                    builder.setMessage("Entregar: "+ SimpleDateFormat("dd/MM/yyy").format(dato.fechaTarea)+"\n"+dato.descripcion)
                    val tarea = Tarea(dato.idTarea, dato.nombre, dato.descripcion, dato.fechaTarea)

                    builder.setPositiveButton("Completada") { dialog, which ->
                        tarea.estado = "Completado"
                        db.tareaDao.update(tarea)
                        Toast.makeText(applicationContext,
                            "Se cambió el estado a "+tarea.estado, Toast.LENGTH_SHORT).show()
                        buscarTabla(chat_area, db, position)
                    }

                    builder.setNegativeButton("Pendiente") { dialog, which ->
                        tarea.estado = "Pendiente"
                        db.tareaDao.update(tarea)
                        Toast.makeText(applicationContext,
                            "Se cambió el estado a "+tarea.estado, Toast.LENGTH_SHORT).show()
                        buscarTabla(chat_area, db, position)
                    }

                    builder.setNeutralButton("Eliminar") { dialog, which ->
                        tarea.estado = dato.estado

                        db.tareaDao.delete(tarea)
                        Toast.makeText(applicationContext,
                            "Eliminado con éxito", Toast.LENGTH_SHORT).show()
                        buscarTabla(chat_area, db, position)
                    }

                    builder.show()
                }
                chat_area.addView(msg_view)
            }
        }else{

        }

    }

    fun cambiar(){
        val intent = Intent(this, CrearTarea::class.java)
        startActivity(intent)
        finish()
    }
    
}
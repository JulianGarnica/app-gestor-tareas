package com.example.bdroom.config

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.actividadgestortareas.config.Converters
import com.example.bdroom.dao.TareaDao
import com.example.bdroom.models.Tarea

//Clase que no se puede instanciar
@Database(
    entities = [Tarea::class],
    version = 4
)
@TypeConverters(Converters::class)
abstract class TareaDB: RoomDatabase() {

    abstract val tareaDao: TareaDao

    companion object{
        const val DATABASE_NAME = "db_tareas"
    }
}
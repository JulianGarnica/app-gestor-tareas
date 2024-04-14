package com.example.bdroom.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.bdroom.models.Tarea

//Objeto de acceso a datos
@Dao
interface TareaDao {

    @Query("SELECT * FROM tarea ORDER BY fechaTarea DESC")
    fun getAll(): List<Tarea>

    @Query("SELECT * FROM tarea WHERE estado = 'Pendiente' AND fechaTarea >= :dateActual ORDER BY fechaTarea ASC")
    fun getPendientes(dateActual: String = System.currentTimeMillis().toString()): List<Tarea>

    @Query("SELECT * FROM tarea WHERE estado = 'Completado' ORDER BY fechaTarea ASC")
    fun getCompletados(): List<Tarea>

    @Query("SELECT * FROM tarea WHERE estado = 'Pendiente' AND fechaTarea < :dateActual ORDER BY fechaTarea ASC")
    fun getAtrasadas(dateActual: String = System.currentTimeMillis().toString()): List<Tarea>

    @Insert
    fun insert(perso:Tarea)

    @Query("SELECT * FROM tarea WHERE idTarea = :idTarea")
    fun getById(idTarea: Long): Tarea

    @Update
    fun update(perso:Tarea)

    @Delete
    fun delete(perso:Tarea)

}
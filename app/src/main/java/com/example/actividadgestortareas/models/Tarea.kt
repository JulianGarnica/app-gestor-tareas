package com.example.bdroom.models

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity
data class Tarea (

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idTarea")
    var idTarea: Long = 0,

    @ColumnInfo(name = "nombre")
    @NonNull
    var nombre: String = "",

    @ColumnInfo(name = "descripcion")
    @NonNull
    var descripcion: String = "",

    @ColumnInfo(name = "fechaTarea")
    @NonNull
    var fechaTarea: Date,

    @ColumnInfo(name = "estado")
    @NonNull
    var estado: String = ""
)
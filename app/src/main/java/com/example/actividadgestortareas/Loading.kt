package com.example.actividadgestortareas

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.Timer
import kotlin.concurrent.schedule
import kotlin.system.exitProcess

class Loading : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_loading)
        Timer().schedule(3000){

            //calls this function after delay
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
            finish()

            exitProcess(1)
        }
    }
}
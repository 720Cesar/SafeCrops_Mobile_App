package com.example.safecrops

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.cardview.widget.CardView

class homeTester : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_tester)

        val btnCamara = findViewById<CardView>(R.id.btnCamara)
        //val btnPerfil = findViewById<CardView>(R.id.btnPerfil)
        val btnSalir = findViewById<CardView>(R.id.btnCerrarSesion)

        btnCamara.setOnClickListener(){
            irCamara()
        }

        btnSalir.setOnClickListener(){
            logout()
        }

    }

    private fun irCamara(){
        startActivity(Intent(applicationContext, elige_subirFoto::class.java))
        finish()
    }

    private fun logout(){
        startActivity(Intent(applicationContext, MainActivity::class.java))
        finish()
    }

    override fun onBackPressed() {} //Evita que el usuario regrese a una Activity equivocada

}
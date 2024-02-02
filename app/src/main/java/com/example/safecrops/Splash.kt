package com.example.safecrops

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.safecrops.databinding.ActivitySplashBinding

class Splash : AppCompatActivity() {

    lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fondo = binding.fondo
        val logo = binding.imgLogo
        val nombre = binding.tituloSplash

        //Se animan cada uno de los elementos del Splash
        //Se asigna una duración de 1s, delay de 3s y el movimiento
        fondo.animate().setDuration(1000).setStartDelay(3000).translationY(-2600f)
        logo.animate().setDuration(1000).setStartDelay(3000)?.translationY(2400f)
        nombre.animate().setDuration(1000).setStartDelay(3000)?.translationY(2400f)


        //Se hace uso de un handler para iniciar la actividad después del Splash
        val mHandler = Handler()
        mHandler.postDelayed({
            //Se inicia la activity del inicio de sesión después de 4.6s de iniciar el Splash
            val intent: Intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.fade_in, R.anim.fade_in);
        }, 4600L)

    }
}
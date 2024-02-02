package com.example.safecrops

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.safecrops.inicioCultivos

class detalleCultivo : AppCompatActivity() {
    lateinit var t_ID: TextView
    lateinit var t_Nombre: TextView
    lateinit var t_Descripcion: TextView
    lateinit var t_Volver: TextView
    var position = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_cultivo)
        t_ID = findViewById(R.id.txtIDCultivoDet)
        t_Nombre = findViewById(R.id.txtNombreCultivoDet)
        t_Descripcion = findViewById(R.id.txtDescCultivoDet)
        t_Volver = findViewById(R.id.txtAtrasDet)
        val intent = intent
        position = intent.extras!!.getInt("position")
        t_ID.text = inicioCultivos.cultivosArrayList[position].getId()
        t_Nombre.text = inicioCultivos.cultivosArrayList[position].getNombreCultivos()
        t_Descripcion.text = inicioCultivos.cultivosArrayList[position].getDescripcionCultivos()
        t_Volver.setOnClickListener(View.OnClickListener { volver() })
    }

    override fun onBackPressed() {
        volver()
    }

    private fun volver() {
        val intent = Intent(this@detalleCultivo, inicioCultivos::class.java)
        startActivity(intent)
    }
}
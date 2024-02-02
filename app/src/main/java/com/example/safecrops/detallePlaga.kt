package com.example.safecrops

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class detallePlaga : AppCompatActivity() {

    lateinit var t_ID: TextView
    lateinit var t_Nombre: TextView
    lateinit var t_Cultivo: TextView
    lateinit var t_Descripcion: TextView
    lateinit var t_Cura: TextView
    lateinit var t_Volver: TextView
    var position = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_plaga)

        t_ID = findViewById(R.id.txtIDEnfermedadDet)
        t_Nombre = findViewById(R.id.txtNombreEnfermedadDet)
        t_Cultivo = findViewById(R.id.txtCultivoEnDet)
        t_Descripcion = findViewById(R.id.txtDescEnfermedadDet)
        t_Cura = findViewById(R.id.txtCuraDet)
        t_Volver = findViewById(R.id.txtAtrasDet)

        val intent = intent

        position = intent.extras!!.getInt("position")

        t_ID.text = inicioPlagas.plagasArrayList[position].getId()
        t_Nombre.text = inicioPlagas.plagasArrayList[position].getNombreEnfermedad()
        t_Cultivo.text = inicioPlagas.plagasArrayList[position].getCultivoEnfermedad()
        t_Descripcion.text = inicioPlagas.plagasArrayList[position].getDescripcionEnfermedad()
        t_Cura.text = inicioPlagas.plagasArrayList[position].getCuraEnfermedad()
        t_Volver.setOnClickListener(){
            volver()
        }

    }

    override fun onBackPressed() {
        volver()
    }

    private fun volver() {
        val intent = Intent(this@detallePlaga, inicioPlagas::class.java)
        startActivity(intent)
    }

}
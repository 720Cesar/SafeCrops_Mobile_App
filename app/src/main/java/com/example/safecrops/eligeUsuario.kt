package com.example.safecrops

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

class eligeUsuario : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_elige_usuario)

        var img = findViewById<ImageButton>(R.id.imgBtnBack)

        img.setOnClickListener(){
            Eliminar()
        }

    }

    fun registerExperto(view: View?) {
        startActivity(Intent(applicationContext, registroExperto::class.java))
        finish()
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }

    fun registerTester(view: View?) {
        startActivity(Intent(applicationContext, registroTester::class.java))
        finish()
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }


    fun Eliminar() {
        val request: StringRequest = object : StringRequest(
            Method.POST,
            "https://pruebasfupem.000webhostapp.com/eliminarRegistroAtras.php",
            Response.Listener { response ->
                if (response == "Registro eliminado") {
                    Toast.makeText(
                        this@eligeUsuario,
                        "Registro cancelado",
                        Toast.LENGTH_SHORT
                    ).show()
                    startActivity(Intent(applicationContext, registro::class.java))
                    finish()
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
                } else {
                    Toast.makeText(
                        this@eligeUsuario,
                        "Error en la eliminación",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            },
            Response.ErrorListener {
                Toast.makeText(
                    this@eligeUsuario,
                    "Error en la eliminación",
                    Toast.LENGTH_SHORT
                ).show()
            }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): MutableMap<String, String>? {
                return null
            }
        }
        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(request)
    }

    override fun onBackPressed() {
        //Función para eliminar el registro recién hecho
        Eliminar()
    }
}
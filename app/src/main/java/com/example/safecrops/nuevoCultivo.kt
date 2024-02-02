package com.example.safecrops

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

class nuevoCultivo : AppCompatActivity() {

    var nombreCultivo: EditText? = null
    var descCultivo: EditText? = null
    var nombre: String? = null
    var descripcion: String? = null
    var url = "https://pruebasfupem.000webhostapp.com/registrarCultivo.php"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nuevo_cultivo)

        val txtAtras = findViewById<TextView>(R.id.txtAtrasCult)
        nombreCultivo = findViewById<EditText>(R.id.txtNombreCultivo)
        descCultivo = findViewById<EditText>(R.id.txtDescCultivo)
        val enviar = findViewById<Button>(R.id.btnEnviarCult)

        enviar.setOnClickListener(){
            insertar()
        }

        txtAtras.setOnClickListener(){
            atras()
        }


    }

    private fun insertar(){

        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Cargando...")

        if (nombreCultivo!!.text.toString() == ""){
            nombreCultivo!!.error = "Completa el campo"
            return
        }else if(descCultivo!!.text.toString() == ""){
            descCultivo!!.error = "Completa el campo"
            return
        }else{
            nombre = nombreCultivo!!.text.toString().trim{ it <= ' '}
            descripcion = descCultivo!!.text.toString().trim{ it <= ' '}


            progressDialog.show()

            val request: StringRequest = object :StringRequest(
                Method.POST,
                url,
                Response.Listener<String> {response ->
                    progressDialog.dismiss()

                    if(response.equals("Registrado correctamente", ignoreCase = true)){
                        Toast.makeText(this, "Datos insertados correctamente", Toast.LENGTH_SHORT).show()
                        atras()
                    }else{
                        Toast.makeText(this, response, Toast.LENGTH_SHORT).show()
                    }

                }, Response.ErrorListener { error ->
                    progressDialog.dismiss()
                    Toast.makeText(this, error.message.toString(), Toast.LENGTH_SHORT)
                        .show()
                }) {

                @Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String>? {
                    //Se realiza la conexión a la base de datos con sus campos
                    //Devuelve el valor de los parámetros que conectan los valores ingresados por el usuario
                    val params: MutableMap<String, String> = HashMap()
                    params["nombreCultivo"] = nombre!!
                    params["descripcionCultivo"] = descripcion!!
                    return params
                }

            }

            //Se hace la solicitud a la BD
            val requestQueue = Volley.newRequestQueue(this)
            requestQueue.add(request)

        }
    }

    private fun atras(){
        startActivity(Intent(applicationContext, inicioCultivos::class.java))
        finish()
    }

    override fun onBackPressed() {}

}


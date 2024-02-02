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

class nuevaPlaga : AppCompatActivity() {

    var nombreEnfermedad: EditText? = null
    var cultivoEnfermedad: EditText? = null
    var descEnfermedad: EditText? = null
    var curaEnfermedad: EditText? = null

    var nombre: String? = null
    var cultivoE: String? = null
    var descripcion: String? = null
    var cura: String? = null

    var url = "https://pruebasfupem.000webhostapp.com/registrarEnfermedad.php"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nueva_plaga)

        val txtAtras = findViewById<TextView>(R.id.txtAtrasEnf)
        val enviar = findViewById<Button>(R.id.btnEnviarEnf)

        nombreEnfermedad = findViewById<EditText>(R.id.txtNombreEnfermedad)
        cultivoEnfermedad = findViewById<EditText>(R.id.txtCultivoEnfermedad)
        descEnfermedad = findViewById<EditText>(R.id.txtDescEnfermedad)
        curaEnfermedad = findViewById<EditText>(R.id.txtCuraEnfermedad)

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

        if (nombreEnfermedad!!.text.toString() == ""){
            nombreEnfermedad!!.error = "Completa el campo"
            return
        }else if(cultivoEnfermedad!!.text.toString() == ""){
            cultivoEnfermedad!!.error = "Completa el campo"
            return
        }else if(descEnfermedad!!.text.toString() == ""){
            descEnfermedad!!.error = "Completa el campo"
            return
        }else if(curaEnfermedad!!.text.toString() == ""){
            curaEnfermedad!!.error = "Completa el campo"
            return
        }else{
            nombre = nombreEnfermedad!!.text.toString().trim{ it <= ' '}
            cultivoE = cultivoEnfermedad!!.text.toString().trim{ it <= ' '}
            descripcion = descEnfermedad!!.text.toString().trim{ it <= ' '}
            cura = curaEnfermedad!!.text.toString().trim{it <= ' '}

            progressDialog.show()

            val request: StringRequest = object : StringRequest(
                Method.POST,
                url,
                Response.Listener<String> { response ->
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
                    params["nombreEnfermedad"] = nombre!!
                    params["cultivoEnfermedad"] = cultivoE!!
                    params["descripcionEnfermedad"] = descripcion!!
                    params["curaEnfermedad"] = cura!!
                    return params
                }

            }

            //Se hace la solicitud a la BD
            val requestQueue = Volley.newRequestQueue(this)
            requestQueue.add(request)

        }
    }

    private fun atras(){
        startActivity(Intent(applicationContext, inicioPlagas::class.java))
        finish()
    }

    override fun onBackPressed() {}
}
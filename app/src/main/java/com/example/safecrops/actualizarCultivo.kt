package com.example.safecrops

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

class actualizarCultivo : AppCompatActivity() {
    lateinit var t_Nombre: EditText
    lateinit var t_Descripcion: EditText
    lateinit var t_Atras: TextView
    lateinit var btnActualizar: Button
    private var position = 0
    var url = "https://pruebasfupem.000webhostapp.com/actualizarCultivo.php"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actualizar_cultivo)
        t_Nombre = findViewById(R.id.txtNombreCultivo)
        t_Descripcion = findViewById(R.id.txtDescCultivo)
        t_Atras = findViewById(R.id.txtAtrasCultAct)
        btnActualizar = findViewById(R.id.btnActualizarCult)

        val intent = intent
        position = intent.extras!!.getInt("position")
        t_Nombre.setText(inicioCultivos.cultivosArrayList[position].getNombreCultivos())
        t_Descripcion.setText(inicioCultivos.cultivosArrayList[position].getDescripcionCultivos())
        btnActualizar.setOnClickListener(View.OnClickListener { view -> actualizar(view) })
        t_Atras.setOnClickListener(){atras()}
    }

    fun actualizar(view: View?) {
        val id = inicioCultivos.cultivosArrayList[position].getId()
        val nombre = t_Nombre!!.text.toString().trim { it <= ' ' }
        val descripcion = t_Descripcion!!.text.toString().trim { it <= ' ' }
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Actualizando datos...")
        progressDialog.dismiss()
        val request: StringRequest =
            object : StringRequest(Method.POST, url, Response.Listener<String?> { response ->
                Toast.makeText(this@actualizarCultivo, response, Toast.LENGTH_SHORT).show()
                startActivity(Intent(applicationContext, inicioCultivos::class.java))
                finish()
                progressDialog.dismiss()
            }, Response.ErrorListener {
                Toast.makeText(
                    this@actualizarCultivo,
                    "Error en la actualización de datos",
                    Toast.LENGTH_SHORT
                ).show()
                progressDialog.dismiss()
            }) {
                @Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String>? {
                    val params: MutableMap<String, String> = HashMap()
                    params["id_Cultivo"] = id
                    params["nombreCultivo"] = nombre
                    params["descripcionCultivo"] = descripcion
                    return params
                }
            }
        val requestQueue = Volley.newRequestQueue(this@actualizarCultivo)
        requestQueue.add(request)
    }

    private fun atras(){
        val intent = Intent(this@actualizarCultivo, inicioCultivos::class.java)
        startActivity(intent)
    }

}
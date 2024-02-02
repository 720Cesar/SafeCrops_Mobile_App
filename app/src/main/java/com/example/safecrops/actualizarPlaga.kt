package com.example.safecrops

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

class actualizarPlaga : AppCompatActivity() {

    lateinit var t_Nombre: EditText
    lateinit var t_Cultivo: EditText
    lateinit var t_Descripcion: EditText
    lateinit var t_Cura: EditText
    lateinit var t_Atras: TextView
    lateinit var btnActualizar: Button

    private var position = 0

    var url = "https://pruebasfupem.000webhostapp.com/actualizarEnfermedad.php"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actualizar_plaga)

        t_Nombre = findViewById(R.id.txtNombreEnfermedad)
        t_Cultivo = findViewById(R.id.txtCultivoEnfermedad)
        t_Descripcion = findViewById(R.id.txtDescEnfermedad)
        t_Cura = findViewById(R.id.txtCuraEnfermedad)
        t_Atras = findViewById(R.id.txtAtrasEnfAct)
        btnActualizar = findViewById(R.id.btnActualizarEnf)

        val intent = intent
        position = intent.extras!!.getInt("position")

        t_Nombre.setText(inicioPlagas.plagasArrayList[position].getNombreEnfermedad())
        t_Cultivo.setText(inicioPlagas.plagasArrayList[position].getCultivoEnfermedad())
        t_Descripcion.setText(inicioPlagas.plagasArrayList[position].getDescripcionEnfermedad())
        t_Cura.setText(inicioPlagas.plagasArrayList[position].getCuraEnfermedad())

        btnActualizar.setOnClickListener(View.OnClickListener { view -> actualizar(view) })
        t_Atras.setOnClickListener(){atras()}
    }

    fun actualizar(view: View?) {

        val id = inicioPlagas.plagasArrayList[position].getId()
        val nombre = t_Nombre!!.text.toString().trim { it <= ' ' }
        val cultivo = t_Cultivo!!.text.toString().trim {it <= ' '}
        val descripcion = t_Descripcion!!.text.toString().trim { it <= ' ' }
        val cura = t_Cura!!.text.toString().trim { it <= ' '}

        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Actualizando datos...")
        progressDialog.dismiss()
        val request: StringRequest =
            object : StringRequest(Method.POST, url, Response.Listener<String?> { response ->
                Toast.makeText(this@actualizarPlaga, response, Toast.LENGTH_SHORT).show()
                startActivity(Intent(applicationContext, inicioPlagas::class.java))
                finish()
                progressDialog.dismiss()
            }, Response.ErrorListener {
                Toast.makeText(
                    this@actualizarPlaga,
                    "Error en la actualización de datos",
                    Toast.LENGTH_SHORT
                ).show()
                progressDialog.dismiss()
            }) {
                @Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String>? {
                    val params: MutableMap<String, String> = HashMap()
                    params["id_Enfermedad"] = id
                    params["nombreEnfermedad"] = nombre
                    params["cultivoEnfermedad"] = cultivo
                    params["descripcionEnfermedad"] = descripcion
                    params["curaEnfermedad"] = cura
                    return params
                }
            }
        val requestQueue = Volley.newRequestQueue(this@actualizarPlaga)
        requestQueue.add(request)
    }

    private fun atras(){
        val intent = Intent(this@actualizarPlaga, inicioPlagas::class.java)
        startActivity(intent)
    }

}
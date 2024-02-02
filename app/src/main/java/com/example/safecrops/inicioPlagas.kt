package com.example.safecrops

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.json.JSONException
import org.json.JSONObject

class inicioPlagas : AppCompatActivity() {

    lateinit var listView: ListView
    var adapter: adapterPlagas? = null
    lateinit var btnNuevo: FloatingActionButton
    var url = "https://pruebasfupem.000webhostapp.com/consultarEnfermedades.php"
    var plagas: Plagas? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicio_plagas)

        listView = findViewById(R.id.listMostrarEn)
        adapter = adapterPlagas(this, plagasArrayList)
        listView.adapter = adapter
        btnNuevo = findViewById(R.id.btnInsertar)
        btnNuevo.setOnClickListener(View.OnClickListener { nuevo() })
        listView.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->

                //Permite abrir un progressDialago para que el usuario pueda realizar acciones en cada registro
                val builder = AlertDialog.Builder(view.context)
                val progressDialog = ProgressDialog(view.context)

                //Se enlistan las acciones a realizar con los registros
                val dialogItem = arrayOf<CharSequence>("Detalles", "Editar", "Eliminar")
                builder.setTitle(plagasArrayList[position].getNombreEnfermedad())
                builder.setItems(dialogItem) { dialogInterface, i ->
                    when (i) {
                        0 -> startActivity(Intent(applicationContext, detallePlaga::class.java).
                        putExtra("position", position))
                        1 -> startActivity(Intent(applicationContext, actualizarPlaga::class.java).
                        putExtra("position", position))
                        2 -> Eliminar(plagasArrayList[position].getId())
                    }
                }
                builder.create().show()
        }
        mostrarInfo()
    }

    private fun Eliminar(id: String) {
        val request: StringRequest = object : StringRequest(
            Method.POST,
            "https://pruebasfupem.000webhostapp.com/eliminarEnfermedad.php",
            Response.Listener { response ->
                if (response == "Registro eliminado") {
                    Toast.makeText(this@inicioPlagas, "Dato eliminado correctamente", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(applicationContext, inicioPlagas::class.java))
                } else {
                    Toast.makeText(this@inicioPlagas, "Error en la eliminación", Toast.LENGTH_SHORT).show()
                }
            },
            Response.ErrorListener {
                Toast.makeText(this@inicioPlagas, "Error en la eliminación", Toast.LENGTH_SHORT).show()
            }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String>? {
                val params: MutableMap<String, String> = HashMap()
                params["id_Enfermedad"] = id
                return params
            }
        }
        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(request)
    }

    private fun mostrarInfo() {
        val request = StringRequest(Request.Method.POST, url, { response ->
            inicioPlagas.plagasArrayList.clear()
            try {
                val jsonObject = JSONObject(response)
                val Exito =
                    jsonObject.getString("Exito") //Obtiene el valor de cadena enviado desde PHP
                val jsonArray = jsonObject.getJSONArray("datos")
                if (Exito == "1") { //Si se obtienen datos, se retorna un valor 1 para obtener los datos en la app
                    //Se obtienen los datos de todos los registros
                    for (j in 0 until jsonArray.length()) {
                        val `object` = jsonArray.getJSONObject(j)
                        val id_Enfermedad = `object`.getString("id_Enfermedad")
                        val nombreEnfermedad = `object`.getString("nombreEnfermedad")
                        val cultivoEnfermedad = `object`.getString("cultivoEnfermedad")
                        val descripcionEnfermedad = `object`.getString("descripcionEnfermedad")
                        val curaEnfermedad = `object`.getString("curaEnfermedad")

                        plagas = Plagas(id_Enfermedad, nombreEnfermedad, cultivoEnfermedad, descripcionEnfermedad, curaEnfermedad)
                        inicioPlagas.plagasArrayList.add(plagas!!) //Se envian los registros obtenidos al ArrayList
                        adapter!!.notifyDataSetChanged()
                    }
                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }) { error ->
            Toast.makeText(this@inicioPlagas, error.message, Toast.LENGTH_SHORT).show()
        }
        //Solicitar información con Volley
        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(request)
    }

    //Función para abrir Activity de registro de cultivos
    fun nuevo() {
        val intent = Intent(this@inicioPlagas, nuevaPlaga::class.java)
        startActivity(intent)
    }

    //Función para evitar que el usuario se salga de la Activity
    override fun onBackPressed() {}

    companion object {
        @JvmField
        var plagasArrayList = ArrayList<Plagas>()
    }

}
package com.example.safecrops

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView.OnItemClickListener
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.*
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.json.JSONException
import org.json.JSONObject

class inicioCultivos : AppCompatActivity() {
    lateinit var listView: ListView
    var adapter: adapterCultivos? = null
    lateinit var btnNuevo: FloatingActionButton
    var url = "https://pruebasfupem.000webhostapp.com/consultarCultivos.php"
    var cultivos: Cultivos? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicio_cultivos)
        listView = findViewById(R.id.listMostrar)
        adapter = adapterCultivos(this, cultivosArrayList)
        listView.adapter = adapter
        btnNuevo = findViewById(R.id.btnInsertar)
        btnNuevo.setOnClickListener(View.OnClickListener { nuevo() })
        listView.onItemClickListener = OnItemClickListener { parent, view, position, id ->

            //Permite abrir un progressDialago para que el usuario pueda realizar acciones en cada registro
            val builder = AlertDialog.Builder(view.context)
            val progressDialog = ProgressDialog(view.context)

            //Se enlistan las acciones a realizar con los registros
            val dialogItem = arrayOf<CharSequence>("Detalles", "Editar", "Eliminar")
            builder.setTitle(cultivosArrayList[position].getNombreCultivos())
            builder.setItems(dialogItem) { dialogInterface, i ->
                when (i) {
                    0 -> startActivity(
                        Intent(
                            applicationContext,
                            detalleCultivo::class.java
                        ).putExtra("position", position)
                    )
                    1 -> startActivity(
                        Intent(
                            applicationContext,
                            actualizarCultivo::class.java
                        ).putExtra("position", position)
                    )
                    2 -> Eliminar(cultivosArrayList[position].getId())
                }
            }
            builder.create().show()
        }
        mostrarInfo()
    }

    private fun Eliminar(id: String) {
        val request: StringRequest = object : StringRequest(
            Method.POST,
            "https://pruebasfupem.000webhostapp.com/eliminarCultivo.php",
            Response.Listener { response ->
                if (response == "Registro eliminado") {
                    Toast.makeText(
                        this@inicioCultivos,
                        "Dato eliminado correctamente",
                        Toast.LENGTH_SHORT
                    ).show()
                    startActivity(Intent(applicationContext, inicioCultivos::class.java))
                } else {
                    Toast.makeText(
                        this@inicioCultivos,
                        "Error en la eliminación",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            },
            Response.ErrorListener {
                Toast.makeText(
                    this@inicioCultivos,
                    "Error en la eliminación",
                    Toast.LENGTH_SHORT
                ).show()
            }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String>? {
                val params: MutableMap<String, String> = HashMap()
                params["id_Cultivo"] = id
                return params
            }
        }
        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(request)
    }

    //Función que permite obtener y mostrar la información de los cultivos
    private fun mostrarInfo() {
        val request = StringRequest(Request.Method.POST, url, { response ->
            cultivosArrayList.clear()
            try {
                val jsonObject = JSONObject(response)
                val Exito =
                    jsonObject.getString("Exito") //Obtiene el valor de cadena enviado desde PHP
                val jsonArray = jsonObject.getJSONArray("datos")
                if (Exito == "1") { //Si se obtienen datos, se retorna un valor 1 para obtener los datos en la app
                    //Se obtienen los datos de todos los registros
                    for (i in 0 until jsonArray.length()) {
                        val `object` = jsonArray.getJSONObject(i)
                        val id_Cultivo = `object`.getString("id_Cultivo")
                        val nombreCultivo = `object`.getString("nombreCultivo")
                        val descripcionCultivo = `object`.getString("descripcionCultivo")
                        cultivos = Cultivos(id_Cultivo, nombreCultivo, descripcionCultivo)
                        cultivosArrayList.add(cultivos!!) //Se envian los registros obtenidos al ArrayList
                        adapter!!.notifyDataSetChanged()
                    }
                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }) { error ->
            Toast.makeText(this@inicioCultivos, error.message, Toast.LENGTH_SHORT).show()
        }
        //Solicitar información con Volley
        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(request)
    }

    //Función para abrir Activity de registro de cultivos
    fun nuevo() {
        val intent = Intent(this@inicioCultivos, nuevoCultivo::class.java)
        startActivity(intent)
    }

    //Función para evitar que el usuario se salga de la Activity
    override fun onBackPressed() {}

    companion object {
        @JvmField
        var cultivosArrayList = ArrayList<Cultivos>()
    }
}
package com.example.safecrops

import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.util.*
import android.app.DatePickerDialog

class registroExperto : AppCompatActivity() {
    var t_nombre: EditText? = null
    var t_apellidoP: EditText? = null
    var t_apellidoM: EditText? = null
    var t_fechaNac: EditText? = null
    var t_correo: EditText? = null
    var t_institucion: EditText? = null
    var btnSubir: Button? = null
    lateinit var editTextFecha: EditText

    //VARIABLES PARA SUBIR IMAGEN -----------------------
    private var bitmap: Bitmap? = null
    private val PICK_IMAGE_REQUEST = 1
    private val UPLOAD_URL = "https://pruebasfupem.000webhostapp.com/subirImgExp.php"
    private val KEY_IMAGEN = "imagen"

    //-------------------------------------------------------
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro_experto)
        t_nombre = findViewById(R.id.txtNombreExp)
        t_apellidoP = findViewById(R.id.txtApellidoPExp)
        t_apellidoM = findViewById(R.id.txtApellidoMExp)
        t_fechaNac = findViewById(R.id.txtFechaNacExp)
        t_correo = findViewById(R.id.txtCorreoExp)
        t_institucion = findViewById(R.id.txtInstitucion)
        editTextFecha = findViewById(R.id.txtFechaNacExp)
        var btnBuscar = findViewById<Button>(R.id.btnCargaFotoExp)
        var btnGuardar = findViewById<Button>(R.id.btnGuardarExp)

        var atras = findViewById<ImageButton>(R.id.imgBtnBack)

        editTextFecha.setOnClickListener(){
            mostrarDatePicker()
        }

        atras.setOnClickListener(){
            Eliminar()
        }

        //Función que llama a las funciones para insertar datos y subir la imagen del usuario
        btnGuardar.setOnClickListener(View.OnClickListener {
            insertaDatos()
            uploadImage()
        })

        //Función que permite abrir la galería del celular para seleccionar una imagen
        btnBuscar.setOnClickListener(View.OnClickListener { showFileChooser() })
    }

    private fun insertaDatos() {
        val nombre = t_nombre!!.text.toString().trim { it <= ' ' }
        val apellidoP = t_apellidoP!!.text.toString().trim { it <= ' ' }
        val apellidoM = t_apellidoM!!.text.toString().trim { it <= ' ' }
        val fechaNac = t_fechaNac!!.text.toString().trim { it <= ' ' }
        val correo = t_correo!!.text.toString().trim { it <= ' ' }
        val institucion = t_correo!!.text.toString().trim { it <= ' ' }
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Cargando...")
        if (nombre.isEmpty()) {
            t_nombre!!.error = "Ingrese su nombre"
            return
        } else if (apellidoP.isEmpty()) {
            t_apellidoP!!.error = "Ingrese su apellido parterno"
            return
        } else if (fechaNac.isEmpty()) {
            t_fechaNac!!.error = "Ingrese su fecha de nacimiento"
            return
        } else if (correo.isEmpty()) {
            t_fechaNac!!.error = "Ingrese su correo"
            return
        } else if (institucion.isEmpty()) {
            t_institucion!!.error = "Ingrese la institución a la que pertenece"
            return
        } else {
            progressDialog.show()
            val request: StringRequest = object : StringRequest(
                Method.POST,
                "https://pruebasfupem.000webhostapp.com/insertarExperto.php",
                Response.Listener<String> { response ->
                    if (response.equals("Registrado correctamente", ignoreCase = true)) {
                        Toast.makeText(this@registroExperto, "Registro exitoso", Toast.LENGTH_SHORT)
                            .show()
                        progressDialog.dismiss()

                        //Envia al usuario al inicio de sesión
                        val intent = Intent(this@registroExperto, MainActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this@registroExperto, response, Toast.LENGTH_SHORT).show()
                        progressDialog.dismiss()
                        Toast.makeText(
                            this@registroExperto,
                            "Error en el registro de datos",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                },
                Response.ErrorListener { error ->
                    Toast.makeText(this@registroExperto, error.message, Toast.LENGTH_SHORT).show()
                    progressDialog.dismiss()
                }) {
                @Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String>? {
                    val params: MutableMap<String, String> = HashMap()
                    params["nombre"] = nombre
                    params["apellidoP"] = apellidoP
                    params["apellidoM"] = apellidoM
                    params["fechaNac"] = fechaNac
                    params["correo"] = correo
                    params["institucionPerteneciente"] = institucion
                    return params
                }
            }
            val requestQueue = Volley.newRequestQueue(this@registroExperto)
            requestQueue.add(request)
        }
    }

    fun registerElige(view: View?) {
        startActivity(Intent(applicationContext, eligeUsuario::class.java))
        finish()
    }

    //Funciones para subir la imagen del usuario --------------------------------------------
    //Función que retorna la imagen codificada en base 64
    fun getStringImagen(bmp: Bitmap?): String {
        val baos = ByteArrayOutputStream()
        bmp!!.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val imageBytes = baos.toByteArray()
        return Base64.encodeToString(imageBytes, Base64.DEFAULT)
    }

    //Función que permite subir la imagen del usuario una vez cargada
    //Realiza una conexión a la base de datos
    private fun uploadImage() {
        //Mostrar el diálogo de progreso
        val loading = ProgressDialog.show(this, "Subiendo...", "Espere por favor...", false, false)
        val stringRequest: StringRequest = object : StringRequest(
            Method.POST, UPLOAD_URL,
            Response.Listener { s -> //Descartar el diálogo de progreso
                loading.dismiss()
                //Mostrando el mensaje de la respuesta
                Toast.makeText(this@registroExperto, s, Toast.LENGTH_LONG).show()
            },
            Response.ErrorListener {
                //Descartar el diálogo de progreso
                loading.dismiss()

                //Showing toast
                //Toast.makeText(registroTester.this, volleyError.getMessage().toString(), Toast.LENGTH_LONG).show();
            }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String>? {
                //Convertir bits a cadena
                val imagen = getStringImagen(bitmap)

                //Obtener el nombre de la imagen

                //Creación de parámetros
                val params: MutableMap<String, String> = Hashtable()

                //Agregando de parámetros
                params[KEY_IMAGEN] = imagen

                //Parámetros de retorno
                return params
            }
        }

        //Creación de una cola de solicitudes
        val requestQueue = Volley.newRequestQueue(this)

        //Agregar solicitud a la cola
        requestQueue.add(stringRequest)
    }

    private fun showFileChooser() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Selecciona tu imagen"), PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.data != null) {
            val filePath = data.data
            try {
                //Cómo obtener el mapa de bits de la Galería
                bitmap = MediaStore.Images.Media.getBitmap(contentResolver, filePath)
                //Configuración del mapa de bits en ImageView
                //imageView.setImageBitmap(bitmap);
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    fun Eliminar() {
        val request: StringRequest = object : StringRequest(
            Method.POST,
            "https://pruebasfupem.000webhostapp.com/eliminarRegistroAtras.php",
            Response.Listener { response ->
                if (response == "Registro eliminado") {
                    Toast.makeText(
                        this@registroExperto,
                        "Registro cancelado",
                        Toast.LENGTH_SHORT
                    ).show()
                    startActivity(Intent(applicationContext, registro::class.java))
                    finish()
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
                } else {
                    Toast.makeText(
                        this@registroExperto,
                        "Error en la eliminación",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            },
            Response.ErrorListener {
                Toast.makeText(
                    this@registroExperto,
                    "Error. Vuelve a intentar el proceso",
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

    private fun mostrarDatePicker() {
        val c = Calendar.getInstance()
        val anio = c[Calendar.YEAR]
        val mes = c[Calendar.MONTH]
        val dia = c[Calendar.DAY_OF_MONTH]
        val datePickerDialog = DatePickerDialog(this, { view, year, monthOfYear, dayOfMonth ->
            val fecha = String.format("%04d/%02d/%02d", year, monthOfYear + 1, dayOfMonth)
            editTextFecha!!.setText(fecha)
        }, anio, mes, dia)
        datePickerDialog.show()
    }


    override fun onBackPressed() {
        Eliminar()
    }



}
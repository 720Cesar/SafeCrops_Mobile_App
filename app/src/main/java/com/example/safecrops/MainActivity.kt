package com.example.safecrops

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.graphics.drawable.AnimationDrawable
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.safecrops.databinding.ActivityMainBinding
import kotlinx.coroutines.delay

class MainActivity : AppCompatActivity() {
    //Declaración de variables que se usan en el proceso
    var t_username: EditText? = null //Reconoce el elemento campo del nombre de usuario
    var t_password: EditText? = null //Reconoce el elemento campo de la contraseña del usuario
    var b_login: Button? = null //Reconoce el elemento botón de inicio de sesión
    var username: String? = null //Almacena el nombre de usuario
    var password: String? = null //Almacena la contraseña del usuario
    var url = "https://pruebasfupem.000webhostapp.com/login_prueba2.php" //URL que conecta a la BD


    lateinit var binding: ActivityMainBinding

    //Variables de notificaciones
    private val CHANNEL_ID = "channel_id_sf"
    private val notificacionID = 113

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        crearCanalNotificacion()

        //Declaración de los elementos con los que interactúa el usuario desde la vista
        t_username = findViewById(R.id.txtUsername)
        t_password = findViewById(R.id.txtPassword)
        b_login = findViewById(R.id.btnLogin)

        //Llamado a las funciones de animaciones
        iniciaAnimacion()
        iniciaAnimacionBckg()

    }

    //Se declara para ir al registro sin usar un botón
    fun register(view: View?) {
        //Recibe un parámetro View para abrir la Activity de registro
        startActivity(Intent(applicationContext, registro::class.java))
        finish()
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

    }

    //Función que permite ir a la pantalla para subir una imagen de usuario
    fun img(view: View?) {
        //Recibe un parámetro View para abrir la Activity de subir imagen
        startActivity(Intent(applicationContext, pruebaSubirImg::class.java))
        finish()
    }

    fun calendario(view: View?){
        startActivity(Intent(applicationContext, pruebaPass::class.java))
        finish()
    }

    fun login(view: View?) {
        //Función que permite hacer el proceso del login del usuario, realizando una conexión a la base de datos
        //Recibe un parámetro de View para redirigir a una Activity nueva en caso de un inicio exitoso
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Cargando...")

        //Validaciones de llenado de campos
        if (t_username!!.text.toString() == "") {
            t_username!!.error = "Completa el campo"
            return
        } else if (t_password!!.text.toString() == "") {
            t_password!!.error = "Completa el campo"
            return
        } else { //En caso de haber llenado los campos correctamente
            progressDialog.show()

            //Extracción de los valores que escribe el usuario
            username = t_username!!.text.toString().trim { it <= ' ' }
            password = t_password!!.text.toString().trim { it <= ' ' }

            //Uso de StringRequest para conectarse a la BD con el método POST
            val request: StringRequest = object : StringRequest(
                Method.POST,
                url,
                Response.Listener<String> { response -> //En caso de conectar correctamente a la BD
                    progressDialog.dismiss()

                    //Cuando se hace la conexión a la BD, se redirige a la pantalla inicial de usuario Admin
                    if (response.equals("Ingreso correctamente - Admin", ignoreCase = true)) {

                        mandarNotificacion()

                        t_username!!.setText("")
                        t_password!!.setText("")

                        val intent = Intent(this@MainActivity, homeTester::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this@MainActivity, response, Toast.LENGTH_SHORT).show()
                    }


                    //Cuando se hace la conexión a la BD, se redirige a la pantalla inicial de usuario Tester
                    if (response.equals("Ingreso correctamente - Tester", ignoreCase = true)){

                        mandarNotificacion()

                        t_username!!.setText("")
                        t_password!!.setText("")

                        val intent = Intent(this@MainActivity, homeTester::class.java)
                        startActivity(intent)

                    } else {
                        Toast.makeText(this@MainActivity, "Registro no encontrado", Toast.LENGTH_SHORT).show()
                    }

                    //Cuando se hace la conexión a la BD, se redirige a la pantalla inicial de usuario Experto
                    if (response.equals("Ingreso correctamente - Experto", ignoreCase = true)){

                        mandarNotificacion()

                        t_username!!.setText("")
                        t_password!!.setText("")

                        val intent = Intent(this@MainActivity, homeTester::class.java)
                        startActivity(intent)

                    } else {
                        Toast.makeText(this@MainActivity, "Registro no encontrado", Toast.LENGTH_SHORT).show()
                    }

                },
                Response.ErrorListener { error ->

                    //En caso de haber fallo en el login
                    progressDialog.dismiss()
                    Toast.makeText(this@MainActivity, error.message.toString(), Toast.LENGTH_SHORT)
                        .show()
                }) {
                @Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String>? {
                    //Se realiza la conexión a la base de datos con sus campos
                    //Devuelve el valor de los parámetros que conectan los valores ingresados por el usuario
                    val params: MutableMap<String, String> = HashMap()
                    params["nomUsuario"] = username!!
                    params["password"] = password!!
                    return params
                }
            }
            //Se hace la solicitud a la BD
            val requestQueue = Volley.newRequestQueue(this@MainActivity)
            requestQueue.add(request)
        }
    }

    private fun iniciaAnimacion() {

        var fade_in = AnimationUtils.loadAnimation(this,R.anim.fade_in)
        var bottom_dowm = AnimationUtils.loadAnimation(this,R.anim.bottom_down)

            binding.imageView.animation = bottom_dowm
            binding.tituloSplash.animation = bottom_dowm


        var handler = Handler()
        var runnable = Runnable{
            binding.corne.animation = fade_in
        }

        handler.postDelayed(runnable, 1000)
    }

    private fun iniciaAnimacionBckg(){
        val constraintLayout = binding.MainLayout
        val animationDrawable = constraintLayout.background as AnimationDrawable
        animationDrawable.apply {
            setEnterFadeDuration(2500)
            setExitFadeDuration(5000)
            start()
        }
    }

    //FUNCIÓN QUE PERMITE CREAR EL CANAL DE NOTIFICACIONES
    private fun crearCanalNotificacion(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val titulo = "AVISO SOBRE INICIO DE SESIÓN"
            val desc = "Hola ¡Bienvenido a SafeCrops!"
            val importancia = NotificationManager.IMPORTANCE_DEFAULT
            val canal = NotificationChannel(CHANNEL_ID, titulo, importancia).apply {
                description = desc //Se asigna la descripcón a la notificación
            }

            val notificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(canal)

        }
    }

    //FUNCIÓN QUE PERMITE ENVIAR NOTIFICACIONES PERSONALIZADAS
    private fun mandarNotificacion(){

        username = t_username!!.text.toString().trim { it <= ' ' }

        val mensaje = "Hola $username, ¡Bienvenido a SafeCrops!"

        val bitmap = BitmapFactory.decodeResource(applicationContext.resources, R.drawable.fondohorizontal)
        val bitmap2 = BitmapFactory.decodeResource(applicationContext.resources, R.drawable.logosc)

        val builder = NotificationCompat.Builder(this,CHANNEL_ID)
            .setSmallIcon(R.drawable.icoleaf)
            .setContentTitle("INICIO DE SESIÓN") //Título de la notificacón
            .setContentText(mensaje) //Descripción de la notificación
            .setStyle(NotificationCompat.BigPictureStyle().bigPicture(bitmap)) //Agregar imagen larga a la notificación
            .setLargeIcon(bitmap2) //Agregar logo de la estética
            .setPriority(NotificationCompat.PRIORITY_DEFAULT) //Establecer prioridad de la notificación
            .setAutoCancel(true)

        with(NotificationManagerCompat.from(this)) {
            if (ActivityCompat.checkSelfPermission(
                    this@MainActivity,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
            notify(notificacionID,builder.build() )
        }


    }

    override fun onBackPressed() {} //Evita que el usuario regrese a una Activity equivocada

}
package com.example.safecrops

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import java.util.*


class pruebaCalendario : AppCompatActivity() {
    //private var editTextFecha: EditText? = null
    lateinit var editTextFecha: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prueba_calendario)
        editTextFecha = findViewById(R.id.editTextFecha)

        editTextFecha.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                validarFormatoFecha(s!!)
                editTextFecha.error = null // Limpia el error si el formato es correcto
            }
            override fun afterTextChanged(s: Editable) {
                validarFormatoFecha(s)
            }
        })

        editTextFecha.setOnClickListener(){
            mostrarDatePicker()
        }



    }

    private fun mostrarDatePicker() {
        val c = Calendar.getInstance()
        val year = c[Calendar.YEAR]
        val month = c[Calendar.MONTH]
        val day = c[Calendar.DAY_OF_MONTH]
        val datePickerDialog = DatePickerDialog(this, { view, year, monthOfYear, dayOfMonth ->
            val fecha = String.format("%04d/%02d/%02d", year, monthOfYear + 1, dayOfMonth)
            editTextFecha!!.setText(fecha)
        }, year, month, day)
        datePickerDialog.show()
    }

    private fun validarFormatoFecha(s: CharSequence) {
        val fecha = s.toString()
        val regex = """\\d{4}/\\d{2}/\\d{2}"""
        if (!fecha.matches(Regex(regex)) && fecha.isNotEmpty()) {
            editTextFecha.error = "Formato de fecha no válido (yyyy/mm/dd)"
        }else{
            editTextFecha.error = null
        }

    }


}
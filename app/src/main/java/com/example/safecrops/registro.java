package com.example.safecrops;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.shape.EdgeTreatment;
import com.google.android.material.textfield.TextInputLayout;

import java.util.HashMap;
import java.util.Map;

public class registro extends AppCompatActivity {

    EditText t_username, t_password;
    Button b_insertar;

    TextInputLayout textInputLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        t_username = findViewById(R.id.txtUsernameRg);
        t_password = findViewById(R.id.txtPasswordRg);
        b_insertar = findViewById(R.id.btnRegister);
        textInputLayout = findViewById(R.id.textInput);

        b_insertar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertarDatos();
            }
        });

    }

    public void login(View view){
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

    }

    private void insertarDatos() {
        final String username = t_username.getText().toString().trim();
        final String password = t_password.getText().toString().trim();

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Cargando...");

        if (password.length() < 8) {
            textInputLayout.setError("La contraseña debe tener al menos 8 caracteres");
            textInputLayout.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
            t_password.setHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.white)));
            t_password.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.white)));
            textInputLayout.setEndIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));

        } else {

            if (username.isEmpty()) {
            t_username.setError("Complete el campo");
            return;
        } else if (password.isEmpty()) {
            textInputLayout.setError("Complete el campo");
            return;
        } else {
            progressDialog.show();
            StringRequest request = new StringRequest(Request.Method.POST, "https://pruebasfupem.000webhostapp.com/insertar.php", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (response.equalsIgnoreCase("Registrado correctamente")) {
                        Toast.makeText(registro.this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();

                        //Envia al usuario a elegir qué tipo de usuario es
                        Intent intent = new Intent(registro.this, eligeUsuario.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(registro.this, response, Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        Toast.makeText(registro.this, "Error en el registro de datos", Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(registro.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }) {
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    Map<String, String> params = new HashMap<>();
                    params.put("nomUsuario", username);
                    params.put("password", password);

                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(registro.this);
            requestQueue.add(request); //Agrega todos los datos

        }
    }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

    }
}
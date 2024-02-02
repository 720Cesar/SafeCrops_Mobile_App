package com.example.safecrops;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class elige_subirFoto extends AppCompatActivity {

    private ImageButton btnTomarFoto;
    private ImageButton btnAbrirGaleria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elige_subir_foto);

        btnTomarFoto = findViewById(R.id.imgBtnCamara);
        btnAbrirGaleria = findViewById(R.id.imgBtnGaleria);

        btnTomarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                irTomaFoto();
            }
        });

        btnAbrirGaleria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                irAbrirGaleria();
            }
        });

    }


    private void irTomaFoto(){
        startActivity(new Intent(getApplicationContext(), tomarFoto.class));
        finish();
    }

    private void irAbrirGaleria(){
        startActivity(new Intent(getApplicationContext(), elegirFoto.class));
        finish();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), homeTester.class));
        finish();
    }
}
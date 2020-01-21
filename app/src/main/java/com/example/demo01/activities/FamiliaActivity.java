package com.example.demo01.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.demo01.R;

public class FamiliaActivity extends AppCompatActivity {

    Button mbtnPerfil, mbtnFamilia, mbtnActividad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_familia);

        mbtnActividad = (Button) findViewById(R. id.btnActividad);
        mbtnFamilia = (Button) findViewById(R. id.btnFamilia);
        mbtnPerfil = (Button) findViewById(R. id.btnPerfil);

        mbtnActividad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FamiliaActivity.this, InicioActivity.class));
                finish();
            }
        });

//        mbtnFamilia.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(FamiliaActivity.this, FamiliaActivity.class));
//                finish();
//            }
//        });

        mbtnPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FamiliaActivity.this, PerfilActivity.class));
                finish();
            }
        });
    }
}
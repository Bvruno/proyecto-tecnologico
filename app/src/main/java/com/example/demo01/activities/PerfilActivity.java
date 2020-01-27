package com.example.demo01.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.demo01.R;
import com.google.firebase.auth.FirebaseAuth;

public class PerfilActivity extends AppCompatActivity {

    Button mbtnCerrarSesion;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        mAuth = FirebaseAuth.getInstance();

        mbtnCerrarSesion = (Button) findViewById(R. id.btnCerrarSesion);

        ImageButton mbtnActividad = findViewById(R.id.btnActividad);
        ImageButton mbtnFamilia = findViewById(R.id.btnFamilia);
        ImageButton mbtnPerfil = findViewById(R.id.btnPerfil);

        mbtnCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    mAuth.signOut();
                    startActivity(new Intent(PerfilActivity.this, MainActivity.class));
                    finish();
                    Toast.makeText(PerfilActivity.this, "Hasta pronto!!", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(PerfilActivity.this, "Ocurrio un error", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });

        mbtnActividad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PerfilActivity.this, InicioActivity.class));
                finish();
            }
        });

        mbtnFamilia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PerfilActivity.this, FamiliaActivity.class));
                finish();
            }
        });

//        mbtnPerfil.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(PerfilActivity.this, PerfilActivity.class));
//                finish();
//            }
//        });
    }
}

package com.example.demo01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class InicioActivity extends AppCompatActivity {

    Button mbtnCerrar;

    Button mbtnPerfil, mbtnFamilia, mbtnActividad;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        mAuth = FirebaseAuth.getInstance();

        //mbtnCerrar = (Button)findViewById(R. id.btnCerrar);

        mbtnActividad = (Button) findViewById(R. id.btnActividad);
        mbtnFamilia = (Button) findViewById(R. id.btnFamilia);
        mbtnPerfil = (Button) findViewById(R. id.btnPerfil);

//        mbtnActividad.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(InicioActivity.this, InicioActivity.class));
//                finish();
//            }
//        });

        mbtnFamilia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(InicioActivity.this, FamiliaActivity.class));
                finish();
            }
        });

        mbtnPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(InicioActivity.this, PerfilActivity.class));
                finish();
            }
        });

//        mbtnCerrar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                try {
//                    mAuth.signOut();
//                    startActivity(new Intent(InicioActivity.this, MainActivity.class));
//                    finish();
//                    Toast.makeText(InicioActivity.this, "Hasta pronto!!", Toast.LENGTH_SHORT).show();
//                } catch (Exception e) {
//                    Toast.makeText(InicioActivity.this, "Ocurrio un error", Toast.LENGTH_SHORT).show();
//                    e.printStackTrace();
//                }
//            }
//        });
    }
}

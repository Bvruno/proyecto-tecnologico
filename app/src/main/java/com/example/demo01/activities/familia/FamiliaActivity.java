package com.example.demo01.activities.familia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.demo01.R;
import com.example.demo01.activities.InicioActivity;
import com.example.demo01.activities.perfil.PerfilActivity;

public class FamiliaActivity extends AppCompatActivity {

    ImageButton mbtnPerfil, mbtnFamilia, mbtnActividad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_familia);

        mbtnActividad = findViewById(R. id.btnActividad);
        mbtnFamilia = findViewById(R. id.btnFamilia);
        mbtnPerfil = findViewById(R. id.btnPerfil);

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

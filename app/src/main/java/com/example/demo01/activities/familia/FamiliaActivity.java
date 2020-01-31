package com.example.demo01.activities.familia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.demo01.R;
import com.example.demo01.activities.InicioActivity;
import com.example.demo01.activities.perfil.PerfilActivity;

public class FamiliaActivity extends AppCompatActivity {

    ImageButton mbtnPerfil, mbtnFamilia, mbtnActividad;
    Button mCrearFamilia, mUnirseFamilia;
    RecyclerView mrcvFamiliares;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_familia);

        mbtnActividad = findViewById(R. id.btnActividad);
        mbtnFamilia = findViewById(R. id.btnFamilia);
        mbtnPerfil = findViewById(R. id.btnPerfil);

        mrcvFamiliares = findViewById(R.id.rcvFamiliares);

        mCrearFamilia = findViewById(R.id.btnCrearFamilia);
        mUnirseFamilia = findViewById(R.id.btnUnirseFamilia);

        mbtnActividad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FamiliaActivity.this, InicioActivity.class));
                finish();
            }
        });

        mbtnPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FamiliaActivity.this, PerfilActivity.class));
                finish();
            }
        });

        mCrearFamilia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FamiliaActivity.this, CrearFamiliaActivity.class));
                finish();
            }
        });

        mUnirseFamilia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FamiliaActivity.this, UnirseAFamiliaActivity.class));
                finish();
            }
        });
    }
}

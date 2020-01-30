package com.example.demo01.activities.familia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;

import com.example.demo01.R;

public class UnirseAFamiliaActivity extends AppCompatActivity {

    SearchView mbuscarFamilia;
    RecyclerView mrcvFamilias;
    Button mbtnCancelar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unirse_afamilia);

        mbuscarFamilia = findViewById(R.id.scFamilias);
        mrcvFamilias = findViewById(R.id.rcvFamilias);
        mbtnCancelar = findViewById(R.id.btnCancelar);

        mbtnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelar();
            }
        });

    }

    private void cancelar(){
        startActivity(new Intent(UnirseAFamiliaActivity.this, FamiliaActivity.class));
        finish();
    }

}

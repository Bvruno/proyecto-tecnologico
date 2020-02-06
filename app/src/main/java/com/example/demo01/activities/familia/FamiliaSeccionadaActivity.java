package com.example.demo01.activities.familia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.demo01.R;

public class FamiliaSeccionadaActivity extends AppCompatActivity {

    Button mbtnCacelar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_familia_seccionada);

        mbtnCacelar = findViewById(R.id.btnCancelar);

        mbtnCacelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelar();
            }
        });
    }
    private void cancelar(){
        startActivity(new Intent(FamiliaSeccionadaActivity.this, UnirseAFamiliaActivity.class));
        finish();
    }
}

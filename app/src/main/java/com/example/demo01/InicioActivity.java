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

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        mAuth = FirebaseAuth.getInstance();

        mbtnCerrar = (Button)findViewById(R. id.btnCerrar);

        mbtnCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    mAuth.signOut();
                    startActivity(new Intent(InicioActivity.this, MainActivity.class));
                    finish();
                    Toast.makeText(InicioActivity.this, "Hasta pronto!!", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(InicioActivity.this, "Ocurrio un error", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });
    }
}

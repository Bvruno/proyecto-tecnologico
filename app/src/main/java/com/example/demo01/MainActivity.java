package com.example.demo01;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

     EditText mtxtUsuario, mtxtClave;
     TextView mtxtRecuperar;
     Button mbtnIngresar, mbtnCrear;

     String usuario = "";
     String clave = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mtxtUsuario = (EditText) findViewById(R. id.txtUsuario);
        mtxtClave = (EditText) findViewById(R. id.txtUsuario);
        mtxtRecuperar = (TextView) findViewById(R. id.txtRecuperar);

        mbtnIngresar = (Button) findViewById(R. id.btnIngresar);
        mbtnCrear = (Button) findViewById(R. id.btnCrearCuenta);

        mbtnIngresar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                usuario = mtxtUsuario.getText().toString();
                clave = mtxtClave.getText().toString();
            }
        });
    }
}

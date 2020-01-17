package com.example.demo01;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegistrarActivity extends AppCompatActivity {

    EditText mNombres, mApellidos, mEmail, mUsuario, mClave;
    Button mRegistrar;

    String nombres = "";
    String apellidos = "";
    String email = "";
    String usuario = "";
    String clave = "";

    Firebase

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);

        mNombres = (EditText)findViewById(R. id.txtNombres);
        mApellidos = (EditText)findViewById(R. id.txtApellidos);
        mEmail = (EditText)findViewById(R. id.txtEmail);
        mUsuario = (EditText)findViewById(R. id.txtUsuario);
        mClave = (EditText)findViewById(R. id.txtClave);
        mRegistrar = (Button)findViewById(R. id.btnRegistrar);

        mRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nombres = mNombres.getText().toString();
                apellidos = mApellidos.getText().toString();
                email = mEmail.getText().toString();
                usuario = mUsuario.getText().toString();
                clave = mClave.getText().toString();

                if(!nombres.isEmpty() && !apellidos.isEmpty() && !email.isEmpty() && !usuario.isEmpty() && !clave.isEmpty()){
                    if(clave.length() >= 6){
                        registrarUsuario();
                    } else {
                        Toast.makeText(RegistrarActivity.this, "la clave debe tener como minimo 6 digitos", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(RegistrarActivity.this, "Debe completar todos los campos", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    private void registrarUsuario(){}
}

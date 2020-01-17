package com.example.demo01;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

     EditText mtxtEmail, mtxtClave;
     TextView mtxtRecuperar;
     Button mbtnIngresar, mbtnCrear;

     String email = "";
     String clave = "";

    FirebaseAuth mAuth;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseApp.initializeApp(MainActivity.this);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        mtxtEmail = (EditText) findViewById(R. id.txtEmail);
        mtxtClave = (EditText) findViewById(R. id.txtClave);
        mtxtRecuperar = (TextView) findViewById(R. id.txtRecuperar);

        mbtnIngresar = (Button) findViewById(R. id.btnIngresar);
        mbtnCrear = (Button) findViewById(R. id.btnCrearCuenta);

        mbtnIngresar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                email = mtxtEmail.getText().toString();
                clave = mtxtClave.getText().toString();
                mAuth.signInWithEmailAndPassword(email,clave).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            startActivity(new Intent(MainActivity.this, InicioActivity.class));
                            finish();
                            Toast.makeText(MainActivity.this, "Bienvenido "+email, Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(MainActivity.this, "No se pudo iniciar sesion", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        mbtnCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent( MainActivity.this, RegistrarActivity.class);
                startActivity(i);
            }
        });
    }
}

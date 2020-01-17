package com.example.demo01;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegistrarActivity extends AppCompatActivity {

    EditText mNombres, mApellidos, mEmail, mUsuario, mClave;
    Button mRegistrar;

    String nombres = "";
    String apellidos = "";
    String email = "";
    String usuario = "";
    String clave = "";

    FirebaseAuth mAuth;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);

        FirebaseApp.initializeApp(RegistrarActivity.this);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        mNombres = (EditText)findViewById(R. id.txtNombres);
        mApellidos = (EditText)findViewById(R. id.txtApellidos);
        mEmail = (EditText)findViewById(R. id.txtEmail);
        mUsuario = (EditText)findViewById(R. id.txtEmail);
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
    private void registrarUsuario(){

        mAuth.createUserWithEmailAndPassword(email,clave).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    String id = mAuth.getCurrentUser().getUid();
                    Map<String, Object> user = new HashMap<>();
                    user.put("idPadre", id);
                    user.put("nombres", nombres);
                    user.put("apellidos", apellidos);
                    user.put("email", email);
                    user.put("clave", clave);

                    db.collection("usuarioPadre")
                            .add(user)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    //Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                                    startActivity(new Intent(RegistrarActivity.this, InicioActivity.class));
                                    finish();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    //Log.w(TAG, "Error adding document", e);
                                }
                            });
                }
                else{
                    Toast.makeText(RegistrarActivity.this, "No se pudo registrar este usuario, intentelo nuevamente", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}

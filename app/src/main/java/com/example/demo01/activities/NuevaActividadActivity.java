package com.example.demo01.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.demo01.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class NuevaActividadActivity extends AppCompatActivity {

    EditText mtxtNombre, mtxtActividad, mtxtRecompensa;
    Button mbtnCrear,btnAgregarFoto;
    ImageView mimgActivdad;

    String nombre = "";
    String actividad = "";
    String recompensa = "";

    FirebaseFirestore db;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_actividad);

        mtxtNombre = (EditText) findViewById(R. id.txtNombreUsuario);
        mtxtActividad = (EditText) findViewById(R. id.txtDetalleActividad);
        mtxtRecompensa = (EditText) findViewById(R. id.txtDetalleRecompensa);

        mbtnCrear = (Button) findViewById(R. id.btnCrearActivdad);
        btnAgregarFoto = findViewById(R.id.btnAgregarFoto);

        db = FirebaseFirestore.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();

        mbtnCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nombre = mtxtNombre.getText().toString();
                actividad = mtxtActividad.getText().toString();
                recompensa = mtxtRecompensa.getText().toString();

                Map<String, Object> data = new HashMap<>();
                data.put("nombre", nombre);
                data.put("actividad", actividad);
                data.put("recompensa", recompensa);
                data.put("fecha", new Timestamp(new Date()));

                if (user != null) {
                    String uid = user.getUid();

                    if(!nombre.isEmpty()){
                        if (!actividad.isEmpty()){
                            if (!recompensa.isEmpty()){
                                db.collection("usuario").document(uid+"").collection("actividades")
                                        .add(data)
                                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                            @Override
                                            public void onSuccess(DocumentReference documentReference) {
                                                Toast.makeText(NuevaActividadActivity.this, "Actividad creada.", Toast.LENGTH_SHORT).show();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(NuevaActividadActivity.this, "No se pudo crear la actividad.", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            } else {
                                Toast.makeText(NuevaActividadActivity.this, "Esta creando una actividad sin recompensa. "+uid, Toast.LENGTH_SHORT).show();
                                db.collection("usuarioPadre").document(uid+"").collection("actividades")
                                        .add(data)
                                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                            @Override
                                            public void onSuccess(DocumentReference documentReference) {
                                                Toast.makeText(NuevaActividadActivity.this, "Actividad creada.", Toast.LENGTH_SHORT).show();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(NuevaActividadActivity.this, "No se pudo crear la actividad.", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }
                        } else {
                            Toast.makeText(NuevaActividadActivity.this, "Ingrese una actividad primero.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(NuevaActividadActivity.this, "Debe poner un nombre a la actividad.", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            }
        });

        btnAgregarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NuevaActividadActivity.this,tomarFotoActivity.class));
            }
        });
    }
}

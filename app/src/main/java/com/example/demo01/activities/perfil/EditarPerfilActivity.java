package com.example.demo01.activities.perfil;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.demo01.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class EditarPerfilActivity extends AppCompatActivity {

    Button mCancelar, mGuardar;
    EditText mNombres, mAPaterno, mAMaterno, mNacimiento;

    FirebaseAuth mAuth;
    FirebaseUser user;
    FirebaseFirestore db;

    String nombres = "";
    String apaterno = "";
    String amaterno = "";
    String apellidos = apaterno + " " + amaterno;
    String email = "";
    String nacimiento = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil);

        mAuth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        db = FirebaseFirestore.getInstance();

        cargarDatos();

        mNombres = (EditText) findViewById(R.id.txtNombres);
        mAPaterno = (EditText) findViewById(R.id.txtAPaterno);
        mAMaterno = (EditText) findViewById(R.id.txtAMaterno);
        mNacimiento = (EditText) findViewById(R.id.txtNacimiento);

        mGuardar = (Button) findViewById(R.id.btnGuardar);
        mCancelar = (Button) findViewById(R.id.btnCancelar);

        mGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarDatos();
            }
        });

        mCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EditarPerfilActivity.this, PerfilActivity.class));
                finish();
            }
        });
    }

    private void guardarDatos(){
        String uid = user.getUid();

        Map<String, Object> data = new HashMap<>();
        //data.put("idPadre", id);
        data.put("nombres", mNombres.getText().toString());
        data.put("apellidoPaterno", mAPaterno.getText().toString());
        data.put("apellidoMaterno", mAMaterno.getText().toString());
        data.put("nacimiento",mNacimiento.getText().toString());
        //data.put("email", email);
        //data.put("clave", clave);
        //data.put("fechaCreacion", new Timestamp(new Date()));
        db.collection("usuarioPadre").document(uid+"")
                .set(data, SetOptions.merge())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        //Log.d(TAG, "DocumentSnapshot successfully updated!");
                        Toast.makeText(EditarPerfilActivity.this, "Guardado", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(EditarPerfilActivity.this, PerfilActivity.class));
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(EditarPerfilActivity.this, "No se pudo guardar", Toast.LENGTH_SHORT).show();
                        //Log.w(TAG, "Error updating document", e);
                    }
                });
    }

    private void cargarDatos(){
        try {
            if (user != null) {
                String uid = user.getUid();
                db.collection("usuarioPadre").document(uid+"").
                        get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()){

                            nombres = documentSnapshot.getString("nombres");
                            apaterno = documentSnapshot.getString("apellidoPaterno");
                            amaterno = documentSnapshot.getString("apellidoMaterno");
                            nacimiento = documentSnapshot.getString("fechaNacimiento");

                            //apellidos = apaterno + " " +amaterno;
                            mNombres.setText(nombres);
                            mAPaterno.setText(apaterno);
                            mAMaterno.setText(amaterno);
                            mNacimiento.setText(nacimiento);

                        } else {
                            Toast.makeText(EditarPerfilActivity.this, "Error"+documentSnapshot, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } else {
                Toast.makeText(this, "algo paso " + user, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e){
            Toast.makeText(this, "Error: "+e, Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
}

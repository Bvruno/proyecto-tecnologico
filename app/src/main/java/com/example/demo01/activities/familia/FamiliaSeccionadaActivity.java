package com.example.demo01.activities.familia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.demo01.R;
import com.example.demo01.activities.models.Familia;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class FamiliaSeccionadaActivity extends AppCompatActivity {

    TextView mnombre,mdescripcion,mclaveingresada;
    ImageView mimgfamilia;
    Button mbtnCancelar,mbtnUnirme;

    FirebaseAuth mAuth;
    FirebaseUser user;
    FirebaseFirestore db;
    FirebaseStorage storage;
    StorageReference storageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_familia_seccionada);

        mAuth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        db = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();

        mnombre = findViewById(R.id.txtNombreFamilia);
        mdescripcion = findViewById(R.id.txtDescripcionFamilia);
        mclaveingresada = findViewById(R.id.txtClaveIngresada);
        mimgfamilia = findViewById(R.id.imgFamilia);

        mbtnCancelar = findViewById(R.id.btnCancelar);
        mbtnUnirme = findViewById(R.id.btnUnirme);

        Bundle args = getIntent().getExtras();
        assert args != null;
        Familia familia = (Familia) args.getSerializable("familia");
        assert familia != null;
        mnombre.setText(familia.getNombre());
        mdescripcion.setText(familia.getDescripcion());
        
        storageRef.child("grupofamiliar/"+familia.getIdFamilia()+"/portada.jpg")
                .getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(FamiliaSeccionadaActivity.this).load(uri).into(mimgfamilia);
            }
        });

        final Familia finalFamilia = familia;

        mbtnUnirme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String claveFamilia = finalFamilia.getClave();
                String claveIngresada = mclaveingresada.getText().toString();

                if (claveFamilia.equals(claveIngresada)) {
                    Toast.makeText(FamiliaSeccionadaActivity.this, "Bienvenido al grupo", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(FamiliaSeccionadaActivity.this, "La clave no concuerda con la familia seleccionada", Toast.LENGTH_SHORT).show();
                }
            }
        });


        mbtnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelar();
            }
        });
    }

    private void cancelar(){
        startActivity(new Intent(FamiliaSeccionadaActivity.this, ListaFamiliasActivity.class));
        finish();
    }
}

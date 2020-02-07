package com.example.demo01.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.demo01.activities.models.Actividad;
import com.example.demo01.R;
import com.example.demo01.activities.familia.FamiliaActivity;
import com.example.demo01.activities.perfil.PerfilActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

public class InicioActivity extends AppCompatActivity {

     Button mbtnCrearActividad;
     ImageButton mbtnFamilia, mbtnPerfil, mbtnActividad;
     RecyclerView rv;

    List<Actividad> actividades;

     FirebaseAuth mAuth;
     FirebaseFirestore db;
     FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();

        mbtnCrearActividad = (Button) findViewById(R. id.btnCrearActivdad);

        mbtnActividad = findViewById(R. id.btnActividad);
        mbtnFamilia = findViewById(R. id.btnFamilia);
        mbtnPerfil = findViewById(R. id.btnPerfil);

        rv = (RecyclerView)findViewById(R. id.rvActividades);
        rv.setLayoutManager(new LinearLayoutManager(InicioActivity.this));

        actividades = new ArrayList<>();
        String uid = user.getUid();

        db.collection("usuario").document(uid+"").collection("actividades")
                //.whereEqualTo("state", "CA")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot snapshots,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            return;
                        }

                        for (DocumentChange dc : snapshots.getDocumentChanges()) {
                            switch (dc.getType()) {
                                case ADDED:
                                    dc.getDocument().getData();
                                    //Log.d(TAG, "New city: " + dc.getDocument().getData());
                                    break;
                            }
                        }

                    }
                });

        mbtnCrearActividad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(InicioActivity.this, NuevaActividadActivity.class));
                finish();
            }
        });

        mbtnFamilia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(InicioActivity.this, FamiliaActivity.class));
                finish();
            }
        });

        mbtnPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(InicioActivity.this, PerfilActivity.class));
                finish();
            }
        });

//        mbtnCerrar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                try {
//                    mAuth.signOut();
//                    startActivity(new Intent(InicioActivity.this, MainActivity.class));
//                    finish();
//                    Toast.makeText(InicioActivity.this, "Hasta pronto!!", Toast.LENGTH_SHORT).show();
//                } catch (Exception e) {
//                    Toast.makeText(InicioActivity.this, "Ocurrio un error", Toast.LENGTH_SHORT).show();
//                    e.printStackTrace();
//                }
//            }
//        });
    }
}

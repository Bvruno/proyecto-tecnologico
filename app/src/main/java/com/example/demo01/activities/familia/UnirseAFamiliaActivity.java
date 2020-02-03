package com.example.demo01.activities.familia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.demo01.R;
import com.example.demo01.activities.models.Familia;
import com.example.demo01.adapters.Adapter_familia;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UnirseAFamiliaActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;

    FirebaseAuth mAuth;
    FirebaseUser user;
    FirebaseFirestore db;
    FirebaseStorage storage;
    StorageReference storageRef;
    FirestoreRecyclerAdapter familiaAdapter;

    SearchView mbuscarFamilia;
    Button mbtnCancelar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unirse_afamilia);

        mAuth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        db = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();

        mRecyclerView = findViewById(R.id.rcvFamilias);

        mbuscarFamilia = findViewById(R.id.scFamilias);
        mbtnCancelar = findViewById(R.id.btnCancelar);

        mbtnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelar();
            }
        });

        Query query = db.collection("grupoFamiliar");

        FirestoreRecyclerOptions<Familia> familiaOptions = new FirestoreRecyclerOptions.Builder<Familia>()
                .setQuery(query, Familia.class)
                .build();

        familiaAdapter = new FirestoreRecyclerAdapter<Familia, FamiliaViewHolder>(familiaOptions) {
            @NonNull
            @Override
            public FamiliaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_familia_item, parent,false);
                return new FamiliaViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull FamiliaViewHolder familiaViewHolder, int i, @NonNull Familia familia) {
                familiaViewHolder.nombre_item.setText(familia.getNombre());
                familiaViewHolder.clave_item.setText(familia.getClave());
                familiaViewHolder.descripcion_item.setText(familia.getDescripcion());
                Picasso.get().load(familia.getImagenFamilia()).into((Target) familiaViewHolder.imagen_item);
            }
        };

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(familiaAdapter);

    }

    private class FamiliaViewHolder extends RecyclerView.ViewHolder{

        private TextView nombre_item, clave_item, descripcion_item, imagen_item;

        FamiliaViewHolder(@NonNull View itemView) {
            super(itemView);

            nombre_item = itemView.findViewById(R.id.txtNombreFamilia);
            clave_item = itemView.findViewById(R.id.btnUnirse);
            imagen_item = itemView.findViewById(R.id.imgFamilia);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        familiaAdapter.stopListening();
    }

    @Override
    protected void onStart() {
        super.onStart();
        familiaAdapter.startListening();
    }

    private void cancelar(){
        startActivity(new Intent(UnirseAFamiliaActivity.this, FamiliaActivity.class));
        finish();
    }

}

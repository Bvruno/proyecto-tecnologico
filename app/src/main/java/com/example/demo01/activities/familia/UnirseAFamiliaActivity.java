package com.example.demo01.activities.familia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;

import com.example.demo01.R;
import com.example.demo01.activities.models.Familia;
import com.example.demo01.adapters.Adapter_familia;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UnirseAFamiliaActivity extends AppCompatActivity {

    private List<Familia> familias;

    RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;

    FirebaseAuth mAuth;
    FirebaseUser user;
    FirebaseFirestore db;
    FirebaseStorage storage;
    StorageReference storageRef;

    private int counter = 0;

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
        cargarDatosFamilias();
        //familias = this.getAllFamilias();

        mRecyclerView = findViewById(R.id.rcvFamilias);
        mLayoutManager = new LinearLayoutManager(this);

        mAdapter = new Adapter_familia(familias, R.layout.recycler_familia_item, new Adapter_familia.OnItemClickListener() {
            @Override
            public void onItemClick(Familia familia, int position) {
                removeFamilia(position);
            }
        });

        mRecyclerView.setHasFixedSize(true);
        // Añade un efecto por defecto, si le pasamos null lo desactivamos por completo
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        // Enlazamos el layout manager y adaptor directamente al recycler view
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mbuscarFamilia = findViewById(R.id.scFamilias);
        mbtnCancelar = findViewById(R.id.btnCancelar);

        mbtnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelar();
            }
        });

    }

    private void cargarDatosFamilias(){

        familias = new ArrayList<>();
        db.collection("grupoFamiliar")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                                Log.d("RefFamilias", document.getId() + " => " + document.getData());
                                String nombre = document.getString("nombre");
                                String descripcion = document.getString("descripcion");
                                familias.add(new Familia(nombre,descripcion));
                                //Log.d("",familias.toString()+"");

                            }
                        } else {
                            Log.d("", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    private List<Familia> getAllFamilias(){
        return new ArrayList<Familia>(){{

        }};
    }

//    private void addFamilia(int position){
//        familias.add(position, new Familia("we"+(++counter), R.drawable.perfil_button));
//        mAdapter.notifyItemInserted(position);
//        mLayoutManager.scrollToPosition(position);
//    }

    private void removeFamilia(int position){
        familias.remove(position);
        mAdapter.notifyItemRemoved(position);
    }

    private void cancelar(){
        startActivity(new Intent(UnirseAFamiliaActivity.this, FamiliaActivity.class));
        finish();
    }

}

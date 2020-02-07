package com.example.demo01.activities.familia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.demo01.R;
import com.example.demo01.activities.models.Familia;
import com.example.demo01.activities.models.Miembro;
import com.example.demo01.activities.models.Usuario;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Objects;

public class MiGrupoFamiliarActivity extends AppCompatActivity {

    TextView mnombrefamilia, mdescripcion;
    ImageView mFamilia;
    RecyclerView mFamiliares;
    Button mvolver;

    FirebaseAuth mAuth;
    FirebaseUser user;
    FirebaseFirestore db;
    FirebaseStorage storage;
    StorageReference storageRef;
    FirestoreRecyclerAdapter miembroAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mi_grupo_familiar);

        mAuth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        db = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();

        mnombrefamilia = findViewById(R.id.txtNombreFamilia);
        mdescripcion = findViewById(R.id.txtDescripcion);
        mFamilia = findViewById(R.id.imgFamilia);
        mFamiliares = findViewById(R.id.rcvFamiliares);
        mvolver = findViewById(R.id.btnVolver);

        mvolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelar();
            }
        });

        Bundle args = getIntent().getExtras();
        assert args != null;
        Familia familia = (Familia) args.getSerializable("familia");
        assert familia != null;
        mnombrefamilia.setText(familia.getNombre());
        mdescripcion.setText(familia.getDescripcion());

        storageRef.child("grupofamiliar/"+familia.getIdFamilia()+"/portada.jpg")
                .getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(MiGrupoFamiliarActivity.this).load(uri).into(mFamilia);
            }
        });

        final ArrayList<Miembro> miembros = new ArrayList<Miembro>();
        final ArrayList<Usuario> usuarios = new ArrayList<Usuario>();

        CollectionReference docRef = db.collection("grupoFamiliar").document(familia.getIdFamilia()+"").collection("miembros");
        docRef.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                                Miembro miembro = document.toObject(Miembro.class);
                                miembros.add(miembro);
                            }
                        } else {
                            Log.d("", "Error getting documents: ", task.getException());
                        }
                        Toast.makeText(MiGrupoFamiliarActivity.this, miembros.get(0).getIdMiembro()+"", Toast.LENGTH_SHORT).show();
                    }
                });

        for (int i = 0;i < miembros.size(); i++) {
            String idUsurio = miembros.get(i).getIdMiembro();
            DocumentReference usuarioRef = db.collection("usuario").document(idUsurio+"");
            usuarioRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    Usuario usuario = documentSnapshot.toObject(Usuario.class);
                }
            });

        }


        Query query = db.collection("grupoFamiliar").document(familia.getIdFamilia()+"").collection("miembros");

        final FirestoreRecyclerOptions<Miembro> miembroOptions = new FirestoreRecyclerOptions.Builder<Miembro>()
                .setQuery(query, Miembro.class)
                .build();

        miembroAdapter = new FirestoreRecyclerAdapter<Miembro, MiembroViewHolder>(miembroOptions) {
            @NonNull
            @Override
            public MiembroViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_usuario_item, parent, false);
                return new MiembroViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull final MiembroViewHolder miembroViewHolder, final int i, @NonNull final Miembro miembro) {

                miembroViewHolder.nombre_item.setText(miembro.getFecha().toString());

                storageRef.child("grupofamiliar/" + miembro.getIdMiembro() + "/portada.jpg")
                        .getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Glide.with(MiGrupoFamiliarActivity.this).load(uri).into(miembroViewHolder.imagen_item);
                    }
                });

                miembroViewHolder.imagen_item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle args = new Bundle();

                        Intent intent = new Intent(MiGrupoFamiliarActivity.this, FamiliaSeccionadaActivity.class);

                        args.putSerializable("familia", miembro);
                        intent.putExtras(args);
                        startActivity(intent);
                    }
                });
            }

        };

    }


    private class MiembroViewHolder extends RecyclerView.ViewHolder{

    private TextView nombre_item;
    private ImageView imagen_item;

        MiembroViewHolder(@NonNull final View itemView) {
            super(itemView);

            nombre_item = itemView.findViewById(R.id.txtNombreFamilia);
            imagen_item = itemView.findViewById(R.id.imgFamilia);

        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        miembroAdapter.stopListening();
    }

    @Override
    protected void onStart() {
        super.onStart();
        miembroAdapter.startListening();
    }

    private void cancelar(){
        startActivity(new Intent(MiGrupoFamiliarActivity.this, FamiliaActivity.class));
        finish();
    }

}
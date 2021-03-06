package com.example.demo01.activities.familia;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.demo01.R;
import com.example.demo01.activities.dialog.ClaveDialog;
import com.example.demo01.activities.models.Familia;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class UnirseAFamiliaActivity extends AppCompatActivity implements ClaveDialog.ClaveDialogListener{

    RecyclerView mRecyclerView;

    FirebaseAuth mAuth;
    FirebaseUser user;
    FirebaseFirestore db;
    FirebaseStorage storage;
    StorageReference storageRef;
    FirestoreRecyclerAdapter familiaAdapter;

    SearchView mbuscarFamilia;
    Button mbtnCancelar;

    String mClaveDialog = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_familias);

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
            protected void onBindViewHolder(@NonNull final FamiliaViewHolder familiaViewHolder, final int i, @NonNull final Familia familia) {
                familiaViewHolder.nombre_item.setText(familia.getNombre());
                familiaViewHolder.clave_item.setText(familia.getClave());
                final String uidFamilia = familia.getIdFamilia();
                final String claveFamilia = familia.getClave();

                storageRef.child("grupofamiliar/"+familia.getIdFamilia()+"/portada.jpg")
                        .getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        final String uriFamilia = uri.toString();
                        Log.d("",uri+"");
                        Glide.with(UnirseAFamiliaActivity.this).load(uri).into(familiaViewHolder.imagen_item);
                    }
                });

                familiaViewHolder.imagen_item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle args = new Bundle();
                        Intent i = new Intent(UnirseAFamiliaActivity.this, FamiliaSeccionadaActivity.class);
                        i.putExtra("Familia",familia.getNombre());
                        startActivity(i);
                    }
                });
            }

        };

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(familiaAdapter);

    }

    private void openDialog(){
        ClaveDialog claveDialog = new ClaveDialog();
        claveDialog.show(getSupportFragmentManager(), "ClaveDialogo");
    }

    @Override
    public void onDialogAction(String clave, String claveRecivido) {
        mClaveDialog = clave ;


    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        Toast.makeText(this, "AQUIIIII", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }

    private class FamiliaViewHolder extends RecyclerView.ViewHolder{

        private TextView nombre_item, clave_item;
        private ImageView imagen_item;
        //private Button unirse_item;

        FamiliaViewHolder(@NonNull final View itemView) {
            super(itemView);

            nombre_item = itemView.findViewById(R.id.txtNombreFamilia);
            clave_item = itemView.findViewById(R.id.btnUnirse);
            imagen_item = itemView.findViewById(R.id.imgFamilia);
            //unirse_item = itemView.findViewById(R.id.btnUnirse);

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

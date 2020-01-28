package com.example.demo01.activities.perfil;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.demo01.R;
import com.example.demo01.activities.familia.FamiliaActivity;
import com.example.demo01.activities.InicioActivity;
import com.example.demo01.activities.MainActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class PerfilActivity extends AppCompatActivity {

    Button mbtnCerrarSesion, mbtnEditar;
    TextView mNombres, mApellidos, mEmail, mNacimiento;

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
        setContentView(R.layout.activity_perfil);

        mAuth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        db = FirebaseFirestore.getInstance();

        cargarDatos();
        mbtnCerrarSesion = (Button) findViewById(R. id.btnCerrarSesion);

        ImageButton mbtnActividad = findViewById(R.id.btnActividad);
        ImageButton mbtnFamilia = findViewById(R.id.btnFamilia);
        ImageButton mbtnPerfil = findViewById(R.id.btnPerfil);
        mbtnEditar = (Button) findViewById(R.id.btnEditarPerfil);

        mNombres = (TextView) findViewById(R.id.txtNombre);
        mApellidos = (TextView) findViewById(R.id.txtApellidoCompleto);
        mEmail = (TextView) findViewById(R.id.txtEmail);
        mNacimiento = (TextView) findViewById(R.id.txtNacimiento2);

        mbtnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    startActivity(new Intent(PerfilActivity.this, EditarPerfilActivity.class));
                    finish();
            }
        });

        mbtnCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    mAuth.signOut();
                    startActivity(new Intent(PerfilActivity.this, MainActivity.class));
                    finish();
                    Toast.makeText(PerfilActivity.this, "Hasta pronto!!", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(PerfilActivity.this, "Ocurrio un error", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });

        mbtnActividad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PerfilActivity.this, InicioActivity.class));
                finish();
            }
        });

        mbtnFamilia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PerfilActivity.this, FamiliaActivity.class));
                finish();
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
                            email = documentSnapshot.getString("email");
                            nacimiento = documentSnapshot.getString("nacimiento");

                            apellidos = apaterno + " " +amaterno;
                            mNombres.setText(nombres);
                            mApellidos.setText(apellidos);
                            mEmail.setText(email);
                            mNacimiento.setText(nacimiento);

                        } else {
                            Toast.makeText(PerfilActivity.this, "Error"+documentSnapshot, Toast.LENGTH_SHORT).show();
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

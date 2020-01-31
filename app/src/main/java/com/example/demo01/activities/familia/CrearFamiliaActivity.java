package com.example.demo01.activities.familia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.demo01.R;
import com.example.demo01.activities.perfil.PerfilActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CrearFamiliaActivity extends AppCompatActivity {

    EditText mNombreFamilia, mDescripcionFamilia, mClaveFamilia;
    ImageView mImgFamilia;
    Button mCrearFamilia, mCancelar;

    static final int PICK_IMAGE_REQUEST = 1;
    Uri filePath;

    FirebaseAuth mAuth;
    FirebaseUser user;
    FirebaseFirestore db;
    FirebaseStorage storage;
    StorageReference storageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_familia);

        mAuth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        db = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();

        mNombreFamilia = (EditText) findViewById(R.id.txtNombreFamilia);
        mDescripcionFamilia = (EditText) findViewById(R.id.txtDescripcionFamilia);
        mClaveFamilia = (EditText) findViewById(R.id.txtClaveFamiliar);

        mImgFamilia = (ImageView) findViewById(R.id.imgFamilia);

        mCrearFamilia = (Button) findViewById(R.id.btnCrearFamilia);
        mCancelar = (Button) findViewById(R.id.btnCancelar);

        mImgFamilia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    agregarImagen();
            }
        });

        mCrearFamilia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uid = user.getUid();

                String nombre = mNombreFamilia.getText().toString();
                String descripcion = mDescripcionFamilia.getText().toString();
                String clave = mClaveFamilia.getText().toString();

                Map<String, Object> data = new HashMap<>();
                data.put("nombre", nombre);
                data.put("descripcion", descripcion);
                data.put("clave",clave);
                data.put("idCreador", uid);

                if(!nombre.isEmpty() && !descripcion.isEmpty() && !clave.isEmpty()) {
                    crearFamilia(data);
                } else {
                    Toast.makeText(CrearFamiliaActivity.this, "Tiene que poner un Nombre, Descripcion y Clave a su Familia.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelar();
            }
        });
    }

    private void agregarImagen(){
        fotoGaleria();
    }

    private void crearFamilia(Map data){

        // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.

        final DocumentReference grupoFamiiaRef = db.collection("grupoFamiliar").document();
        final String uidFamilia = grupoFamiiaRef.getId();

        grupoFamiiaRef.set(data)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        mImgFamilia.setDrawingCacheEnabled(true);
                        mImgFamilia.buildDrawingCache();

                        Bitmap bitmap = ((BitmapDrawable) mImgFamilia.getDrawable()).getBitmap();
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 25, baos);

                        byte[] dataimg = baos.toByteArray();

                        StorageReference mountainsRef = storageRef.child("grupofamiliar/"+uidFamilia+"/portada.jpg");

                        UploadTask uploadTask = mountainsRef.putBytes(dataimg);
                        uploadTask.addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                // Handle unsuccessful uploads
                            }
                        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                agregarDatoFamilia(uidFamilia);

                            }
                        });

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(CrearFamiliaActivity.this, "No se pudo guardar", Toast.LENGTH_SHORT).show();
                        //Log.w(TAG, "Error updating document", e);
                    }
                });

    }

    private void cancelar(){
        startActivity(new Intent(CrearFamiliaActivity.this, FamiliaActivity.class));
        finish();
    }

    private void fotoGaleria(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Seleccionar Imagen"), PICK_IMAGE_REQUEST);
    }

    private void agregarDatoFamilia(final String uidFamilia){
        Map<String, Object> nestedData = new HashMap<>();
        nestedData.put("idFalimia", uidFamilia);

        final String uid = user.getUid();

        db.collection("usuarioPadre").document(uid+"")
                .set(nestedData, SetOptions.merge())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        //Log.d(TAG, "DocumentSnapshot successfully updated!");
                        agregarIntegranteFamilia(uidFamilia, uid);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(CrearFamiliaActivity.this, "No se pudo guardar", Toast.LENGTH_SHORT).show();
                        //Log.w(TAG, "Error updating document", e);
                    }
                });
    }

    private void agregarIntegranteFamilia(String uidFamilia, String uid){
        final DocumentReference grupoFamiiaRef = db.collection("grupoFamiliar").document(uidFamilia+"");
        final DocumentReference integranteFamiliaRef = grupoFamiiaRef.collection("miembros").document(uid+"");

        Map<String, Object> nestedData = new HashMap<>();
        nestedData.put("tipo","creador");
        nestedData.put("funcion","padre");
        nestedData.put("fecha", new Timestamp(new Date()));

        integranteFamiliaRef.set(nestedData, SetOptions.merge())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        //Log.d(TAG, "DocumentSnapshot successfully updated!");
                        Toast.makeText(CrearFamiliaActivity.this, "Familia Creada", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(CrearFamiliaActivity.this, PerfilActivity.class));
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(CrearFamiliaActivity.this, "No se pudo guardar", Toast.LENGTH_SHORT).show();
                        //Log.w(TAG, "Error updating document", e);
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                mImgFamilia.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            Toast.makeText(this, "Algo paso con la imagen, intentelo nuevamente.", Toast.LENGTH_SHORT).show();
        }
    }

}

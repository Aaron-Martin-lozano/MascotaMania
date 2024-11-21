package com.aaron.mascotamania;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AñadirMascotaActivity extends AppCompatActivity {

    private Button buttonVolver;
    private Button buttonRegistrarMascota;
    private EditText eNombreMascota;
    private EditText eEdadMascota;
    private EditText eEspecie;
    private FirebaseFirestore db;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_anadir_mascota);


        buttonVolver = findViewById(R.id.buttonVolver);
        buttonRegistrarMascota = findViewById(R.id.buttonGuardarPerfilMascota);
        eNombreMascota = findViewById(R.id.editTextNombreMascota);
        eEdadMascota = findViewById(R.id.editTextEdadMascota);
        eEspecie = findViewById(R.id.editTextEpecie);


        db = FirebaseFirestore.getInstance();

        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        String nombre =  user.getDisplayName();

        buttonRegistrarMascota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombreMascota = eNombreMascota.getText().toString().trim();
                int edadMascota = Integer.parseInt(eEdadMascota.getText().toString().trim());
                String especieMascota = eEspecie.getText().toString().trim();

                if(nombreMascota.isEmpty() &&especieMascota.isEmpty() && edadMascota == 0){
                    Toast.makeText(getApplicationContext(),"Se deben introducir los datos obligatorios", Toast.LENGTH_LONG).show();
                }else{
                    registarMascota(nombreMascota, edadMascota, especieMascota, nombre);
                }


            }
        });

        buttonVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AñadirMascotaActivity.this, TusMascotasActivity.class);
                startActivity(intent);
            }
        });
    }

    private void registarMascota(String nombre,int edad, String especie, String dueño){
        Map<String, Object> mascota = new HashMap<>();
        mascota.put("nombre", nombre);
        mascota.put("edad", edad);
        mascota.put("especie", especie);
        mascota.put("dueño", dueño);


        db.collection("mascotas").add(mascota)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        // Aquí obtenemos el ID generado por Firebase
                        String id = documentReference.getId();

                        // Ahora actualizamos el documento para agregar el ID dentro de los datos
                        db.collection("mascotas")
                                .document(id)
                                .update("id", id) // Actualizamos el campo "id" con el ID de Firestore
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        // Después de actualizar, puedes redirigir o realizar otras acciones
                                        finish();
                                        startActivity(new Intent(AñadirMascotaActivity.this, TusMascotasActivity.class));
                                        Toast.makeText(getApplicationContext(), "Perfil de mascota guardado correctamente", Toast.LENGTH_LONG).show();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(getApplicationContext(), "Error al añadir a la mascota", Toast.LENGTH_LONG).show();
                                    }
                                });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Error al ingresar los datos, intentalo de nuevo", Toast.LENGTH_LONG).show();
                    }
                });

    }
}
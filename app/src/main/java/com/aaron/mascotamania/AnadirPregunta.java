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

public class AnadirPregunta extends AppCompatActivity {

    private EditText ePregunta;
    private Button aceptarPregunta;
    private Button botonVolver;
    private FirebaseFirestore db;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_anadir_pregunta);


        ePregunta = findViewById(R.id.editTextAñadirPregunta);
        aceptarPregunta = findViewById(R.id.buttonAceptar);
        botonVolver = findViewById(R.id.buttonVolver);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        String nombre =  user.getDisplayName();


        aceptarPregunta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pregunta = ePregunta.getText().toString().trim();

                if(pregunta.isEmpty()) {
                    Toast.makeText(getApplicationContext(),"El campo pregunta esta vacio", Toast.LENGTH_LONG).show();
                }else {
                    añadirPregunta(nombre, pregunta);
                }
            }
        });


        botonVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AnadirPregunta.this, ComunidadActivity.class);
                startActivity(intent);
            }
        });
    }

    private void añadirPregunta(String usuario, String pregunta){
        Map<String, Object> preguntas = new HashMap<>();
        preguntas.put("usuario", usuario);
        preguntas.put("pregunta", pregunta);

        db.collection("comunidad").add(preguntas).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                String id = documentReference.getId();

            db.collection("comunidad").document(id).update("id", id).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    finish();
                    startActivity(new Intent(AnadirPregunta.this, ComunidadActivity.class));
                    Toast.makeText(getApplicationContext(), "Pregunta añadida correctamente", Toast.LENGTH_LONG).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(), "Error al añadir la pregunta", Toast.LENGTH_LONG).show();
                }
            });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Error al ingresar los datos, intentalo de nuevo", Toast.LENGTH_LONG).show();
            }
        });
    }

}
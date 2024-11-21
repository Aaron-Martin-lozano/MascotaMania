package com.aaron.mascotamania;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.aaron.mascotamania.adapter.ComunidadAdapter;
import com.aaron.mascotamania.model.Comunidad;
import com.example.myapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ComunidadActivity extends AppCompatActivity {

    private FirebaseFirestore db;
    private Button botonAgregarPregunta;
    private Button buttonVolver;
    private ListView lComunidad;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_comunidad);


        botonAgregarPregunta = findViewById(R.id.buttonAgregarPregunta);
        buttonVolver = findViewById(R.id.buttonVolver);
        db = FirebaseFirestore.getInstance();
        lComunidad = findViewById(R.id.listViewForo);
        ArrayList<Comunidad> listaComunidad = new ArrayList<>();
        ComunidadAdapter adapter = new ComunidadAdapter(this, listaComunidad);

        lComunidad.setAdapter(adapter);

        db.collection("comunidad")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                Comunidad comunidad = document.toObject(Comunidad.class);
                                listaComunidad.add(comunidad);

                            }
                            adapter.notifyDataSetChanged();
                        } else {
                            Log.d("FirestoreError", "Error obteniendo los datos: ", task.getException());
                        }
                    }
                });

        buttonVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ComunidadActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        botonAgregarPregunta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ComunidadActivity.this, AnadirPregunta.class);
                startActivity(intent);
            }
        });

    }
}
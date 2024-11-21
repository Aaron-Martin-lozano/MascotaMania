package com.aaron.mascotamania;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.aaron.mascotamania.adapter.TusMascotasAdapter;
import com.example.myapplication.R;
import com.aaron.mascotamania.model.Mascotas;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class TusMascotasActivity extends AppCompatActivity {

    private Button buttonVolver;
    private Button buttonAñadirMascota;
    private ListView lTusMascotas;
    private TextView tTusMascotas;
    private FirebaseFirestore db;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tus_mascotas);


        buttonVolver = findViewById(R.id.buttonVolver);
        buttonAñadirMascota = findViewById(R.id.buttonAñadirMAscotas);
        lTusMascotas = findViewById(R.id.listViewTusMascotas);
        tTusMascotas = findViewById(R.id.textViewTusMascotas);

        db = FirebaseFirestore.getInstance();
        ArrayList<Mascotas> listaMascotas = new ArrayList<>();
        TusMascotasAdapter adapter = new TusMascotasAdapter(this, listaMascotas);

        lTusMascotas.setAdapter(adapter);

        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        String nombre =  user.getDisplayName();

        tTusMascotas.setText("Las mascotas de " + nombre);

        db.collection("mascotas")
                .whereEqualTo("dueño", nombre)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                Mascotas mascotas = document.toObject(Mascotas.class);
                                mascotas.setId(document.getId());
                                listaMascotas.add(mascotas);

                            }
                            adapter.notifyDataSetChanged();
                        } else {
                            Log.d("FirestoreError", "Error obteniendo los datos: ", task.getException());
                        }
                    }
                });

        buttonAñadirMascota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TusMascotasActivity.this, AñadirMascotaActivity.class);
                startActivity(intent);
            }
        });

        buttonVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TusMascotasActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
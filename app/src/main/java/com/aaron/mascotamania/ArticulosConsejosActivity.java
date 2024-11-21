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


import com.aaron.mascotamania.adapter.ArticulosAdapter;
import com.example.myapplication.R;
import com.aaron.mascotamania.model.Articulos;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ArticulosConsejosActivity extends AppCompatActivity {

    private FirebaseFirestore db;
    private Button buttonVolver;
    private ListView lArticulos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_articulos_consejos);


        buttonVolver = findViewById(R.id.buttonVolver);
        db = FirebaseFirestore.getInstance();
        lArticulos = findViewById(R.id.listViewArticulos);
        ArrayList<Articulos> listaArticulos = new ArrayList<>();
        ArticulosAdapter adapter = new ArticulosAdapter(this, listaArticulos);

        lArticulos.setAdapter(adapter);

        db.collection("articulos")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                Articulos articulo = document.toObject(Articulos.class);
                                listaArticulos.add(articulo);

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
                Intent intent = new Intent(ArticulosConsejosActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
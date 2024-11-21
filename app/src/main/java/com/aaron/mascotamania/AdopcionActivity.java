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

import com.aaron.mascotamania.model.Adopcion;
import com.example.myapplication.R;
import com.aaron.mascotamania.adapter.AdopcionMascotaAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class AdopcionActivity extends AppCompatActivity {

    private FirebaseFirestore db;
    private Button buttonVolver;
    private ListView lAdopcion;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_adopcion);


        buttonVolver = findViewById(R.id.buttonVolver);
        db = FirebaseFirestore.getInstance();
        lAdopcion = findViewById(R.id.listViewAdopcion);
        ArrayList<Adopcion> listaAdopcion = new ArrayList<>();
        AdopcionMascotaAdapter adapter = new AdopcionMascotaAdapter(this, listaAdopcion);

        lAdopcion.setAdapter(adapter);

        db.collection("adopcion")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                Adopcion adopcion = document.toObject(Adopcion.class);
                                listaAdopcion.add(adopcion);

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
                Intent intent = new Intent(AdopcionActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }


}


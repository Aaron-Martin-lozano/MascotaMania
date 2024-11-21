package com.aaron.mascotamania;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class EditarMascotaActivity extends AppCompatActivity {

    private Button buttonVolver;
    private Button actualizar;
    private EditText eNombre;
    private EditText eEdad;
    private EditText eEspecie;
    private FirebaseFirestore db;
    String mascotaId;
    String dueño;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_editar_mascota);


        buttonVolver = findViewById(R.id.buttonVolver);
        actualizar =  findViewById(R.id.buttonActualizarMascota);
        eNombre = findViewById(R.id.editTextNombreMascotaActualizar);
        eEdad = findViewById(R.id.editTextEdadMascotaActualizar);
        eEspecie = findViewById(R.id.editTextEpecieActualizar);
        db = FirebaseFirestore.getInstance();


        Intent intent = getIntent();
        mascotaId = intent.getStringExtra("id");
        dueño = intent.getStringExtra("dueño");
        String nombre = intent.getStringExtra("nombre");
        int edad = intent.getIntExtra("edad", 0);
        String especie = intent.getStringExtra("especie");



        eNombre.setText(nombre);
        eEdad.setText(String.valueOf(edad));
        eEspecie.setText(especie);

        actualizar.setOnClickListener(view -> actualizarMascota());



        buttonVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditarMascotaActivity.this, TusMascotasActivity.class);
                startActivity(intent);
            }
        });
    }

    private void actualizarMascota() {
        String nuevoNombre = eNombre.getText().toString().trim();
        int nuevaEdad = Integer.parseInt(eEdad.getText().toString().trim());
        String nuevaEspecie = eEspecie.getText().toString().trim();


        Map<String, Object> mascotaActualizada = new HashMap<>();
        mascotaActualizada.put("nombre", nuevoNombre);
        mascotaActualizada.put("edad", nuevaEdad);
        mascotaActualizada.put("especie", nuevaEspecie);
        mascotaActualizada.put("dueño", dueño);

        db.collection("mascotas").document(mascotaId).update(mascotaActualizada)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(getApplicationContext(), "Mascota actualizada correctamente", Toast.LENGTH_SHORT).show();
                    finish();
                    startActivity(new Intent(EditarMascotaActivity.this, TusMascotasActivity.class));
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getApplicationContext(), "Error al actualizar la mascota", Toast.LENGTH_SHORT).show();
                });
    }
}
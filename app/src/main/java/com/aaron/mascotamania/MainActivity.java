package com.aaron.mascotamania;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private Button buttonTusMascotas;
    private Button buttonComunidad;
    private Button buttonArticulosConsejos;
    private Button buttonAdopcion;
    private Button buttonCerrarSesion;
    private TextView textoBienvenida;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);



        buttonTusMascotas = findViewById(R.id.buttonPerfilMascota);
        buttonComunidad = findViewById(R.id.buttonComunidad);
        buttonArticulosConsejos = findViewById(R.id.buttonArticulosConsejos);
        buttonAdopcion = findViewById(R.id.buttonAdopcion);
        buttonCerrarSesion = findViewById(R.id.buttonCerrarSesion);
        textoBienvenida = findViewById(R.id.textViewBienvenida);

        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        String nombre =  user.getDisplayName();

        textoBienvenida.setText("hola " + nombre + " bienvenido a MascotaMania");


        buttonTusMascotas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TusMascotasActivity.class);
                startActivity(intent);
            }
        });


        buttonComunidad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ComunidadActivity.class);
                startActivity(intent);
            }
        });


        buttonArticulosConsejos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ArticulosConsejosActivity.class);
                startActivity(intent);
            }
        });


        buttonAdopcion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AdopcionActivity.class);
                startActivity(intent);
            }
        });


        buttonCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                finish();
                Intent intent = new Intent(MainActivity.this, InicioSesionActivity.class);
                startActivity(intent);
            }
        });


    }
}
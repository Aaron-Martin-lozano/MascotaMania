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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class InicioSesionActivity extends AppCompatActivity {

    private Button buttonRegistartse;
    private Button buttonIniciarSesion;
    private EditText eCorreoUsuario;
    private EditText eContraseña;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_inicio_sesion);


        buttonRegistartse = findViewById(R.id.buttonRegistrar);
        buttonIniciarSesion = findViewById(R.id.buttonIniciarSesion);
        eCorreoUsuario = findViewById(R.id.editTextUsuario);
        eContraseña = findViewById(R.id.editTextContraseña);
        auth = FirebaseAuth.getInstance();

        buttonRegistartse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InicioSesionActivity.this, RegistrarUsuarioActivity.class);
                startActivity(intent);
            }
        });

        buttonIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String correoUsuario = eCorreoUsuario.getText().toString().trim();
                String contraseñausuario = eContraseña.getText().toString().trim();

                if(correoUsuario.isEmpty() && contraseñausuario.isEmpty()) {
                    Toast.makeText(getApplicationContext(),"Ingresar los datos", Toast.LENGTH_LONG).show();
                } else {
                    iniciarSesion(correoUsuario, contraseñausuario);
                }


            }
        });

    }

    private void iniciarSesion(String correoUsuario, String contraseñausuario) {
        auth.signInWithEmailAndPassword(correoUsuario, contraseñausuario).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()) {
                    finish();
                    startActivity(new Intent(InicioSesionActivity.this, MainActivity.class));
                    Toast.makeText(getApplicationContext(),"Bienvenido", Toast.LENGTH_LONG).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"Error al ingresar", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = auth.getCurrentUser();
        if(user != null) {
            startActivity(new Intent(InicioSesionActivity.this, MainActivity.class));
            finish();
        }
    }
}
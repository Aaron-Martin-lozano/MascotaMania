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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegistrarUsuarioActivity extends AppCompatActivity {

    private Button buttonVolver;
    private Button buttontRegistrarUsuario;
    private EditText eUsuario;
    private EditText eCorreo;
    private EditText eContraseña;
    private FirebaseFirestore db;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registrar_usuario);


        buttonVolver = findViewById(R.id.buttonVolver);
        buttontRegistrarUsuario = findViewById(R.id.buttonRegistarse);
        eUsuario = findViewById(R.id.editTextUsuario);
        eCorreo = findViewById(R.id.editTextCorreo);
        eContraseña =findViewById(R.id.editTextContraseña);
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();


        buttonVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegistrarUsuarioActivity.this, InicioSesionActivity.class);
                startActivity(intent);
            }
        });

        buttontRegistrarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = eUsuario.getText().toString().trim();
                String correo = eCorreo.getText().toString().trim();
                String contraseña = eContraseña.getText().toString().trim();

                if(user.isEmpty() && correo.isEmpty() && contraseña.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Ingresar los datos", Toast.LENGTH_LONG).show();
                }else{
                    registarUsuario(user, correo, contraseña);
                }


            }
        });

    }


    private void registarUsuario(String nombreUser, String correoUser, String contraseñaUser) {
        auth.createUserWithEmailAndPassword(correoUser, contraseñaUser)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Obtenemod el usuario recién creado
                            FirebaseUser user = auth.getCurrentUser();
                            if (user != null) {
                                String id = user.getUid();


                                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                        .setDisplayName(nombreUser)
                                        .build();


                                user.updateProfile(profileUpdates)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {

                                                    Map<String, Object> usuario = new HashMap<>();
                                                    usuario.put("id", id);
                                                    usuario.put("usuario", nombreUser);
                                                    usuario.put("correo", correoUser);
                                                    usuario.put("contraseña", contraseñaUser);


                                                    db.collection("usuarios").document(id).set(usuario)
                                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                @Override
                                                                public void onSuccess(Void unused) {
                                                                    Toast.makeText(getApplicationContext(), "Usuario registrado exitosamente", Toast.LENGTH_LONG).show();
                                                                    finish();
                                                                }
                                                            })
                                                            .addOnFailureListener(new OnFailureListener() {
                                                                @Override
                                                                public void onFailure(@NonNull Exception e) {
                                                                    Toast.makeText(getApplicationContext(), "Error al guardar en Firestore", Toast.LENGTH_LONG).show();
                                                                }
                                                            });
                                                } else {
                                                    Toast.makeText(getApplicationContext(), "Error al actualizar displayName", Toast.LENGTH_LONG).show();
                                                }
                                            }
                                        });
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "Error al crear usuario", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}
package com.aaron.mascotamania.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.aaron.mascotamania.EditarMascotaActivity;
import com.example.myapplication.R;
import com.aaron.mascotamania.model.Mascotas;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class TusMascotasAdapter extends ArrayAdapter<Mascotas> {

    private TextView tNombre;
    private TextView tEdad;
    private TextView tEspecie;
    private TextView tDueño;
    private Button editar;
    private Button borrar;
    private FirebaseFirestore db;

    public TusMascotasAdapter(@NonNull Context context, ArrayList<Mascotas> mascotas) {
        super(context, 0, mascotas);
        db = FirebaseFirestore.getInstance();
    }

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.view_tus_mascotas, parent, false);
        }

        Mascotas mascota = getItem(position);

        tNombre = convertView.findViewById(R.id.tvNombre);
        tEdad = convertView.findViewById(R.id.tvEdad);
        tEspecie = convertView.findViewById(R.id.tvEspecie);
        tDueño = convertView.findViewById(R.id.tvDueño);
        editar = convertView.findViewById(R.id.btnEditar);
        borrar = convertView.findViewById(R.id.btnBorrar);



        if(mascota != null) {

            tNombre.setText(mascota.getNombre());
            tEdad.setText(String.valueOf(mascota.getEdad()));
            tEspecie.setText(mascota.getEspecie());
            tDueño.setText(mascota.getDueño());
        }


        editar.setOnClickListener(view -> {
            Intent intent = new Intent(getContext(), EditarMascotaActivity.class);
            intent.putExtra("id", mascota.getId());
            intent.putExtra("nombre", mascota.getNombre());
            intent.putExtra("edad", mascota.getEdad());
            intent.putExtra("especie", mascota.getEspecie());
            intent.putExtra("dueño", mascota.getDueño());
            getContext().startActivity(intent);
        });




        borrar.setOnClickListener(v -> {

            if (mascota != null) {
                String mascotaId = mascota.getId();


                db.collection("mascotas").document(mascotaId)
                        .delete()
                        .addOnSuccessListener(aVoid -> {

                            remove(mascota);
                            notifyDataSetChanged();
                            Toast.makeText(getContext(), "Mascota eliminada", Toast.LENGTH_SHORT).show();
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(getContext(), "Error al eliminar la mascota", Toast.LENGTH_SHORT).show();
                        });
            }
        });



        return convertView;
    }
}

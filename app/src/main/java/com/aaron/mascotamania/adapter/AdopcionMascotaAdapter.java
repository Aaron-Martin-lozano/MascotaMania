package com.aaron.mascotamania.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.aaron.mascotamania.model.Adopcion;
import com.bumptech.glide.Glide;
import com.example.myapplication.R;

import java.util.ArrayList;

public class AdopcionMascotaAdapter extends ArrayAdapter<Adopcion> {

    public AdopcionMascotaAdapter(@NonNull Context context, ArrayList<Adopcion> adopcion) {
        super(context, 0, adopcion);
    }

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.view_adopcion, parent, false);
        }

        Adopcion adopcion = getItem(position);

        TextView tvNombre = convertView.findViewById(R.id.nombreTextView);
        TextView tvEdad = convertView.findViewById(R.id.edadTextView);
        TextView tvEspecie = convertView.findViewById(R.id.especieTextView);
        TextView tvEstado = convertView.findViewById(R.id.estadoTextView);
        ImageView imagen = convertView.findViewById(R.id.imageViewMascota);


        if (adopcion != null) {
            tvNombre.setText(adopcion.getNombre());
            tvEdad.setText("Edad: " + adopcion.getEdad());
            tvEspecie.setText("Especie: " + adopcion.getEspecie());
            tvEstado.setText("Estado: " + adopcion.getEstado());

            Glide.with(getContext()).load(adopcion.getImagenURL()).placeholder(R.drawable.image_placeholder).into(imagen);
        }

        return convertView;
    }
}

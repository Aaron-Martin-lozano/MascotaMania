package com.aaron.mascotamania.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.R;
import com.aaron.mascotamania.model.Comunidad;

import java.util.ArrayList;

public class ComunidadAdapter extends ArrayAdapter<Comunidad> {
    public ComunidadAdapter(@NonNull Context context, ArrayList<Comunidad> comunidad) {
        super(context, 0, comunidad);
    }

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.view_comunidad, parent, false);
        }

        Comunidad comunidad = getItem(position);

        TextView tvUsuario = convertView.findViewById(R.id.nombreUsuarioTextView);
        TextView tvPregunta = convertView.findViewById(R.id.preguntaTextView);

        if(comunidad != null) {
            tvUsuario.setText(comunidad.getUsuario());
            tvPregunta.setText(comunidad.getPregunta());
        }

        return convertView;
    }
}

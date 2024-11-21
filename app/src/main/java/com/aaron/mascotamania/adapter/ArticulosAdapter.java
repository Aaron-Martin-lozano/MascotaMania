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
import com.aaron.mascotamania.model.Articulos;

import java.util.ArrayList;

public class ArticulosAdapter extends ArrayAdapter<Articulos> {


    public ArticulosAdapter(@NonNull Context context, ArrayList<Articulos> articulos) {
        super(context, 0, articulos);
    }

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.view_articulos, parent, false);
        }

        Articulos articulos = getItem(position);

        TextView tvAutor = convertView.findViewById(R.id.autorTextView);
        TextView tvTitulo = convertView.findViewById(R.id.tituloTextView);

        if (articulos != null){
            tvAutor.setText(articulos.getAutor());
            tvTitulo.setText(articulos.getTitulo());
        }

        return convertView;

    }

}

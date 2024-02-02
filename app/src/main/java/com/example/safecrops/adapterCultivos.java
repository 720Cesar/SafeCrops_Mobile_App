package com.example.safecrops;

import android.content.Context;
import android.icu.text.Transliterator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class adapterCultivos extends ArrayAdapter<Cultivos> {

    Context context;
    List<Cultivos> arrayCultivos;

    public adapterCultivos(@NonNull Context context, List<Cultivos>arrayCultivos) {
        super(context, R.layout.list_cultivos,arrayCultivos);
        this.context = context;
        this.arrayCultivos = arrayCultivos;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_cultivos, null, true);

        //TextView t_id = view.findViewById(R.id.txtID);
        TextView t_nombre = view.findViewById(R.id.txtNombre);

        //t_nombre.setText(arrayCultivos.get(position).getNombreCultivos());
        t_nombre.setText(arrayCultivos.get(position).getNombreCultivos());

        return view;
    }
}

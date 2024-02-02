package com.example.safecrops;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class adapterPlagas extends ArrayAdapter<Plagas> {

    Context context;
    List<Plagas> arrayPlagas;

    public adapterPlagas(@NonNull Context context, List<Plagas>arrayPlagas){
        super(context, R.layout.list_plagas,arrayPlagas);
        this.context = context;
        this.arrayPlagas = arrayPlagas;
    }

    @Nullable
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_plagas, null, true);
        //TextView t_id = view.findViewById(R.id.txtID);
        TextView t_nombre = view.findViewById(R.id.txtNombre);

        //t_nombre.setText(arrayCultivos.get(position).getNombreCultivos());
        t_nombre.setText(arrayPlagas.get(position).getNombreEnfermedad());

        return view;
    }

}

package com.example.demo01.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demo01.Actividad;
import com.example.demo01.R;

import java.util.List;

public class Adapter_actividad extends RecyclerView.Adapter<Adapter_actividad.ActividadAdapter> {

    List<Actividad> actividades;

    public Adapter_actividad(List<Actividad> actividades) {
        this.actividades = actividades;
    }

    @NonNull
    @Override
    public ActividadAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_activadad, parent, false);
        ActividadAdapter holder = new ActividadAdapter(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ActividadAdapter holder, int position) {
        Actividad actividad = actividades.get(position);
        holder.mtxtNombre.setText(actividad.getNombre());
        //holder.mimgImagen.setImageBitmap(actividad.getImgActividad());

    }

    @Override
    public int getItemCount() {
        return actividades.size();
    }

    public static class ActividadAdapter extends RecyclerView.ViewHolder {

        TextView mtxtNombre;
        ImageView mimgImagen;

        public ActividadAdapter(View itemView){
            super(itemView);
            mtxtNombre = itemView.findViewById(R. id.txtNombreActividad);
            mimgImagen = itemView.findViewById(R. id.imgActividad);
        }
    }
}

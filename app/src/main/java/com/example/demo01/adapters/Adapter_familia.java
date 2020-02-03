package com.example.demo01.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.demo01.R;
import com.example.demo01.activities.familia.FamiliaActivity;
import com.example.demo01.activities.models.Familia;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Adapter_familia extends RecyclerView.Adapter<Adapter_familia.ViewHolder> {

    private List<Familia> familias;
    private int layout;
    private OnItemClickListener itemClickListener;

    private Context context;

    public Adapter_familia(List<Familia> familias, int layout, OnItemClickListener listener) {
        this.familias = familias;
        this.layout = layout;
        this.itemClickListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(layout,parent,false);
        context = parent.getContext();
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(familias.get(position), itemClickListener);
    }

    @Override
    public int getItemCount() {
        return familias.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

         TextView mnombreFamilia;
         ImageView mImgFamilia;
         Button mbtnUnirse;

        public ViewHolder(View v) {
            super(v);
            this.mnombreFamilia = (TextView) v.findViewById(R.id.txtNombreFamilia);
            this.mImgFamilia = (ImageView) v.findViewById(R.id.imgFamilia);
            this.mbtnUnirse = (Button) v.findViewById(R.id.btnUnirse);
        }

        public void bind(final Familia familia, final OnItemClickListener listener){

            this.mnombreFamilia.setText(familia.getNombre());
            //Picasso.get().load(familia.getImagenFamilia()).fit().into(mImgFamilia);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(familia, getAdapterPosition());
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Familia familia, int position);
    }

}

package com.carlosbaquero.notasapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.carlosbaquero.notasapp.models.itemRubrica;

import java.util.ArrayList;

public class AdapterItemsRub extends RecyclerView.Adapter<AdapterItemsRub.ViewHolderDatos> {
    @NonNull
    ArrayList<itemRubrica> list_items;

    public AdapterItemsRub(@NonNull ArrayList<itemRubrica> items) {
        list_items = items;
    }

    @Override
    public AdapterItemsRub.ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list,null,false);
        return new AdapterItemsRub.ViewHolderDatos(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterItemsRub.ViewHolderDatos holder, int position) {
        holder.asignarDatos(list_items.get(position));
    }

    @Override
    public int getItemCount() {
        return list_items.size();
    }

    public class ViewHolderDatos extends RecyclerView.ViewHolder {

        TextView dato;

        public ViewHolderDatos(@NonNull View itemView) {
            super(itemView);
            dato = itemView.findViewById(R.id.idDato);
        }

        public void asignarDatos(itemRubrica item) {
            String texto = item.getDescripcion() + " "+ item.getPorcentaje() + "%";
            dato.setText(texto);

        }
    }
}
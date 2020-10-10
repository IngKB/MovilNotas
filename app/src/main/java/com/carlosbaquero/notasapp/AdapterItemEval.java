package com.carlosbaquero.notasapp;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.carlosbaquero.notasapp.models.itemRubrica;

import java.util.ArrayList;

public class AdapterItemEval extends RecyclerView.Adapter<AdapterItemEval.ViewHolderDatos> {
    @NonNull
    ArrayList<itemRubrica> list_items;
    double[] valores;

    @NonNull
    public ArrayList<itemRubrica> getList_items() {
        return list_items;
    }

    public void setList_items(@NonNull ArrayList<itemRubrica> list_items) {
        this.list_items = list_items;
    }

    public double[] getValores() {
        return valores;
    }

    public void setValores(double[] valores) {
        this.valores = valores;
    }

    public AdapterItemEval(@NonNull ArrayList<itemRubrica> items) {

        list_items = items;
        valores = new double[items.size()];
    }

    @Override
    public AdapterItemEval.ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_eval,null,false);
        return new AdapterItemEval.ViewHolderDatos(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterItemEval.ViewHolderDatos holder, int position) {
        holder.asignarDatos(list_items.get(position), valores[position]);
    }

    @Override
    public int getItemCount() {
        return list_items.size();
    }

    public class ViewHolderDatos extends RecyclerView.ViewHolder {

        TextView dato;
        EditText input;

        public ViewHolderDatos(@NonNull View itemView) {
            super(itemView);
            dato = itemView.findViewById(R.id.idDescrip);
            input = itemView.findViewById(R.id.txtValor);
            input.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    valores[getAdapterPosition()] = Double.parseDouble(s.toString());

                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }

        public void asignarDatos(itemRubrica item, double valor) {
            String texto = item.getDescripcion() + " "+ item.getPorcentaje() + "%";
            dato.setText(texto);
            input.setText(String.valueOf(valor));
        }
    }
}
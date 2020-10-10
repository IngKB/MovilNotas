package com.carlosbaquero.notasapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.carlosbaquero.notasapp.Helper.DataBaseHelper;
import com.carlosbaquero.notasapp.models.EvaluacionCorte;
import com.carlosbaquero.notasapp.models.EvaluacionMateria;
import com.carlosbaquero.notasapp.models.Materia;

import java.util.ArrayList;

public class VerEvaluaciones extends AppCompatActivity {

    RecyclerView listMaterias;
    DataBaseHelper myDb;
    AdapterDatos adapterDatos;
    ArrayList<Materia> materias;
    TextView lblPrimerCorte,lblSegundoCorte,lblTercerCorte,lblFinal;


    EvaluacionMateria evaluacionMateria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_evaluaciones);


        lblPrimerCorte = findViewById(R.id.lblCorte1Val);
        lblSegundoCorte = findViewById(R.id.lblCorte2Val);
        lblTercerCorte = findViewById(R.id.lblCorte3Val);
        lblFinal = findViewById(R.id.lblCorteFinalVal);

        myDb = new DataBaseHelper(getApplicationContext());

        listMaterias = findViewById(R.id.listMateriasEval);
        listMaterias.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

        mostrarMaterias();

    }

    public void getEvaluaciones(int mat_id){
        evaluacionMateria = myDb.getEvaluacionMateriaById(mat_id);
        if(evaluacionMateria!=null){

            EvaluacionCorte corte1 = myDb.getEvaluacionCorte(evaluacionMateria.getPrimerCorte_id());
            EvaluacionCorte corte2 = myDb.getEvaluacionCorte(evaluacionMateria.getSegundoCorte_id());
            EvaluacionCorte corte3 = myDb.getEvaluacionCorte(evaluacionMateria.getTercerCorte_id());

            lblPrimerCorte.setText(String.valueOf(corte1.getNota()));
            lblSegundoCorte.setText(String.valueOf(corte2.getNota()));
            lblTercerCorte.setText(String.valueOf(corte3.getNota()));
            lblFinal.setText(String.valueOf(evaluacionMateria.getNotaFinal()));
        }else{
            MostrarMensaje("Evaluación No Realizada");
        }
    }

    public void mostrarMaterias(){
        materias = myDb.getAllMaterias();
        if(materias.isEmpty()){
            MostrarMensaje("no hay datos");
            return;
        }else{
            MostrarMensaje("Recibidos");

        }
        adapterDatos = new AdapterDatos(materias);
        adapterDatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Materia mat = materias.get(listMaterias.getChildAdapterPosition(v));
                getEvaluaciones(mat.getId());

            }
        });
        listMaterias.setAdapter(adapterDatos);
    }

    public void MostrarMensaje(String mensaje){
        Toast.makeText(VerEvaluaciones.this, mensaje, Toast.LENGTH_SHORT).show();
    }
}
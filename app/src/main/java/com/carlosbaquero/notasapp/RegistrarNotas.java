package com.carlosbaquero.notasapp;

import androidx.annotation.ColorInt;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.carlosbaquero.notasapp.Helper.DataBaseHelper;
import com.carlosbaquero.notasapp.models.*;

import java.util.ArrayList;

public class RegistrarNotas extends AppCompatActivity {

    AdapterDatos adapterDatos;
    RecyclerView listaMaterias;
    RecyclerView listaAspectos;
    AdapterItemEval adapterItemEval1,adapterItemEval2,adapterItemEval3;
    EvaluacionCorte evaluacionCorte1,evaluacionCorte2,evaluacionCorte3;
    EvaluacionMateria evaluacionMateria;

    Button btnActualizar,btnRegistrarNota;

    DataBaseHelper myDb;
    Spinner comboCortes;
    TextView lblSelected, lblPrimerCorte,lblSegundoCorte,lblTercerCorte,lblFinal;

    ArrayList<itemRubrica> itemsRubricas1,itemsRubricas2,itemsRubricas3;
    ArrayList<Rubrica> rubricas;
    ArrayList<Materia> materias;


    Rubrica rub_primer,rub_segun,rub_tercer;
    String itemSelected;

    int IDmateria;
    boolean materiaSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notas);

        myDb = new DataBaseHelper(getApplicationContext());
        materiaSelected=false;

        comboCortes = findViewById(R.id.SpinnerCortes);

        btnActualizar = findViewById(R.id.btnActualizar);
        btnRegistrarNota = findViewById(R.id.btnRegistrarNota);

        lblSelected = findViewById(R.id.lblSelected);
        lblPrimerCorte = findViewById(R.id.lblNotaPrimer);
        lblSegundoCorte = findViewById(R.id.lblNotaSegunda);
        lblTercerCorte = findViewById(R.id.lblNotaTercer);
        lblFinal = findViewById(R.id.lblNotaFinal);

        listaMaterias = findViewById(R.id.listMaterias);
        listaMaterias.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

        listaAspectos = findViewById(R.id.listAspectos);
        listaAspectos.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

        rub_primer = new Rubrica(0.3);
        rub_segun = new Rubrica(0.3);
        rub_tercer = new Rubrica(0.4);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.combo_cortes,android.R.layout.simple_spinner_dropdown_item);
        comboCortes.setAdapter(adapter);

        mostrarMaterias();
        SetComboCortes();
        setBtnActualizar();
        setBtnRegistrarNota();
    }


    public void setBtnRegistrarNota(){
        btnRegistrarNota.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CalcularNota();
                        int corte1_id = myDb.AddEvaluacionCorte(evaluacionCorte1);
                        int corte2_id = myDb.AddEvaluacionCorte(evaluacionCorte2);
                        int corte3_id = myDb.AddEvaluacionCorte(evaluacionCorte3);

                        evaluacionMateria.setPrimerCorte_id(corte1_id);
                        evaluacionMateria.setSegundoCorte_id(corte2_id);
                        evaluacionMateria.setTercerCorte_id(corte3_id);
                        evaluacionMateria.setMateria_id(IDmateria);

                        myDb.AddEvaluacionMateria(evaluacionMateria);
                        MostrarMensaje("Evaluacion Registrada");

                    }
                }
        );
    }
    public void CalcularNota(){

        double corte1=0,corte2=0,corte3=0,notaFin;

        for (int i=0;i<adapterItemEval1.getList_items().size();i++) {

            double porcentaje = adapterItemEval1.getList_items().get(i).getPorcentaje();
            double notaitem = adapterItemEval1.getValores()[i];
            corte1 += (porcentaje*notaitem/100);
        }
        for (int i=0;i<adapterItemEval2.getList_items().size();i++) {

            double porcentaje = adapterItemEval2.getList_items().get(i).getPorcentaje();
            double notaitem = adapterItemEval2.getValores()[i];
            corte2 += (porcentaje*notaitem/100);
        }
        for (int i=0;i<adapterItemEval3.getList_items().size();i++) {

            double porcentaje = adapterItemEval3.getList_items().get(i).getPorcentaje();
            double notaitem = adapterItemEval3.getValores()[i];
            corte3 += (porcentaje*notaitem/100);
        }
        notaFin = corte1*0.3 + corte2*0.3 + corte3*0.4;


        lblPrimerCorte.setText(String.valueOf(corte1));
        lblSegundoCorte.setText(String.valueOf(corte2));
        lblTercerCorte.setText(String.valueOf(corte3));
        lblFinal.setText(String.valueOf(notaFin));

        evaluacionMateria = new EvaluacionMateria(notaFin);

        evaluacionCorte1 = new EvaluacionCorte(corte1);
        evaluacionCorte2 = new EvaluacionCorte(corte2);
        evaluacionCorte3 = new EvaluacionCorte(corte3);
    }

    public void setBtnActualizar(){
        btnActualizar.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(materiaSelected){
                            MostrarDatos();
                            CalcularNota();
                        }

                    }
                }
        );
    }
    public void SetComboCortes(){
        comboCortes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                MostrarDatos();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void MostrarDatos(){
        itemSelected = comboCortes.getSelectedItem().toString();

        if(itemsRubricas1 == null || itemsRubricas2 == null || itemsRubricas3 == null ){
            return;
        }
        if(itemSelected.equals("Primer corte")){
            if(adapterItemEval1==null){
                adapterItemEval1 = new AdapterItemEval(itemsRubricas1);
            }

            listaAspectos.setAdapter(adapterItemEval1);
        }
        if(itemSelected.equals("Segundo corte")){

            if(adapterItemEval2==null){
                adapterItemEval2 = new AdapterItemEval(itemsRubricas2);
            }
            listaAspectos.setAdapter(adapterItemEval2);

        }
        if(itemSelected.equals("Tercer corte")){

            if(adapterItemEval3==null){
                adapterItemEval3 = new AdapterItemEval(itemsRubricas3);
            }
            listaAspectos.setAdapter(adapterItemEval3);
        }

    }

    public void getItemsRubricas(int mat_id){
        rubricas = myDb.getRubricasMateria(mat_id);
        if(rubricas.isEmpty()){
            MostrarMensaje("no hay rubricas");
            return;
        }else{
            MostrarMensaje("Recibidas rubricas");
        }
        for (Rubrica rub:rubricas) {
            if(rub.getCorte()==1){
                itemsRubricas1 = myDb.getItemsByRubricaId(rub.getId());
                if(itemsRubricas1.isEmpty()){
                    MostrarMensaje("no recibio items1");
                }
            }
            if(rub.getCorte()==2){
                itemsRubricas2 = myDb.getItemsByRubricaId(rub.getId());
                if(itemsRubricas1.isEmpty()){
                    MostrarMensaje("no recibio items2");
                }
            }
            if(rub.getCorte()==3){
                itemsRubricas3 = myDb.getItemsByRubricaId(rub.getId());
                if(itemsRubricas1.isEmpty()){
                    MostrarMensaje("no recibio items3");
                }
            }
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
                Materia mat = materias.get(listaMaterias.getChildAdapterPosition(v));
                IDmateria = mat.getId();
                lblSelected.setText(mat.getNombre());
                getItemsRubricas(mat.getId());
                materiaSelected = true;
                MostrarDatos();

            }
        });
        listaMaterias.setAdapter(adapterDatos);
    }


    public void MostrarMensaje(String mensaje){
        Toast.makeText(RegistrarNotas.this, mensaje, Toast.LENGTH_SHORT).show();
    }
}
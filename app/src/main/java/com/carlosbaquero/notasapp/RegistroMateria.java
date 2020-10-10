package com.carlosbaquero.notasapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.carlosbaquero.notasapp.Helper.DataBaseHelper;
import com.carlosbaquero.notasapp.models.*;

import java.util.ArrayList;

public class RegistroMateria extends AppCompatActivity {

    DataBaseHelper myDb;
    EditText txtNombre,txtItemEv,txtItemPorc;
    TextView lblPorcentaje;
    Button btnAddItem,btnRegistrarMat;
    RecyclerView recyclerView;
    AdapterItemsRub adaptadorItems;
    Spinner comboCortes;
    Rubrica rub_primer,rub_segun,rub_tercer;
    ArrayList<itemRubrica> itemsRubricas;
    String itemSelected;


    String primerCorte,segundoCorte,tercerCorte;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_materia);

        myDb = new DataBaseHelper(getApplicationContext());

        txtNombre = findViewById(R.id.txtNombre);
        txtItemEv = findViewById(R.id.txtItemEv);
        txtItemPorc = findViewById(R.id.txtItemPorc);

        btnAddItem = findViewById(R.id.btnAddItem);
        btnRegistrarMat = findViewById(R.id.btnRegistrarMat);

        recyclerView =  findViewById(R.id.listItems);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

        comboCortes = findViewById(R.id.SpinnerCortes);
        lblPorcentaje = findViewById(R.id.lblPorcentaje);

        primerCorte="Primer corte";
        segundoCorte="Segundo corte";
        tercerCorte="Tercer corte";

        itemsRubricas = new ArrayList<>();

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.combo_cortes,android.R.layout.simple_spinner_dropdown_item);
        comboCortes.setAdapter(adapter);


        SetComboCortes();
        AgregarItem();
        RegistrarMateria();
        /*
        LeerDatos();
        RegistrarMateria();
        viewAllMaterias();
        UpdateMateria();
        DeleteMateria();
         */
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
        ArrayList<itemRubrica> itemsMostrar = new ArrayList<>();
        if(itemSelected.equals("Primer corte")){
            lblPorcentaje.setText("30%");
            itemsMostrar = filtrar(1);
        }
        if(itemSelected.equals("Segundo corte")){
            lblPorcentaje.setText("30%");
            itemsMostrar = filtrar(2);
        }
        if(itemSelected.equals("Tercer corte")){
            lblPorcentaje.setText("40%");
            itemsMostrar = filtrar(3);
        }
        adaptadorItems = new AdapterItemsRub(itemsMostrar);
        recyclerView.setAdapter(adaptadorItems);
    }

    public void AgregarItem(){
        btnAddItem.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        itemRubrica item = new itemRubrica();
                        item.setDescripcion(txtItemEv.getText().toString());
                        item.setPorcentaje( Double.parseDouble(txtItemPorc.getText().toString()));
                        if(itemSelected.equals(primerCorte)){
                            item.setRubrica_id(1);
                        }
                        if(itemSelected.equals(segundoCorte)){
                            item.setRubrica_id(2);
                        }
                        if(itemSelected.equals(tercerCorte)){
                            item.setRubrica_id(3);
                        }
                        itemsRubricas.add(item);
                        MostrarDatos();
                        LimpiarCamposItem();
                    }
                }
        );
    }

    public void LimpiarCamposItem(){
        txtItemEv.setText(null);
        txtItemPorc.setText(null);

    }

    public ArrayList<itemRubrica> filtrar(int id){
        ArrayList<itemRubrica> newList = new ArrayList<>();
        for (itemRubrica item:itemsRubricas) {
            if(item.getRubrica_id() == id){
                newList.add(item);
            }
        }
        return newList;
    }

    public void RegistrarMateria(){

        btnRegistrarMat.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Materia materia = new Materia(txtNombre.getText().toString());
                        if(verificarItems()){
                            int materia_id = myDb.AddMateria(materia);
                            rellenarRubricas(materia_id);

                            if(materia_id!=-1){

                                myDb.AddRubrica(rub_primer,filtrar(1));
                                myDb.AddRubrica(rub_segun,filtrar(2));
                                myDb.AddRubrica(rub_tercer,filtrar(3));
                                Toast.makeText(RegistroMateria.this, "Materia Registrada", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(RegistroMateria.this, "Error al registrar", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            return;
                        }

                    }
                }
        );
    }

    public boolean verificarItems(){

        ArrayList<itemRubrica> lis1 = filtrar(1);
        ArrayList<itemRubrica> lis2 = filtrar(2);
        ArrayList<itemRubrica> lis3 = filtrar(3);
        double porcent1 = 0;
        for (itemRubrica item:lis1 ) {
            porcent1 += item.getPorcentaje();
        }
        if(porcent1!=100){
            MostrarMensaje("Los porcentejes del primer corte no suman 100%");
            return false;
        }

        double porcent2 = 0;
        for (itemRubrica item:lis2 ) {
            porcent2 += item.getPorcentaje();
        }
        if(porcent2!=100){
            MostrarMensaje("Los porcentejes del segundo corte no suman 100%");
            return false;
        }

        double porcent3 = 0;
        for (itemRubrica item:lis3 ) {
            porcent3 += item.getPorcentaje();
        }
        if(porcent3!=100){
            MostrarMensaje("Los porcentejes del tercer corte no suman 100%");
            return false;
        }
        return true;
    }

    public void rellenarRubricas(int materia_id){
        rub_primer = new Rubrica(0.3);
        rub_segun = new Rubrica(0.3);
        rub_tercer = new Rubrica(0.4);

        rub_primer.setMateria_id(materia_id);
        rub_segun.setMateria_id(materia_id);
        rub_tercer.setMateria_id(materia_id);

        rub_primer.setCorte(1);
        rub_segun.setCorte(2);
        rub_tercer.setCorte(3);
    }

    /*
    public void LeerDatos(){
        ArrayList<Materia> res = myDb.getAllMaterias();
        if(res.isEmpty()){
            MostrarMensaje("Error", "no hay datos");
            return;
        }
        adapter = new AdapterDatos(res);
        recyclerView.setAdapter(adapter);
    }
    public void viewAllMaterias(){
        btnVerData.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LeerDatos();
                    }
                }
        );
    }
    public void RegistrarMateria(){
        btnRegistrar.setOnClickListener(
                new View.OnClickListener(){

                    @Override
                    public void onClick(View v) {
                        Materia mat = new Materia(Integer.parseInt(txtId.getText().toString()),txtNombre.getText().toString());
                        boolean isInserted =  myDb.AddMateria(mat);
                        if(isInserted){
                            Toast.makeText(RegistroMateria.this, "Data inserted", Toast.LENGTH_SHORT).show();
                            txtId.setText("");
                            txtNombre.setText("");
                        }else{
                            Toast.makeText(RegistroMateria.this,"Data not inserted", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
    }

    public void UpdateMateria(){
        btnUpdate.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Materia mat = new Materia(Integer.parseInt(txtId.getText().toString()),txtNombre.getText().toString());
                        boolean isUpdated = myDb.UpdateMateria(mat);
                        if(isUpdated){
                            Toast.makeText(RegistroMateria.this, "Data Updated", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(RegistroMateria.this,"Data not Updated", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
    }

    public void DeleteMateria(){
        btnDelete.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Integer deletedRows = myDb.DeleteMateria(txtId.getText().toString());
                        if(deletedRows>0){
                            Toast.makeText(RegistroMateria.this, "Data Deleted", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(RegistroMateria.this,"Data not Deleted", Toast.LENGTH_SHORT).show();
                        }

                    }
                }
        );
    }


    */

    public void MostrarMensaje(String mensaje){
        Toast.makeText(RegistroMateria.this, mensaje, Toast.LENGTH_SHORT).show();
    }
}
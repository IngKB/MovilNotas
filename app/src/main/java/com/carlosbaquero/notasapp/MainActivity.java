package com.carlosbaquero.notasapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.carlosbaquero.notasapp.Helper.DataBaseHelper;

public class MainActivity extends AppCompatActivity {


    DataBaseHelper myDb;
    Button btnRegistrar,btnNotas,btnEvals;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DataBaseHelper(this);

        btnRegistrar = findViewById(R.id.btnGoToRegistrar);
        btnNotas = findViewById(R.id.btnGoToNotas);
        btnEvals = findViewById(R.id.btnGoToEvals);
        goToRegistrar();
        goToNotas();
        goToEvals();
    }

    public void goToRegistrar(){

        btnRegistrar.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, RegistroMateria.class);
                        startActivity(intent);
                    }
                }
        );
    }

    public void goToNotas(){
        btnNotas.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, RegistrarNotas.class);
                        startActivity(intent);
                    }
                }
        );
    }

    public void goToEvals(){
        btnEvals.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, VerEvaluaciones.class);
                        startActivity(intent);
                    }
                }
        );
    }
}
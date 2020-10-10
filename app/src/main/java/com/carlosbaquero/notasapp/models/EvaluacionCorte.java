package com.carlosbaquero.notasapp.models;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class EvaluacionCorte {
    private int id;
    private double nota;

    public EvaluacionCorte() {
    }

    public EvaluacionCorte( double nota) {
        this.nota = nota;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public double getNota() {
        return nota;
    }

    public void setNota(double nota) {
        this.nota = nota;
    }




}

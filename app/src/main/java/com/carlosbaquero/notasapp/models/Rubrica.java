package com.carlosbaquero.notasapp.models;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class Rubrica {
    private int id;
    private int materia_id;
    private int corte;
    private double porcentajeCorte;

    public Rubrica(){}

    public Rubrica(double porcentajeCorte) {
        this.porcentajeCorte = porcentajeCorte;
    }

    public Rubrica(int id, int materia_id, int corte, double porcentajeCorte) {
        this.id = id;
        this.materia_id = materia_id;
        this.corte = corte;
        this.porcentajeCorte = porcentajeCorte;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMateria_id() {
        return materia_id;
    }

    public void setMateria_id(int materia_id) {
        this.materia_id = materia_id;
    }

    public int getCorte() {
        return corte;
    }

    public void setCorte(int corte) {
        this.corte = corte;
    }

    public double getPorcentajeCorte() {
        return porcentajeCorte;
    }

    public void setPorcentajeCorte(double porcentajeCorte) {
        this.porcentajeCorte = porcentajeCorte;
    }


}

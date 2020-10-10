package com.carlosbaquero.notasapp.models;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class itemRubrica {
    private int id;
    private int rubrica_id;
    private String descripcion;
    private double porcentaje;

    public itemRubrica() {
    }

    public itemRubrica(int id, int rubrica_id, String descripcion, double porcentaje) {
        this.id = id;
        this.rubrica_id = rubrica_id;
        this.descripcion = descripcion;
        this.porcentaje = porcentaje;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRubrica_id() {
        return rubrica_id;
    }

    public void setRubrica_id(int rubrica_id) {
        this.rubrica_id = rubrica_id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(double porcentaje) {
        this.porcentaje = porcentaje;
    }



}

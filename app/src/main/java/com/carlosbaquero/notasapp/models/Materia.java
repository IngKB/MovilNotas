package com.carlosbaquero.notasapp.models;

public class Materia {
    private int id;
    private String nombre;

    public Materia(){}

    public Materia( String nombre){
        this.setNombre(nombre);
    }
    public Materia(int id, String nombre){
        this.setId(id);
        this.setNombre(nombre);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}

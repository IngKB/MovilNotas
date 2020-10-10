package com.carlosbaquero.notasapp.models;

public class EvaluacionMateria {
    private int id;
    private int primerCorte_id;
    private int segundoCorte_id;
    private int tercerCorte_id;

    private int materia_id;
    private double notaFinal;

    public EvaluacionMateria() {
    }

    public EvaluacionMateria(double notaFinal) {
        this.notaFinal = notaFinal;
    }

    public int getMateria_id() {
        return materia_id;
    }

    public void setMateria_id(int materia_id) {
        this.materia_id = materia_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPrimerCorte_id() {
        return primerCorte_id;
    }

    public void setPrimerCorte_id(int primerCorte_id) {
        this.primerCorte_id = primerCorte_id;
    }

    public int getSegundoCorte_id() {
        return segundoCorte_id;
    }

    public void setSegundoCorte_id(int segundoCorte_id) {
        this.segundoCorte_id = segundoCorte_id;
    }

    public int getTercerCorte_id() {
        return tercerCorte_id;
    }

    public void setTercerCorte_id(int tercerCorte_id) {
        this.tercerCorte_id = tercerCorte_id;
    }

    public double getNotaFinal() {
        return notaFinal;
    }

    public void setNotaFinal(double notaFinal) {
        this.notaFinal = notaFinal;
    }
}

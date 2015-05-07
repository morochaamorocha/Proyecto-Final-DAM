package com.example.rals.codehelp.model;

/**
 * Created by rals1_000 on 05/05/2015.
 */
public class Experto {

    private String idExperto;
    private int numCasos;
    private float costeMin;

    public Experto() {
    }

    public Experto(String idExperto, int numCasos, float costeMin) {
        this.idExperto = idExperto;
        this.numCasos = numCasos;
        this.costeMin = costeMin;
    }

    public String getIdExperto() {
        return idExperto;
    }

    public void setIdExperto(String idExperto) {
        this.idExperto = idExperto;
    }

    public int getNumCasos() {
        return numCasos;
    }

    public void setNumCasos(int numCasos) {
        this.numCasos = numCasos;
    }

    public float getCosteMin() {
        return costeMin;
    }

    public void setCosteMin(float costeMin) {
        this.costeMin = costeMin;
    }
}

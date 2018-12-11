package br.com.willtrkapp.pa1_appfin.model;

import java.io.Serializable;

public class Conta implements Serializable {
    //private static final long serialVersionUID = 1L;
    private long id;
    private String descr;
    private float saldoIni;

    public Conta(){ }

    public Conta(String descr, float saldoIni) {
        this.descr = descr;
        this.saldoIni = saldoIni;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public float getSaldoIni() {
        return saldoIni;
    }

    public void setSaldoIni(float saldoIni) {
        this.saldoIni = saldoIni;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public  String toString()
    {
        return this.getDescr();
    }
}

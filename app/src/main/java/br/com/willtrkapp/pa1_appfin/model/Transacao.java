package br.com.willtrkapp.pa1_appfin.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Transacao {
    private int id;
    private long idConta;
    private long idCategoria;
    private float valor;
    private String descricao;
    private int natureza; //1 = CREDITO 2 = DEBITO
    private Date dtIns; //Data inserção
    private Date dtLib; //Data liberação

    //Implementar data
    public Transacao()
    {
    }

    public Transacao(int id, int idConta, long idCategoria, float valor, String descricao, int natureza, Date dtIns, Date dtLib) {
        this.id = id;
        this.idConta = idConta;
        this.idCategoria = idCategoria;
        this.valor = valor;
        this.descricao = descricao;
        this.natureza = natureza;
        this.dtIns = dtIns;
        this.dtLib = dtLib;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getIdConta() {
        return idConta;
    }

    public void setIdConta(long idConta) {
        this.idConta = idConta;
    }

    public long getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(long idCategoria) {
        this.idCategoria = idCategoria;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getNatureza() {
        return natureza;
    }

    public void setNatureza(int natureza) {
        this.natureza = natureza;
    }

    public Date getDtIns() {
        return dtIns;
    }

    public void setDtIns(Date dtIns) {
        this.dtIns = dtIns;
    }

    public Date getDtLib() {
        return dtLib;
    }

    public void setDtLib(Date dtLib) {
        this.dtLib = dtLib;
    }



    @Override
    public String toString()
    {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

        return "Id: " + getId() + "\nIdConta " + getIdConta() + "\nIdCategoria " + getIdCategoria() +
        "\nValor " + getValor() + "\nDescr " + getDescricao() + "\nNatureza " + getNatureza() + "\nDtIns " + format.format(getDtIns()) + "\nDtLib" + format.format(getDtLib());

    }
}

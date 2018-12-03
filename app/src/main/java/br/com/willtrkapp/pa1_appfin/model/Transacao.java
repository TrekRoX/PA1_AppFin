package br.com.willtrkapp.pa1_appfin.model;

public class Transacao {
    private int id;
    private int idConta;
    private long idCategoria;
    private float valor;
    private String descricao;
    private int natureza;

    //Implementar data
    public Transacao()
    {
    }

    public Transacao(int id, int idConta, long idCategoria, float valor, String descricao, int natureza) {
        this.id = id;
        this.idConta = idConta;
        this.idCategoria = idCategoria;
        this.valor = valor;
        this.descricao = descricao;
        this.natureza = natureza;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdConta() {
        return idConta;
    }

    public void setIdConta(int idConta) {
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
}

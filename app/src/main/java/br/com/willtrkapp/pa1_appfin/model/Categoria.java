package br.com.willtrkapp.pa1_appfin.model;

public class Categoria {
    private long id;
    private String descr;

    public Categoria() {
    }

    public Categoria(long id, String descr) {
        this.id = id;
        this.descr = descr;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    @Override
    public String toString()
    {
        return getDescr();
    }
}

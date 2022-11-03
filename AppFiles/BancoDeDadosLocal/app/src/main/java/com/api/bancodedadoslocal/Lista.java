package com.api.bancodedadoslocal;

public class Lista {

    private String lista;
    private String dataLista;

    @Override
    public String toString() {
        return "Lista{" +
                "lista='" + lista + '\'' +
                ", dataLista='" + dataLista + '\'' +
                '}';
    }

    public String getLista() {
        return lista;
    }

    public void setLista(String lista) {
        this.lista = lista;
    }

    public String getDataLista() {
        return dataLista;
    }

    public void setDataLista(String dataLista) {
        this.dataLista = dataLista;
    }
}

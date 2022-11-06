package com.api.bancodedadoslocal;

public class Lista {

    private int idLista;
    private String nomeLista;
    private String dataLista;

    @Override
    public String toString() {
        return "Lista{" +
                "lista='" + idLista + '\'' +
                ", nomeLista='" + nomeLista + '\'' +
                ", dataLista='" + dataLista + '\'' +
                '}';
    }

    public Lista(int id_lista, String nomeLista, String dataLista){
        setIdLista(id_lista);
        setNomeLista(nomeLista);
        setDataLista(dataLista);
    }

    public int getIdLista() {
        return idLista;
    }

    public void setIdLista(int lista) {
        this.idLista = lista;
    }

    public String getDataLista() {
        return dataLista;
    }

    public void setDataLista(String dataLista) {
        this.dataLista = dataLista;
    }

    public String getNomeLista() {
        return nomeLista;
    }

    public void setNomeLista(String nomeLista) {
        this.nomeLista = nomeLista;
    }
}

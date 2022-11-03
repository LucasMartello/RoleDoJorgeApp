package com.api.bancodedadoslocal;

public class ItemLista {

    private Lista lista;
    private Produto produto;

    @Override
    public String toString() {
        return "ItemLista{" +
                "lista=" + lista +
                ", produto=" + produto +
                '}';
    }

    public Lista getLista() {
        return lista;
    }

    public void setLista(Lista lista) {
        this.lista = lista;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }
}

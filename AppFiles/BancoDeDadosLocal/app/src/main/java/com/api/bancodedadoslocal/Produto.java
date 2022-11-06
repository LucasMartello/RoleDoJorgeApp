package com.api.bancodedadoslocal;

public class Produto {

    private int id;
    private String nome;
    private double valor;
    private boolean selected;


    public Produto(int id, String nome, double valor, boolean selected){
        setId(id);
        setNome(nome);
        setValor(valor);
        setSelected(selected);
    }

    @Override
    public String toString() {
        return "Produto{" +
                "id='" + id + '\'' +
                ", nome='" + nome + '\'' +
                ", valor='" + valor + '\'' +
                ", selected=" + selected +
                '}';
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public int getId() {return id;}

    public void setId(int id) {
        this.id = id;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}

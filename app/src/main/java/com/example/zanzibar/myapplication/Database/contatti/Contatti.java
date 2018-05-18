package com.example.zanzibar.myapplication.Database.contatti;

public class Contatti {

    private String nome;
    private String ruolo;
    private String numero;



    public Contatti(){}

    public Contatti(String nome, String ruolo, String numero){
        this.nome = nome;
        this.ruolo = ruolo;
        this.numero = numero;
    }

    @Override
    public String toString(){
        return nome +" "+ ruolo +" "+ numero;
    }

    public String getNumero() {
        return numero;
    }

    public String getRuolo() {
        return ruolo;
    }

    public String getNome() {
        return nome;
    }

    public void setRuolo(String ruolo) {
        this.ruolo = ruolo;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }
}

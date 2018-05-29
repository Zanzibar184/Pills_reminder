package com.example.zanzibar.myapplication.Database.contatti;

public class Contatti {

    private String nome;
    private String ruolo;
    private String numero;
    private String foto;



    public Contatti(){}

    public Contatti(String nome, String ruolo, String numero){
        this.nome = nome;
        this.ruolo = ruolo;
        this.numero = numero;
        this.foto=null;
    }
    public Contatti(String nome, String ruolo, String numero, String foto){
        this.nome = nome;
        this.ruolo = ruolo;
        this.numero = numero;
        this.foto=foto;
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

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getFoto() {
        return foto;
    }
}

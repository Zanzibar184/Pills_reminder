package com.example.zanzibar.myapplication.Database.contatti;

public class Contatti {

    private String nome;
    private String ruolo;
    private String numero;
    private String foto;
    private int importante;



    public Contatti(){}

    public Contatti(String nome, String ruolo, String numero, int importante){
        this.nome = nome;
        this.ruolo = ruolo;
        this.numero = numero;
        this.foto=null;
        this.importante = importante;
    }
    public Contatti(String nome, String ruolo, String numero, String foto, int importante){
        this.nome = nome;
        this.ruolo = ruolo;
        this.numero = numero;
        this.foto=foto;
        this.importante = importante;
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

    public int getImportante() {
        return importante;
    }

    public void setImportante(int importante) {
        this.importante = importante;
    }
}

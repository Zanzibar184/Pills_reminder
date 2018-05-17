package com.example.zanzibar.myapplication.Database_cure;

public class Cura {

    private String nome;
    private int quantità_assunzione;
    private int scorta;
    private int rimanenze;
    private String inizio_cura;
    private String fine_cura;

    public Cura(){}

    public Cura(String nome, int quantità_assunzione, int scorta, int rimanenze, String inizio_cura, String fine_cura){
        this.nome = nome;
        this.quantità_assunzione = quantità_assunzione;
        this.scorta = scorta;
        this.rimanenze = rimanenze;
        this.inizio_cura = inizio_cura;
        this.fine_cura = fine_cura;
    }

    @Override
    public String toString(){
        return nome +" "+ quantità_assunzione +" "+ scorta +" "+ rimanenze+" "+ inizio_cura +" "+ fine_cura;
    }


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getQuantità_assunzione() {
        return quantità_assunzione;
    }

    public void setQuantità_assunzione(int quantità_assunzione) {
        this.quantità_assunzione = quantità_assunzione;
    }

    public int getScorta() {
        return scorta;
    }

    public void setScorta(int scorta) {
        this.scorta = scorta;
    }

    public int getRimanenze() {
        return rimanenze;
    }

    public void setRimanenze(int rimanenze) {
        this.rimanenze = rimanenze;
    }


    public String getInizio_cura() {
        return inizio_cura;
    }

    public String getFine_cura() {
        return fine_cura;
    }

    public void setFine_cura(String fine_cura) {
        this.fine_cura = fine_cura;
    }
    public void setInizio_cura(String inizio_cura) {
        this.inizio_cura = inizio_cura;
    }

}

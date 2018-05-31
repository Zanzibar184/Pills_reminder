package com.example.zanzibar.myapplication.Database.cure;

public class Cura {

    private String nome;
    private int quantità_assunzione;
    private int scorta;
    private int rimanenze;
    private String inizio_cura;
    private String fine_cura;
    private int tipo_cura;
    private String orario_assunzione;
    private String stato_cura; //da_assumere - assunta - non_assunta
    private int id;
    private String foto;
    private String unità_misura;
    private int importante;




    public static final String ASSUNTA = "assunta";
    public static final String NON_ASSUNTA = "non_assunta";
    public static final String DA_ASSUMERE = "da_assumere";


    public Cura(String nome, int quantità_assunzione, int scorta, int rimanenze, String inizio_cura, String fine_cura, int tipo_cura, String orario_assunzione, String stato_cura, String foto,String unità_misura, int importante){
        this.nome = nome;
        this.quantità_assunzione = quantità_assunzione;
        this.scorta = scorta;
        this.rimanenze = rimanenze;
        this.inizio_cura = inizio_cura;
        this.fine_cura = fine_cura;
        this.tipo_cura = tipo_cura;
        this.orario_assunzione = orario_assunzione;
        this.stato_cura = stato_cura;
        this.id = -1;
        this.foto = foto;
        this.unità_misura = unità_misura;
        this.importante = importante;
    }

    public Cura(String nome, int quantità_assunzione, int scorta, int rimanenze, String inizio_cura, String fine_cura, int tipo_cura, String orario_assunzione, String stato_cura, int id, String foto, String unità_misura, int importante){
        this.nome = nome;
        this.quantità_assunzione = quantità_assunzione;
        this.scorta = scorta;
        this.rimanenze = rimanenze;
        this.inizio_cura = inizio_cura;
        this.fine_cura = fine_cura;
        this.tipo_cura = tipo_cura;
        this.orario_assunzione = orario_assunzione;
        this.stato_cura = stato_cura;
        this.id = id;
        this.foto = foto;
        this.unità_misura = unità_misura;
        this.importante = importante;
    }

    @Override
    public String toString(){
        return nome +" "+ quantità_assunzione +" "+ scorta +" "+ rimanenze+" "+ inizio_cura +" "+ fine_cura + " " + tipo_cura + " " + orario_assunzione + " " + stato_cura + " " + foto + " " + unità_misura + " " + importante;
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

    public int getTipo_cura() {
        return tipo_cura;
    }

    public void setTipo_cura(int tipo_cura) {
        this.tipo_cura = tipo_cura;
    }

    public String getOrario_assunzione() {
        return orario_assunzione;
    }

    public void setOrario_assunzione(String orario_assunzione) {
        this.orario_assunzione = orario_assunzione;
    }

    public String getStato_cura() {
        return stato_cura;
    }

    public void setStato_cura(String stato_cura) {
        this.stato_cura = stato_cura;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getUnità_misura() {
        return unità_misura;
    }

    public void setUnità_misura(String unità_misura) {
        this.unità_misura = unità_misura;
    }

    public void setImportante(int importante) {
        this.importante = importante;
    }

    public int getImportante() {
        return importante;
    }
}

package com.example.zanzibar.myapplication.Database.cure;

public class Dosi {


    private String giorno;
    private String stato_cura; //da_assumere - assunta - non_assunta
    private int id;




    public static final String ASSUNTA = "assunta";
    public static final String NON_ASSUNTA = "non_assunta";
    public static final String DA_ASSUMERE = "da_assumere";


    public Dosi(int id, String giorno, String stato_cura){

        this.giorno = giorno;
        this.stato_cura = stato_cura;
        this.id = id;

    }

    @Override
    public String toString() {
        return giorno +" " + stato_cura + " "+ id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setStato_cura(String stato_cura) {
        this.stato_cura = stato_cura;
    }

    public String getStato_cura() {
        return stato_cura;
    }

    public String getGiorno() {
        return giorno;
    }

    public void setGiorno(String giorno) {
        this.giorno = giorno;
    }

}

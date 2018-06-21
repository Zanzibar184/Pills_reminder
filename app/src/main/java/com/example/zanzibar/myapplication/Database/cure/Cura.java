package com.example.zanzibar.myapplication.Database.cure;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class Cura {

    private String nome;
    private int quantità_assunzione;
    private int scorta;
    private int rimanenze;
    private String inizio_cura;
    private String fine_cura;
    private int tipo_cura;
    private String orario_assunzione;
    private int id;
    private String foto;
    private String unità_misura;
    private int importante;
    private String ripetizione;



    public Cura(String nome, int quantità_assunzione, int scorta, int rimanenze, String inizio_cura, String fine_cura, int tipo_cura, String orario_assunzione,String foto,String unità_misura, int importante, String ripetizione){
        this.nome = nome;
        this.quantità_assunzione = quantità_assunzione;
        this.scorta = scorta;
        this.rimanenze = rimanenze;
        this.inizio_cura = inizio_cura;
        this.fine_cura = fine_cura;
        this.tipo_cura = tipo_cura;
        this.orario_assunzione = orario_assunzione;
        this.id = -1;
        this.foto = foto;
        this.unità_misura = unità_misura;
        this.importante = importante;
        this.ripetizione = ripetizione;
    }

    public Cura(String nome, int quantità_assunzione, int scorta, int rimanenze, String inizio_cura, String fine_cura, int tipo_cura, String orario_assunzione, int id, String foto, String unità_misura, int importante, String ripetizione){
        this.nome = nome;
        this.quantità_assunzione = quantità_assunzione;
        this.scorta = scorta;
        this.rimanenze = rimanenze;
        this.inizio_cura = inizio_cura;
        this.fine_cura = fine_cura;
        this.tipo_cura = tipo_cura;
        this.orario_assunzione = orario_assunzione;
        this.id = id;
        this.foto = foto;
        this.unità_misura = unità_misura;
        this.importante = importante;
        this.ripetizione = ripetizione;
    }

    @Override
    public String toString(){
        return nome +" "+ quantità_assunzione +" "+ scorta +" "+ rimanenze+" "+ inizio_cura +" "+ fine_cura + " " + tipo_cura + " " + orario_assunzione + " " + id + " " + foto + " " + unità_misura + " " + importante + " " + ripetizione;
    }

    public static Cura toCura(String string){
        String[] splited = string.trim().split("\\s+");
        String nome = splited[0];
        int quantità_assunzione = Integer.parseInt(splited[1]);
        int scorta = Integer.parseInt(splited[2]);
        int rimanenze = Integer.parseInt(splited[3]);
        String inizio_cura  = splited[4];
        String fine_cura  = splited[5];
        int tipo_cura = Integer.parseInt(splited[6]);
        String orario_assunzione  = splited[7];
        int id = Integer.parseInt(splited[8]);
        String foto  = splited[9];
        String unità_misura  = splited[10];
        int importante = Integer.parseInt(splited[11]);
        String ripetizione  = splited[12];

        return new Cura(nome,quantità_assunzione,scorta,rimanenze,inizio_cura,fine_cura,tipo_cura,orario_assunzione,id,foto,unità_misura,importante, ripetizione);


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

    public String getRipetizione() {
        return ripetizione;
    }

    public void setRipetizione(String ripetizione) {
        this.ripetizione = ripetizione;
    }

    public static String parseRipetizione(ArrayList<String> base){
        String result= "";
        for(int i=0; i< base.size(); i++){
            result = result + "-" + base.get(i);
        }
        return result;
    }
    public static ArrayList<String>  reverseRipetizione(String base){
        String[] arrayString = base.split("-");
        ArrayList<String> days = new ArrayList<>();
        for(int i = 0; i < arrayString.length; i++){
            days.add(arrayString[i]);
        }
        return days;
    }
}

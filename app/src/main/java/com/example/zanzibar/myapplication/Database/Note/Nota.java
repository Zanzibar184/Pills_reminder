package com.example.zanzibar.myapplication.Database.Note;

public class Nota {
    private String titolo;
    private String testo;
    private String data;
    private String ora;
    private int tipo_memo;
    private int id_memo;

    public Nota(){

    }

    public Nota(String titolo,String testo, String data, String ora, int tipo_memo){
        this.titolo = titolo;
        this.testo = testo;
        this.data = data;
        this.ora = ora;
        this.tipo_memo = tipo_memo;
    }
    public Nota(String titolo,String testo, String data, String ora, int tipo_memo, int id_memo){
        this.titolo = titolo;
        this.testo = testo;
        this.data = data;
        this.ora = ora;
        this.tipo_memo = tipo_memo;
        this.id_memo = id_memo;
    }

    public int getTipo_memo() {
        return tipo_memo;
    }

    public String getData() {
        return data;
    }

    public String getOra() {
        return ora;
    }

    public String getTesto() {
        return testo;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setTipo_memo(int tipo_memo) {
        this.tipo_memo = tipo_memo;
    }

    public void setOra(String ora) {
        this.ora = ora;
    }

    public void setTesto(String testo) {
        this.testo = testo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public void setId_memo(int id_memo) {
        this.id_memo = id_memo;
    }

    public int getId_memo() {
        return id_memo;
    }
}

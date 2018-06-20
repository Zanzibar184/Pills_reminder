package com.example.zanzibar.myapplication.Database.cure;

import java.util.List;

public interface CureDAO {

    public void open();
    public void close();

    public Cura insertCura(Cura cura);
    public void deleteCura(Cura cura);
    public void updateCura(Cura cura);
    public List<Cura> getAllCure();
    public List<Cura> getCureByDate(String date);
    public List<Cura> getCureByRange(String date);
    public Cura findCura(String nome, String inizio_cura, String fine_cura, String orario);
    public List<Dosi> getAllDosi();
    public List<Dosi> getDosiByDate(String date);
    public void updateDose(Dosi dose);
    public void reinitDose(Cura cura);
}

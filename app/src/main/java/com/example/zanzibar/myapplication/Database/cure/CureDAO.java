package com.example.zanzibar.myapplication.Database.cure;

import java.util.List;

public interface CureDAO {

    public void open();
    public void close();

    public Cura insertCura(Cura person);
    public void deleteCura(Cura person);
    public List<Cura> getAllCure();
    public List<Cura> getTodayCure();
}

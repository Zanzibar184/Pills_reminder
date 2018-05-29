package com.example.zanzibar.myapplication.Database.contatti;

import java.util.List;

public interface ContattiDAO {

    public void open();
    public void close();

    public Contatti insertContatto(Contatti contatti);
    public void deleteContatto(Contatti contatti);
    public void updateContatto(Contatti contatti, String numero);
    public List<Contatti> getAllContatti();
}

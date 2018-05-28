package com.example.zanzibar.myapplication.Database.Note;

import java.util.List;

public interface NoteDao {

    public void open();
    public void close();

    public Nota insertNota(Nota nota);
    public void deleteNota(Nota nota);
    public void updateNota(Nota nota);
    public List<Nota> getAllNote();
    public List<Nota> getNoteByDate(String date);
}







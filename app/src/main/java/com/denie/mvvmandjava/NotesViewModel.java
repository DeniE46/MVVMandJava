package com.denie.mvvmandjava;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.annotation.NonNull;

import java.util.List;

public class NotesViewModel extends AndroidViewModel {
    private NoteRepository noteRepository;
    private LiveData<List<Note>> notesList;

    public NotesViewModel(@NonNull Application application) {
        super(application);
        noteRepository = new NoteRepository(application);
        notesList = noteRepository.getAllNotes();
    }


    void insert(Note note){
        noteRepository.insert(note);
    }

    void update(Note note){
        noteRepository.update(note);
    }

    void delete(Note note){
        noteRepository.delete(note);
    }

    void deleteAll(){
        noteRepository.deleteAllNotes();
    }

    LiveData<List<Note>> getAllNotes(){
        return notesList;
    }


}

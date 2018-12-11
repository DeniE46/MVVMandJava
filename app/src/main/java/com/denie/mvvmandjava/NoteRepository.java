package com.denie.mvvmandjava;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class NoteRepository {
    NoteDao noteDao;

    LiveData<List<Note>> notesList;

    public NoteRepository(Application application) {
        NoteDataBase noteDataBase = NoteDataBase.getInstance(application);
        noteDao = noteDataBase.noteDao();
        notesList = noteDao.getAllNotes();
    }

    void insert(Note note){
        new InsertNotesAsync(noteDao).execute(note);
    }

    void update(Note note){
        new UpdateNotesAsync(noteDao).execute(note);
    }

    void delete(Note note){
        new DeleteAllNotesAsync(noteDao).execute(note);
    }

    void deleteAllNotes(){
        new DeleteAllNotesAsync(noteDao).execute();
    }

    LiveData<List<Note>> getAllNotes(){
        return notesList;
    }


    private static class InsertNotesAsync extends AsyncTask<Note, Void, Void>{
        NoteDao noteDao;

        public InsertNotesAsync(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.insert(notes[0]);
            return null;
        }
    }


    private static class UpdateNotesAsync extends AsyncTask<Note, Void, Void>{
        NoteDao noteDao;

        public UpdateNotesAsync(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.update(notes[0]);
            return null;
        }
    }

    private static class DeleteNotesAsync extends AsyncTask<Note, Void, Void>{
        NoteDao noteDao;

        public DeleteNotesAsync(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.delete(notes[0]);
            return null;
        }
    }

    private static class DeleteAllNotesAsync extends AsyncTask<Note, Void, Void>{
        NoteDao noteDao;

        public DeleteAllNotesAsync(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.deleteAll();
            return null;
        }
    }


}

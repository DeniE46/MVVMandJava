package com.denie.mvvmandjava;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

@Database(entities = {Note.class}, version = 1)
public abstract class NoteDataBase extends RoomDatabase {
    private static NoteDataBase instance;
    public abstract NoteDao noteDao();

    public static synchronized NoteDataBase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), NoteDataBase.class, "note_db")
                    .fallbackToDestructiveMigration()
                    .addCallback(callback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback callback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new InsertAsync(instance).execute();

        }
    };


    private static class InsertAsync extends AsyncTask<Void, Void, Void>{
        NoteDao noteDao;

        public InsertAsync(NoteDataBase db) {
            this.noteDao = db.noteDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.insert(new Note("Title 1", "Description 1", 1));
            noteDao.insert(new Note("Title 2", "Description 2", 2));
            noteDao.insert(new Note("Title 3", "Description 3", 3));
            return null;
        }
    }


}

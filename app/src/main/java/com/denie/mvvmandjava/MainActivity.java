package com.denie.mvvmandjava;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    NotesViewModel notesViewModel;
    FloatingActionButton floatingActionButton;
    public static final int ADD_NOTE = 1;
    public static final int EDIT_NOTE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        floatingActionButton = findViewById(R.id.add_new_note);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddEditItemActivity.class);
                startActivityForResult(intent, ADD_NOTE);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final NotesAdapter notesAdapter = new NotesAdapter();
        recyclerView.setAdapter(notesAdapter);


        notesViewModel = ViewModelProviders.of(this).get(NotesViewModel.class);
        notesViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(@Nullable List<Note> notes) {
                notesAdapter.submitList(notes);
                //Toast.makeText(MainActivity.this, "onChanged", Toast.LENGTH_SHORT).show();
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                notesViewModel.delete(notesAdapter.getNotePosition(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "note deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        notesAdapter.setOnItemClickListener(new NotesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Note note) {
                Intent intent  = new Intent(MainActivity.this, AddEditItemActivity.class);
                intent.putExtra(AddEditItemActivity.EXTRA_ID, note.getId());
                intent.putExtra(AddEditItemActivity.EXTRA_TITLE, note.getTitle());
                intent.putExtra(AddEditItemActivity.EXTRA_DESCRIPTION, note.getDescription());
                intent.putExtra(AddEditItemActivity.EXTRA_PRIORITY, note.getPriority());

                startActivityForResult(intent, EDIT_NOTE);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_NOTE && resultCode == RESULT_OK) {
            String title = data.getStringExtra(AddEditItemActivity.EXTRA_TITLE);
            String description = data.getStringExtra(AddEditItemActivity.EXTRA_DESCRIPTION);
            int priority = data.getIntExtra(AddEditItemActivity.EXTRA_PRIORITY, 1);
            Note note = new Note(title, description, priority);
            notesViewModel.insert(note);
            Toast.makeText(this, "Note added", Toast.LENGTH_SHORT).show();
        } else if (requestCode == EDIT_NOTE && resultCode == RESULT_OK) {
            String title = data.getStringExtra(AddEditItemActivity.EXTRA_TITLE);
            String description = data.getStringExtra(AddEditItemActivity.EXTRA_DESCRIPTION);
            int priority = data.getIntExtra(AddEditItemActivity.EXTRA_PRIORITY, 1);
            int id = data.getIntExtra(AddEditItemActivity.EXTRA_ID, -1);
            if(id == -1){
                Toast.makeText(this, "Note can't modified", Toast.LENGTH_SHORT).show();
                return;
            }
            else{
                Note note = new Note(title, description, priority);
                note.setId(id);
                notesViewModel.update(note);
                Toast.makeText(this, "Note modified", Toast.LENGTH_SHORT).show();
            }

        }else {
            Toast.makeText(this, "note not added", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.delete_all_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_all:
                notesViewModel.deleteAll();
                Toast.makeText(this, "all notes deleted", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

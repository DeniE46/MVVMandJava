package com.denie.mvvmandjava;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NoteHolder> {
    List<Note> noteList = new ArrayList<>();

    class NoteHolder extends RecyclerView.ViewHolder {
        TextView titleView;
        TextView priorityView;
        TextView descriptionView;

        public NoteHolder(@NonNull View itemView) {
            super(itemView);

            titleView = itemView.findViewById(R.id.text_view_title);
            priorityView = itemView.findViewById(R.id.text_view_priority);
            descriptionView = itemView.findViewById(R.id.text_view_description);
        }



    }

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.notes_item, viewGroup, false);

        return new NoteHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder viewHolder, int i) {
        Note note = noteList.get(i);
        viewHolder.titleView.setText(note.getTitle());
        viewHolder.descriptionView.setText(note.getDescription());
        viewHolder.priorityView.setText(String.valueOf(note.getPriority()));
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

    void setNoteList(List<Note> noteList){
        this.noteList = noteList;
        notifyDataSetChanged();
    }
}

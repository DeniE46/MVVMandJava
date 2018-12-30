package com.denie.mvvmandjava;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class NotesAdapter extends ListAdapter<Note, NotesAdapter.NoteHolder> {
    OnItemClickListener listener;

    public NotesAdapter() {
        super(DIFF_CALLBACK);
    }

    public static final DiffUtil.ItemCallback<Note> DIFF_CALLBACK = new DiffUtil.ItemCallback<Note>() {
        @Override
        public boolean areItemsTheSame(@NonNull Note note, @NonNull Note t1) {
            return note.getId() == t1.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Note note, @NonNull Note t1) {
            return note.getTitle().equals(t1.getTitle()) && note.getDescription().equals(t1.getDescription()) && note.getPriority() == t1.getPriority();
        }
    };

    class NoteHolder extends RecyclerView.ViewHolder {
        TextView titleView;
        TextView priorityView;
        TextView descriptionView;

        public NoteHolder(@NonNull View itemView) {
            super(itemView);

            titleView = itemView.findViewById(R.id.text_view_title);
            priorityView = itemView.findViewById(R.id.text_view_priority);
            descriptionView = itemView.findViewById(R.id.text_view_description);

              itemView.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {
                      int position = getAdapterPosition();
                      if(listener != null && position != RecyclerView.NO_POSITION) {
                          listener.onItemClick(getItem(position));
                      }
                  }
              });
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
        Note note = getItem(i);
        viewHolder.titleView.setText(note.getTitle());
        viewHolder.descriptionView.setText(note.getDescription());
        viewHolder.priorityView.setText(String.valueOf(note.getPriority()));
    }


    public Note getNotePosition(int position){
        return getItem(position);
    }


    public interface OnItemClickListener{
        void onItemClick(Note note);
    }

    void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

}




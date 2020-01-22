package com.example.noteproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NoteAdapter extends RecyclerView.Adapter<NoteVH> {
    LinkedList<Note> notes;
    Context context;

    NoteAdapter(LinkedList<Note> notes, Context context) {
        this.context = context;
        this.notes = notes;
    }

    @NonNull
    @Override
    public NoteVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.note_list_row, parent, false);
        return new NoteVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteVH holder, int position) {
        holder.bindData(notes.get(position));
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }
}

class NoteVH extends RecyclerView.ViewHolder {
    TextView title, lastUpdate;

    public NoteVH(@NonNull View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.note_row_title);
        lastUpdate = itemView.findViewById(R.id.note_row_last_update);
    }

    void bindData (Note note) {
        this.title.setText(note.title);
//        this.lastUpdate.setText(new SimpleDateFormat("yyyy/mm/dd").format(new Date(note.lastUpdate)));
    }
}

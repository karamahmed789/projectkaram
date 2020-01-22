package com.example.noteproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;
import java.util.LinkedList;

public class NotesActivity extends AppCompatActivity {
    FirebaseDatabaseAdapter DBAdapter;
    LinkedList<Note> notes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        this.notes = new LinkedList<>();

        final NoteAdapter adapter = new NoteAdapter(this.notes, getApplicationContext());

        DBAdapter = FirebaseDatabaseAdapter.getInstance(getApplicationContext());
        DBAdapter.getAllNotes().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                notes.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Note note = snapshot.getValue(Note.class);

                    notes.add(note);
                }

                adapter.notifyDataSetChanged();
                Toast.makeText(NotesActivity.this, "" + notes.size(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(NotesActivity.this, "error while fetching data", Toast.LENGTH_SHORT).show();
            }
        });
//        this.notes = new LinkedList<>();

        RecyclerView recyclerView = findViewById(R.id.listnote);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(adapter);

        findViewById(R.id.addnote).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
    }

    void showDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
        View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.add_note, null);
        builder.setView(view);
        final Dialog dialog = builder.create();

        final EditText title = view.findViewById(R.id.note_title),
                description = view.findViewById(R.id.note_description);

        view.findViewById(R.id.save_note).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Note note = new Note();
                note.lastUpdate = new Date().getTime();
                note.createAt = new Date().getTime();
                note.title = title.getText().toString();
                note.description = description.getText().toString();

                DBAdapter.addNote(note);
                dialog.dismiss();
            }
        });

        view.findViewById(R.id.add_note_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
}

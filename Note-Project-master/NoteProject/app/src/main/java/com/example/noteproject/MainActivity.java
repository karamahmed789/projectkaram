package com.example.noteproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;
import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {
    FirebaseDatabaseAdapter DBAdapter;
    LinkedList<Note> notes;
    LinkedList<NoteBook> noteBooks;

    int color;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.notes = new LinkedList<>();
        this.noteBooks = new LinkedList<>();

        final NoteAdapter noteAdapter = new NoteAdapter(this.notes, this);
        final NotebookAdapter notebookAdapter = new NotebookAdapter(this.noteBooks, this);

        DBAdapter = FirebaseDatabaseAdapter.getInstance(this);
        DBAdapter.getAllNotes().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                notes.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Note note = snapshot.getValue(Note.class);

                    notes.add(note);
                }

                noteAdapter.notifyDataSetChanged();
                Toast.makeText(MainActivity.this, "" + notes.size(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, "error while fetching data", Toast.LENGTH_SHORT).show();
            }
        });

        DBAdapter.getAllNoteBook().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                noteBooks.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    NoteBook noteBook = snapshot.getValue(NoteBook.class);
                    noteBooks.add(noteBook);
                }

                notebookAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, "error while fetching data", Toast.LENGTH_SHORT).show();
            }
        });

        RecyclerView notesView = findViewById(R.id.notes_list),
                noteBooksView = findViewById(R.id.notebooks_list);


        notesView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        noteBooksView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));

        notesView.setAdapter(noteAdapter);
        noteBooksView.setAdapter(notebookAdapter);

        findViewById(R.id.add_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddNoteDialog();
            }
        });

        findViewById(R.id.add_notebook).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddNotebookDialog();
            }
        });

        findViewById(R.id.show_note).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, NotesActivity.class));
            }
        });
    }

    void showAddNoteDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        View view = LayoutInflater.from(this).inflate(R.layout.add_note, null);
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

//        dialog = builder.create();
        dialog.show();
    }

    void showAddNotebookDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        View view = LayoutInflater.from(this).inflate(R.layout.add_notebook, null);
        builder.setView(view);
        final Dialog dialog = builder.create();

        color = R.drawable.book_blue;
        final int [][] colors = {
                { R.drawable.book_blue, R.drawable.oval_blue },
                { R.drawable.book_orange, R.drawable.oval_orange },
                { R.drawable.book_green, R.drawable.oval_green },
                { R.drawable.book_red, R.drawable.oval_red }
        };

        final EditText title = view.findViewById(R.id.add_notebook_name);
        final ImageView img = view.findViewById(R.id.add_notebook_color);

        view.findViewById(R.id.notebook_color_blue).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img.setBackgroundResource(colors[0][0]);
                color = colors[0][0];
            }
        });

        view.findViewById(R.id.notebook_color_orange).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img.setBackgroundResource(colors[1][0]);
                color = colors[1][0];
            }
        });

        view.findViewById(R.id.notebook_color_green).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img.setBackgroundResource(colors[2][0]);
                color = colors[2][0];
            }
        });

        view.findViewById(R.id.notebook_color_red).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img.setBackgroundResource(colors[3][0]);
                color = colors[3][0];
            }
        });

        view.findViewById(R.id.add_notebook_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NoteBook noteBook = new NoteBook();
                noteBook.color = color + "";
                noteBook.name = title.getText().toString();
                noteBook.notes = new LinkedList<>();

                DBAdapter.addNoteBook(noteBook);
                dialog.dismiss();
            }
        });

        view.findViewById(R.id.add_notebook_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }


}

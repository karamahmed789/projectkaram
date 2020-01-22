package com.example.noteproject;

import android.content.Context;
import android.widget.Toast;

import java.util.LinkedList;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;

class Note {
    public String id;
    public String title;
    public String description;
    public long lastUpdate;
    public long createAt;
}

class NoteBook {
    public String id;
    public String color;
    public String name;
    public LinkedList<Note> notes;
}

public class FirebaseDatabaseAdapter {
    private static FirebaseDatabaseAdapter instance;
    private Context context;
    private LinkedList<Note> notes;
    private LinkedList<NoteBook> noteBooks;

    private String notebookUrl = "NoteBook", noteUrl = "Note";

    private FirebaseDatabaseAdapter(Context context) {
        notes = new LinkedList<>();
        noteBooks = new LinkedList<>();
        this.context = context;
    }

    public static FirebaseDatabaseAdapter getInstance(Context context) {
        if(instance == null)
            instance = new FirebaseDatabaseAdapter(context);

        return instance;
    }

    DatabaseReference getAllNotes () {
        return getDatabase().child(noteUrl);
    }

    DatabaseReference getAllNoteBook() {
        return getDatabase().child(noteUrl);
    }

    void addNote(Note note) {

        String id = getDatabase().child(noteUrl).push().getKey();
        note.id = id;
        getDatabase().child(noteUrl).child(id).setValue(note);
    }

    void putNote(Note note) {
        getDatabase().child(noteUrl).child(note.id).setValue(note);
    }

    void addNoteBook(NoteBook noteBook) {
        noteBook.id = getDatabase().child(notebookUrl).push().getKey();
        getDatabase().child(notebookUrl).child(noteBook.id).setValue(noteBook);
    }

    private DatabaseReference getDatabase() {
        return FirebaseDatabase.getInstance().getReference();
    }
}

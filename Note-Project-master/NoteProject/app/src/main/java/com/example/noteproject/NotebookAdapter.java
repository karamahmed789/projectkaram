package com.example.noteproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.LinkedList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

class NotebookAdapter extends RecyclerView.Adapter<NotebookVH> {
    LinkedList<NoteBook> noteBooks;
    Context context;

    NotebookAdapter(LinkedList<NoteBook> noteBooks, Context context) {
        this.context = context;
        this.noteBooks = noteBooks;
    }

    @NonNull
    @Override
    public NotebookVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.notebooks_row, parent, false);
        return new NotebookVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotebookVH holder, int position) {
        holder.bindData(noteBooks.get(position));
    }

    @Override
    public int getItemCount() {
        return noteBooks.size();
    }
}

class NotebookVH extends RecyclerView.ViewHolder {
    TextView text;
    ImageView img;

    public NotebookVH(@NonNull View itemView) {
        super(itemView);
        text = itemView.findViewById(R.id.notebooks_row_name);
        img = itemView.findViewById(R.id.notebooks_row_img);
    }

    public void bindData(NoteBook noteBook) {
//        this.img.setBackgroundResource(Integer.parseInt(noteBook.color));
        Toast.makeText(itemView.getContext(), noteBook.color, Toast.LENGTH_LONG).show();
        this.img.setBackgroundResource(Integer.parseInt(R.drawable.book_orange + ""));
        this.text.setText(noteBook.name);
    }
}

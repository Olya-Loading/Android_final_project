package com.example.final_project;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BooksAdapter  extends RecyclerView.Adapter<BooksAdapter.BooksViewHolder> {
    private List<Book> books;
    private OnBookClickListener onBookClickListener;
    public interface OnBookClickListener {
        void onBookClick(Book book);
    }
    public BooksAdapter(List<Book> books, OnBookClickListener listener) {
        this.books = books;
        this.onBookClickListener = listener;
    }
    @NonNull
    @Override
    public BooksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        return new BooksViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BooksViewHolder holder, int position) {
        Book book = books.get(position);
        holder.textView.setText(book.getTitle());
        holder.itemView.setOnClickListener(v -> onBookClickListener.onBookClick(book));
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    static class BooksViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public BooksViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(android.R.id.text1);
        }
    }
}


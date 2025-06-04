package com.example.final_project;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Objects;

public class MainActivity3 extends AppCompatActivity {
    private static final int REQUEST_PERMISSION_WRITE_EXTERNAL_STORAGE = 1;
    private BooksAdapter booksAdapter;
    Button button1;
    Button button2;
    Cursor userCursor;
    private RecyclerView recyclerViewNotes;
    private LinkedList<Book> list_book;
    private LinearLayoutManager linearLayoutManager;
    private SQL_helper sqlHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main3);

        button1 = findViewById(R.id.create_book);
        button2 = findViewById(R.id.back_b);
        sqlHelper = new SQL_helper(this);
        if(list_book==null){
            list_book = sqlHelper.getAllBooks();
        }

        recyclerViewNotes = findViewById(R.id.recyclerViewBooks);
        if (booksAdapter == null){
            booksAdapter = new BooksAdapter(list_book, book -> readBook(book.getTitle()));
        }
        if(linearLayoutManager==null) {
            linearLayoutManager =new LinearLayoutManager(getApplicationContext());
        }

        recyclerViewNotes.setLayoutManager(linearLayoutManager);
        recyclerViewNotes.setAdapter(booksAdapter);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        button1.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity3.this,MainActivity2.class);
            startActivity(intent);
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity3.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
    private void readBook( String title) {
        Intent intent = new Intent(MainActivity3.this,MainActivity2.class);
        intent.putExtra("Book",title);
        startActivity(intent);
    }
    private void loadBook() {
        booksAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if(list_book==null){
            list_book = sqlHelper.getAllBooks();
        }

        loadBook();
    }
    @Override
    protected void onResume() {
        super.onResume();
        if(list_book==null){
            list_book = sqlHelper.getAllBooks();
            }
        loadBook();
        }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION_WRITE_EXTERNAL_STORAGE && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            loadBook();
        } else {
            Toast.makeText(this, "Разрешение на запись не предоставлено", Toast.LENGTH_SHORT).show();
        }
    }
}

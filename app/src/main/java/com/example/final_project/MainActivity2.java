package com.example.final_project;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class MainActivity2 extends AppCompatActivity {
    private static final int REQUEST_PERMISSION_WRITE_EXTERNAL_STORAGE = 1;
    private EditText Book_title;
    private EditText Book_text;
    private Button Save_button;
    private Button back;
    private Button delete;
    private Button edit;
    private SQL_helper sqlHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);
        Book_title = findViewById(R.id.title);
        Book_text = findViewById(R.id.text);
        Save_button = findViewById(R.id.save_book);
        back = findViewById(R.id.back);
        delete = findViewById(R.id.delete_book);
        edit = findViewById(R.id.edit_book);
        sqlHelper = new SQL_helper(this);


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Bundle arguments = getIntent().getExtras();

        if(arguments!=null){
            String book = arguments.get("Book").toString();
            Book_title.setText(book);

            try {
                FileInputStream file = new FileInputStream(new File(getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS),  book +".txt"));
                byte[] text = new byte[(int) file.available()];
                file.read(text);
                file.close();
                String content = new String(text);
                Book_text.setText(content);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity2.this,MainActivity3.class);
                startActivity(intent);
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = Book_title.getText().toString();
                File file = new File(title);
                file.delete();
                sqlHelper.deleteBook(title);
                if(!file.exists() && !file.isDirectory()) {
                    Toast.makeText(MainActivity2.this, "Заметка успешно удалена", Toast.LENGTH_SHORT).show();
                }
            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = Book_title.getText().toString();
                String text = Book_text.getText().toString();
                File file_dir = new File(String.valueOf(getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)));
                if (!title.isEmpty() & !text.isEmpty()) {
                    try {

                        FileWriter file = new FileWriter(title + ".txt");
                        file.write(Arrays.toString(text.getBytes()));
                        file.close();
                        Toast.makeText(MainActivity2.this, "Сохранено", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity2.this,MainActivity3.class);
                        startActivity(intent);
                    } catch (FileNotFoundException e) {
                        Toast.makeText(MainActivity2.this, "Ошибка при сохранении заметки", Toast.LENGTH_SHORT).show();
                        throw new RuntimeException(e);
                    } catch (IOException e) {
                        Toast.makeText(MainActivity2.this, "Название и содержимое заметки не могут быть пустыми", Toast.LENGTH_SHORT).show();
                        throw new RuntimeException(e);

                    }
                }
            }
        });
        Save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = Book_title.getText().toString();
                String text = Book_text.getText().toString();
                Book book = new Book(title);
                sqlHelper.addBook(title);
                if (!title.isEmpty() & !text.isEmpty()) {
                    try {
                        FileOutputStream file = new FileOutputStream(new File(getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), title + ".txt"));
                        file.write(text.getBytes());
                        file.close();
                        Toast.makeText(MainActivity2.this, "Сохранено", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity2.this,MainActivity3.class);
                        intent.putExtra("Book",title);
                        startActivity(intent);
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(MainActivity2.this, "Ошибка при сохранении заметки", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity2.this, "Название и содержимое заметки не могут быть пустыми", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }}
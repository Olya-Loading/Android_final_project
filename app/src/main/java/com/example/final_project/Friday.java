package com.example.final_project;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Friday extends AppCompatActivity {
    private Button b1;
    private  Button b2;
    private EditText text;
    private TextView text2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_friday);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        b1 = findViewById(R.id.button);
        b2 = findViewById(R.id.button_back);
        text = findViewById(R.id.editText1);
        text2 = findViewById(R.id.text);
        if(text2.getText().toString() != null){
            try {
                FileInputStream fis = new FileInputStream(new File(getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS),  "Friday.txt"));
                byte[] text = new byte[(int) fis.available()];
                fis.read(text);
                fis.close();
                String content = new String(text);
                text2.setText(content);
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Ошибка при чтении заметки", Toast.LENGTH_SHORT).show();
            }
        }
        b1.setOnClickListener(v -> {
            try {
                FileOutputStream file = new FileOutputStream(new File(getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "Friday.txt"));
                file.write(text.getText().toString().getBytes());
                file.close();
                String content = new String(text.getText().toString());
                text2.setText(content);
                Toast.makeText(Friday.this, "Сохранено", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                Toast.makeText(Friday.this, "Ошибка при сохранении заметки", Toast.LENGTH_SHORT).show();
                throw new RuntimeException(e);
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Friday.this,Timetable.class);
                startActivity(intent);
            }
        });

    }
}
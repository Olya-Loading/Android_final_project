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

public class Wednesday extends AppCompatActivity {
    private Button b1;
    private  Button b2;
    private EditText text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_wednesday);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        b1 = findViewById(R.id.button);
        b2 = findViewById(R.id.button_back);
        text = findViewById(R.id.editText1);

        try {
            FileInputStream file = new FileInputStream(new File(getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS),  "Wednesday.txt"));

            byte[] text1 = new byte[(int) file.available()];
            file.read(text1);
            file.close();
            String content = new String(text1);
            text.setText(content);

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Ошибка при чтении заметки", Toast.LENGTH_SHORT).show();
        }


        b1.setOnClickListener(v -> {
            try {
                FileOutputStream file = new FileOutputStream(new File(getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "Wednesday.txt"));
                file.write(text.getText().toString().getBytes());
                file.close();
                String content = new String(text.getText().toString());
                Toast.makeText(Wednesday.this, "Сохранено", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                Toast.makeText(Wednesday.this, "Ошибка при сохранении заметки", Toast.LENGTH_SHORT).show();
                throw new RuntimeException(e);
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Wednesday.this,Timetable.class);
                startActivity(intent);
            }
        });
    }
}
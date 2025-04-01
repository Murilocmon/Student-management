package com.example.studentmanagement;

import android.util.Log;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.example.studentmanagement.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private StudentDatabaseHelper dbHelper;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Inicializa o banco de dados
        dbHelper = new StudentDatabaseHelper(this);

        // Configurar o clique do botão para abrir a AddStudentActivity
        binding.buttonAddStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddStudentActivity.class);
                startActivity(intent);
            }
        });

        binding.buttonViewStudents.setOnClickListener(v -> {
            try {
                Intent intent = new Intent(MainActivity.this, StudentListActivity.class);
                startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }
}
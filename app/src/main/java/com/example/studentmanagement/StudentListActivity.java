package com.example.studentmanagement;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import androidx.appcompat.app.AppCompatActivity;
import com.example.studentmanagement.databinding.ActivityStudentListBinding;

import java.util.List;

public class StudentListActivity extends AppCompatActivity {
    private ActivityStudentListBinding binding;
    private StudentDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStudentListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        dbHelper = new StudentDatabaseHelper(this);

        // Pega a lista de alunos do banco de dados
        List<Student> students = dbHelper.getAllStudents();

        // Configura o adapter para o ListView
        ArrayAdapter<Student> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1, students);
        binding.listViewStudents.setAdapter(adapter);
    }
}
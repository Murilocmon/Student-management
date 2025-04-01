package com.example.studentmanagement;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.studentmanagement.databinding.ActivityAddStudentBinding;

public class AddStudentActivity extends AppCompatActivity {
    private ActivityAddStudentBinding binding;
    private StudentDatabaseHelper dbHelper; // Corrigido: Usar a classe correta

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddStudentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Corrigido: Usar StudentDatabaseHelper em vez de MainActivity
        dbHelper = new StudentDatabaseHelper(this);

        binding.buttonSave.setOnClickListener(v -> {
            try {
                String name = binding.editTextName.getText().toString().trim();
                String gradeStr = binding.editTextGrade.getText().toString().trim();

                // Validações
                if (name.isEmpty()) {
                    binding.buttonSave.setError("Name is required");
                    return;
                }

                if (gradeStr.isEmpty()) {
                    binding.buttonSave.setError("Grade is required");
                    return;
                }

                double grade = Double.parseDouble(gradeStr);
                if (grade < 0 || grade > 10) {
                    binding.buttonSave.setError("Grade must be 0-10");
                    return;
                }

                // Cria e adiciona o aluno
                Student student = new Student(name, grade);
                dbHelper.addStudent(student);

                Toast.makeText(this, "Student added successfully!", Toast.LENGTH_SHORT).show();
                finish();

            } catch (NumberFormatException e) {
                binding.buttonSave.setError("Invalid grade format");
            }
        });
    }

    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }
}
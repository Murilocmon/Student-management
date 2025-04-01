package com.example.studentmanagement;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.studentmanagement.databinding.ActivityEditStudentBinding;

public class EditStudentActivity extends AppCompatActivity {
    private ActivityEditStudentBinding binding;
    private StudentDatabaseHelper dbHelper; // Usando a classe correta
    private Student currentStudent; // Mantém referência ao aluno atual

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditStudentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Inicializa o banco de dados corretamente
        dbHelper = new StudentDatabaseHelper(this);

        // Obtém o ID do aluno
        int studentId = getIntent().getIntExtra("STUDENT_ID", -1);
        if (studentId == -1) {
            Toast.makeText(this, "Invalid student ID", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Carrega os dados do aluno
        loadStudentData(studentId);

        // Configura os botões
        setupButtons();
    }

    private void loadStudentData(int studentId) {
        // Busca o aluno no banco de dados
        currentStudent = dbHelper.getStudentById(studentId);
        if (currentStudent == null) {
            Toast.makeText(this, "Student not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Preenche os campos
        binding.editTextName.setText(currentStudent.getName());
        binding.editTextGrade.setText(String.valueOf(currentStudent.getGrade()));
    }

    private void setupButtons() {
        binding.buttonUpdate.setOnClickListener(v -> updateStudent());
        binding.buttonDelete.setOnClickListener(v -> deleteStudent());
    }

    private void updateStudent() {
        try {
            String name = binding.editTextName.getText().toString().trim();
            double grade = Double.parseDouble(binding.editTextGrade.getText().toString());

            // Validações
            if (name.isEmpty()) {
                binding.editTextName.setError("Name is required");
                return;
            }

            if (grade < 0 || grade > 10) {
                binding.editTextGrade.setError("Grade must be 0-10");
                return;
            }

            // Atualiza o aluno
            currentStudent.setName(name);
            currentStudent.setGrade(grade);
            dbHelper.updateStudent(currentStudent);

            Toast.makeText(this, "Student updated", Toast.LENGTH_SHORT).show();
            finish();

        } catch (NumberFormatException e) {
            binding.editTextGrade.setError("Invalid grade format");
        }
    }

    private void deleteStudent() {
        dbHelper.deleteStudent(currentStudent.getId());
        Toast.makeText(this, "Student deleted", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }
}

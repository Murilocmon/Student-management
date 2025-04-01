package com.example.studentmanagement;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.studentmanagement.databinding.ItemStudentBinding;
import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {
    private List<Student> studentList;
    private Context context;

    public StudentAdapter(List<Student> studentList, Context context) {
        this.studentList = studentList;
        this.context = context;
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemStudentBinding binding = ItemStudentBinding.inflate(
                LayoutInflater.from(context), parent, false);
        return new StudentViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        Student student = studentList.get(position);
        holder.binding.textInputName.setText(student.getName());
        holder.binding.textViewGrade.setText("Nota: " + student.getGrade());

        // Ao clicar no aluno, abre a tela de edição
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, EditStudentActivity.class);
            intent.putExtra("STUDENT_ID", student.getId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    public static class StudentViewHolder extends RecyclerView.ViewHolder {
        ItemStudentBinding binding;

        public StudentViewHolder(@NonNull ItemStudentBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
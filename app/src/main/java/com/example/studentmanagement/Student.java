package com.example.studentmanagement;

public class Student {
    private int id;
    private String name;
    private double grade;

    public Student(String name, double grade) {
        this.name = name;
        this.grade = grade;
    }

    // Construtor para quando vem do banco de dados
    public Student(int id, String name, double grade) {
        this.id = id;
        this.name = name;
        this.grade = grade;
    }

    // Getters e Setters
    public int getId() { return id; }
    public String getName() { return name; }
    public double getGrade() { return grade; }
    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setGrade(double grade) { this.grade = grade; }

    // Sobrescrever o método toString()
    @Override
    public String toString() {
        return name + " - Nota: " + grade;
    }
}

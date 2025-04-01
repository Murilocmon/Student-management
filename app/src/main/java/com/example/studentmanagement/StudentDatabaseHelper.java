package com.example.studentmanagement;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class StudentDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "students.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_STUDENTS = "students";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_GRADE = "grade";

    public StudentDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_STUDENTS_TABLE = "CREATE TABLE " + TABLE_STUDENTS +
                "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_NAME + " TEXT," +
                COLUMN_GRADE + " REAL" +
                ")";
        db.execSQL(CREATE_STUDENTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENTS);
        onCreate(db);
    }

    public void addStudent(Student student) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, student.getName());
        values.put(COLUMN_GRADE, student.getGrade());
        db.insert(TABLE_STUDENTS, null, values);
        db.close();
    }

    public List<Student> getAllStudents() {
        List<Student> studentList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_STUDENTS, null);

        if (cursor.moveToFirst()) {
            do {
                Student student = new Student(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getDouble(2));
                studentList.add(student);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return studentList;
    }

    public void updateStudent(Student student) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, student.getName());
        values.put(COLUMN_GRADE, student.getGrade());
        db.update(TABLE_STUDENTS, values, COLUMN_ID + " = ?",
                new String[]{String.valueOf(student.getId())});
        db.close();
    }

    public void deleteStudent(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_STUDENTS, COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
    }
    public Student getStudentById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Student student = null;

        Cursor cursor = db.query(
                TABLE_STUDENTS,
                new String[]{COLUMN_ID, COLUMN_NAME, COLUMN_GRADE},
                COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)},
                null, null, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                student = new Student(
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)),
                        cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_GRADE))
                );
            }
            cursor.close();
        }
        db.close();
        return student;
    }
}

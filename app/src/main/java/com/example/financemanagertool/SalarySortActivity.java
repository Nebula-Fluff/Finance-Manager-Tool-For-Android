package com.example.financemanagertool;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class SalarySortActivity extends AppCompatActivity {

    // 声明控件变量
    Button btnStartSalarySort;
    EditText salaryInput;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salary_sort);

        btnStartSalarySort = findViewById(R.id.btn_start_salary_sort);
        salaryInput = findViewById(R.id.salary_input);

    }
}
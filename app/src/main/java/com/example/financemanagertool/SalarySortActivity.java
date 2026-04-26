package com.example.financemanagertool;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SalarySortActivity extends AppCompatActivity {

    // 声明控件变量
    Button btnStartSalarySort;
    EditText etSalaryInput;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salary_sort);

        // ID绑定
        btnStartSalarySort = findViewById(R.id.btn_start_salary_sort);
        etSalaryInput = findViewById(R.id.et_salary_input);

        // 设置按钮点击事件
        btnStartSalarySort.setOnClickListener(v -> {
            String salary = etSalaryInput.getText().toString();
            // 显示Toast提示
            Toast.makeText(this, "操作成功（开发中）", Toast.LENGTH_SHORT).show();
            // 关闭当前Activity
            finish();
        });
    }
}
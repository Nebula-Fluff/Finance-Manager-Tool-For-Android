package com.example.financemanagertool;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SalarySortActivity extends AppCompatActivity {

    Button btnStartSalarySort;
    EditText etSalaryInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salary_sort);

        btnStartSalarySort = findViewById(R.id.btn_start_salary_sort);
        etSalaryInput = findViewById(R.id.et_salary_input);

        btnStartSalarySort.setOnClickListener(v -> {
            String salary = etSalaryInput.getText().toString().trim();

            // 判断输入是否为空
            if (salary.isEmpty()) {
                Toast.makeText(this, "请输入薪资", Toast.LENGTH_SHORT).show();
                return;
            }

            // 判断输入是否为合法数字
            try {
                new java.math.BigDecimal(salary);
            } catch (Exception e) {
                Toast.makeText(this, "请输入正确的金额格式", Toast.LENGTH_SHORT).show();
                return;
            }

            // 调用 FinanceManager 执行入账
            FinanceManager manager = new FinanceManager(this);
            boolean success = manager.salarySortAndWrite(salary);

            if (success) {
                Toast.makeText(this, "入账成功", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "入账失败，请检查数据", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
package com.example.financemanagertool;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    // 声明控件变量
    Button btnSalarySort;
    Button btnManualChangeBalance;
    Button btnCheckBalance;
    Button btnCheckSalarySortLog;
    Button btnCheckBalanceChangeLog;
    Button btnDataIOFunc;
    Button btnFuncTest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ConfigManager.init(this);

        // ID绑定
        btnSalarySort = findViewById(R.id.btn_salary_sort);
        btnManualChangeBalance = findViewById(R.id.btn_manual_change_balance);
        btnCheckBalance = findViewById(R.id.btn_check_balance);
        btnCheckSalarySortLog = findViewById(R.id.btn_check_salary_sort_log);
        btnCheckBalanceChangeLog = findViewById(R.id.btn_check_balance_change_log);
        btnDataIOFunc = findViewById(R.id.btn_data_io_func);
        btnFuncTest = findViewById(R.id.btn_func_test);

        // 设置按钮点击事件
        btnSalarySort.setOnClickListener(v -> {
            Intent salarySortActivity = new Intent(this, SalarySortActivity.class);
            startActivity(salarySortActivity);
        });

        btnManualChangeBalance.setOnClickListener(v -> {
            Intent manualChangeBalanceActivity = new Intent(this, ManualChangeBalanceActivity.class);
            startActivity(manualChangeBalanceActivity);
        });

        btnCheckBalance.setOnClickListener(v -> {
            Intent checkBalanceActivity = new Intent(this, CheckBalanceActivity.class);
            startActivity(checkBalanceActivity);
        });

        btnCheckSalarySortLog.setOnClickListener(v -> {
            Intent checkDataBase = new Intent(this, CheckSalarySortLogActivity.class);
            startActivity(checkDataBase);
        });

        btnCheckBalanceChangeLog.setOnClickListener(v -> {
            Intent checkDataBase = new Intent(this, CheckBalanceChangeLogActivity.class);
            startActivity(checkDataBase);
        });

        btnDataIOFunc.setOnClickListener(v -> {
            Intent dataIOFuncActivity = new Intent(this, DataIOFuncActivity.class);
            startActivity(dataIOFuncActivity);
        });

        btnFuncTest.setOnClickListener(v -> {
            Intent funcTestActivity = new Intent(this, FuncTestActivity.class);
            startActivity(funcTestActivity);
        });
    }
}
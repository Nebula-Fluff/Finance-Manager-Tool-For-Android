package com.example.financemanagertool;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ManualChangeBalanceActivity extends AppCompatActivity {

    // 声明控件变量
    TextView accountInput;
    TextView amountInput;
    Button btnChangeBalance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_change_balance);

        // ID绑定
        accountInput = findViewById(R.id.account_input);
        amountInput = findViewById(R.id.amount_input);
        btnChangeBalance = findViewById(R.id.btn_change_balance);

        // 设置按钮点击事件
        btnChangeBalance.setOnClickListener(v -> {
            // 未完善，待开发
            String account = accountInput.getText().toString();
            String amount = amountInput.getText().toString();
            // 显示Toast提示
            Toast.makeText(this, "操作成功（开发中）", Toast.LENGTH_SHORT).show();
            // 关闭当前Activity
            finish();
        });
    }
}
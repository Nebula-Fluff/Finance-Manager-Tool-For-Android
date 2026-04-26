package com.example.financemanagertool;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CheckBalanceActivity extends AppCompatActivity {

    // 声明控件变量
    TextView securityFundValue;
    TextView securityFundPercent;
    TextView emergencyFundValue;
    TextView emergencyFundPercent;
    TextView healthFundValue;
    TextView overflowValue;
    TextView flexValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_balance);

        // ID绑定
        securityFundValue = findViewById(R.id.tv_security_fund_value);
        securityFundPercent = findViewById(R.id.tv_security_fund_percent);
        emergencyFundValue = findViewById(R.id.tv_emergency_fund_value);
        emergencyFundPercent = findViewById(R.id.tv_emergency_fund_percent);
        healthFundValue = findViewById(R.id.tv_health_fund_value);
        overflowValue = findViewById(R.id.tv_overflow_value);
        flexValue = findViewById(R.id.tv_flex_fund_value);

    }
}
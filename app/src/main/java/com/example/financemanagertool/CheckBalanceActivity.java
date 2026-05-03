package com.example.financemanagertool;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.math.BigDecimal;
import java.util.Map;

public class CheckBalanceActivity extends AppCompatActivity {

    TextView securityFundValue;
    TextView securityFundPercent;
    TextView emergencyFundValue;
    TextView emergencyFundPercent;
    TextView healthFundValue;
    TextView healthFundPercent;
    TextView overflowValue;
    TextView flexValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_balance);

        securityFundValue = findViewById(R.id.tv_security_fund_value);
        securityFundPercent = findViewById(R.id.tv_security_fund_percent);
        emergencyFundValue = findViewById(R.id.tv_emergency_fund_value);
        emergencyFundPercent = findViewById(R.id.tv_emergency_fund_percent);
        healthFundValue = findViewById(R.id.tv_health_fund_value);
        healthFundPercent = findViewById(R.id.tv_health_fund_percent);
        overflowValue = findViewById(R.id.tv_overflow_value);
        flexValue = findViewById(R.id.tv_flex_fund_value);

        // 从数据库取余额
        FinanceDB db = new FinanceDB(this);
        Map<String, String> balances = db.getBalances();

        // 从配置取限额
        String securityLimit = ConfigManager.data.getAsJsonObject("limits").get("Security").getAsString();
        String emergencyLimit = ConfigManager.data.getAsJsonObject("limits").get("Emergency").getAsString();
        String healthLimit = ConfigManager.data.getAsJsonObject("limits").get("Health").getAsString();

        // 取余额值
        String securityVal = balances.getOrDefault("Security", "0");
        String emergencyVal = balances.getOrDefault("Emergency", "0");
        String healthVal = balances.getOrDefault("Health", "0");
        String overflowVal = balances.getOrDefault("Overflow", "0");
        String flexVal = balances.getOrDefault("Flex", "0");

        // 显示余额
        securityFundValue.setText(securityVal);
        emergencyFundValue.setText(emergencyVal);
        healthFundValue.setText(healthVal);
        overflowValue.setText(overflowVal);
        flexValue.setText(flexVal);

        // 计算并显示百分比
        securityFundPercent.setText(calcPercent(securityVal, securityLimit));
        emergencyFundPercent.setText(calcPercent(emergencyVal, emergencyLimit));
        healthFundPercent.setText(calcPercent(healthVal, healthLimit));
    }

    // 计算百分比，对应你 Python 里进度条的逻辑
    private String calcPercent(String current, String limit) {
        try {
            BigDecimal cur = new BigDecimal(current);
            BigDecimal lim = new BigDecimal(limit);
            if (lim.compareTo(BigDecimal.ZERO) <= 0) return "N/A";
            BigDecimal percent = cur.divide(lim, 4, BigDecimal.ROUND_HALF_UP)
                    .multiply(new BigDecimal("100"))
                    .setScale(1, BigDecimal.ROUND_HALF_UP);
            return percent + "%";
        } catch (Exception e) {
            return "N/A";
        }
    }
}
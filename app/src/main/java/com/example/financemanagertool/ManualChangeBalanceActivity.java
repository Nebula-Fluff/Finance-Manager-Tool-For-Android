package com.example.financemanagertool;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import androidx.appcompat.app.AppCompatActivity;

public class ManualChangeBalanceActivity extends AppCompatActivity {

    TextView tvAccountInput;
    EditText etAmountInput;
    EditText etRemarkInput;
    Button btnChangeBalance;

    String selectedAccount = null; // 记录用户选了哪个账户

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_change_balance);

        tvAccountInput = findViewById(R.id.tv_account_input);
        etAmountInput = findViewById(R.id.et_amount_input);
        etRemarkInput = findViewById(R.id.et_remark_input);
        btnChangeBalance = findViewById(R.id.btn_change_balance);

        // 从数据库取账户列表
        FinanceDB db = new FinanceDB(this);
        Map<String, String> balances = db.getBalances();
        String[] accounts = balances.keySet().toArray(new String[0]);

        // 点击账户框弹出选择
        tvAccountInput.setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                    .setTitle("选择账户")
                    .setItems(accounts, (dialog, which) -> {
                        selectedAccount = accounts[which];
                        tvAccountInput.setText(selectedAccount);
                    })
                    .show();
        });

        // 提交按钮
        btnChangeBalance.setOnClickListener(v -> {
            String amount = etAmountInput.getText().toString().trim();
            String remark = etRemarkInput.getText().toString().trim();

            // 校验
            if (selectedAccount == null) {
                Toast.makeText(this, "请选择账户", Toast.LENGTH_SHORT).show();
                return;
            }
            if (amount.isEmpty()) {
                Toast.makeText(this, "请输入金额", Toast.LENGTH_SHORT).show();
                return;
            }
            if (remark.isEmpty()) {
                Toast.makeText(this, "备注不能为空", Toast.LENGTH_SHORT).show();
                return;
            }
            try {
                new java.math.BigDecimal(amount);
            } catch (Exception e) {
                Toast.makeText(this, "金额格式有误", Toast.LENGTH_SHORT).show();
                return;
            }

            String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
            boolean success = db.changeBalance(selectedAccount, amount, date, remark);

            if (success) {
                Toast.makeText(this, "操作成功", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "操作失败", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
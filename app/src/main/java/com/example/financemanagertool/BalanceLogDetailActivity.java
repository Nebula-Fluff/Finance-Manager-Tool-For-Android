package com.example.financemanagertool;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class BalanceLogDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance_log_detail);

        // 接收从上一页传来的数据
        String receivedId = getIntent().getStringExtra("row_id");
        String receivedDate = getIntent().getStringExtra("row_date");
        String receivedAccount = getIntent().getStringExtra("row_account");
        String receivedChange = getIntent().getStringExtra("row_change");
        String receivedRemark = getIntent().getStringExtra("row_remark");

        // 将接收到的数据显示在对应的TextView中
        TextView tvReceivedId = findViewById(R.id.tv_id_value);
        TextView tvReceivedDate = findViewById(R.id.tv_date_value);
        TextView tvReceivedAccount = findViewById(R.id.tv_account);
        TextView tvReceivedChange = findViewById(R.id.tv_change_value);
        TextView tvReceivedRemark = findViewById(R.id.tv_remark);

        // 设置TextView的文本内容
        tvReceivedId.setText(receivedId);
        tvReceivedDate.setText(receivedDate);
        tvReceivedAccount.setText(receivedAccount);
        tvReceivedChange.setText(receivedChange);
        tvReceivedRemark.setText(receivedRemark);
    }
}
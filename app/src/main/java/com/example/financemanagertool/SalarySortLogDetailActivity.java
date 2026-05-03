package com.example.financemanagertool;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class SalarySortLogDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salary_sort_log_detail);

        // 接收从上一页传来的数据
        String receivedId = getIntent().getStringExtra("row_id");
        String receivedDate = getIntent().getStringExtra("row_date");
        String receivedSalaryIn = getIntent().getStringExtra("row_salary_in");
        String receivedReserveOut = getIntent().getStringExtra("row_reserve_out");
        String receivedDailyOut = getIntent().getStringExtra("row_daily_out");
        String receivedFlexOut = getIntent().getStringExtra("row_flex_out");
        String receivedFlexA = getIntent().getStringExtra("row_flex_a");
        String receivedFlexB = getIntent().getStringExtra("row_flex_b");
        String receivedSalaryLevel = getIntent().getStringExtra("row_salary_level");
        String receivedReserveRate = getIntent().getStringExtra("row_reserve_rate");
        String receivedFlexAPerDay = getIntent().getStringExtra("row_flex_a_per_day");
        String receivedFlexBPerDay = getIntent().getStringExtra("row_flex_b_per_day");
        String receivedThisMonthDays = getIntent().getStringExtra("row_this_month_days");

        // 将接收到的数据显示在对应的TextView中
        TextView tvReceivedId = findViewById(R.id.tv_id_value);
        TextView tvReceivedDate = findViewById(R.id.tv_date_value);
        TextView tvReceivedSalaryIn = findViewById(R.id.tv_salary_in_value);
        TextView tvReceivedReserveOut = findViewById(R.id.tv_reserve_out_value);
        TextView tvReceivedDailyOut = findViewById(R.id.tv_daily_out_value);
        TextView tvReceivedFlexOut = findViewById(R.id.tv_flex_out_value);
        TextView tvReceivedFlexA = findViewById(R.id.tv_flex_a_out_value);
        TextView tvReceivedFlexB = findViewById(R.id.tv_flex_b_out_value);
        TextView tvReceivedSalaryLevel = findViewById(R.id.tv_salary_level);
        TextView tvReceivedReserveRate = findViewById(R.id.tv_reserve_rate_value);
        TextView tvReceivedFlexAPerDay = findViewById(R.id.tv_flex_a_per_day_value);
        TextView tvReceivedFlexBPerDay = findViewById(R.id.tv_flex_b_per_day_value);
        TextView tvReceivedThisMonthDays = findViewById(R.id.tv_this_month_days_value);

        // 设置TextView的文本内容
        tvReceivedId.setText(receivedId);
        tvReceivedDate.setText(receivedDate);
        tvReceivedSalaryIn.setText(receivedSalaryIn);
        tvReceivedReserveOut.setText(receivedReserveOut);
        tvReceivedDailyOut.setText(receivedDailyOut);
        tvReceivedFlexOut.setText(receivedFlexOut);
        tvReceivedFlexA.setText(receivedFlexA);
        tvReceivedFlexB.setText(receivedFlexB);
        tvReceivedSalaryLevel.setText(receivedSalaryLevel);
        tvReceivedReserveRate.setText(receivedReserveRate);
        tvReceivedFlexAPerDay.setText(receivedFlexAPerDay);
        tvReceivedFlexBPerDay.setText(receivedFlexBPerDay);
        tvReceivedThisMonthDays.setText(receivedThisMonthDays);
    }
}
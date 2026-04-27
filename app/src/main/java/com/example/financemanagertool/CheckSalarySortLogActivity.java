package com.example.financemanagertool;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import java.util.ArrayList;
import java.util.List;

public class CheckSalarySortLogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_salary_sort_log);

        RecyclerView rv = findViewById(R.id.rv_table);
        rv.setLayoutManager(new LinearLayoutManager(this));

        List<String[]> data = new ArrayList<>();
        data.add(new String[]{"1", "2026-04-26", "3500", "500", "510", "520", "530", "540", ">3000", "0.35", "15", "7", "30"});
        data.add(new String[]{"2", "2026-04-26", "3500", "600", "610", "620", "630", "640", ">3000", "0.35", "15", "7", "30"});

        SalarySortLogAdapter adapter = new SalarySortLogAdapter(data, row -> {
            Intent intent = new Intent(this, SalarySortLogDetailActivity.class);
            intent.putExtra("row_id", row[0]);
            intent.putExtra("row_date", row[1]);
            intent.putExtra("row_salary_in", row[2]);
            intent.putExtra("row_reserve_out", row[3]);
            intent.putExtra("row_daily_out", row[4]);
            intent.putExtra("row_flex_out", row[5]);
            intent.putExtra("row_flex_a", row[6]);
            intent.putExtra("row_flex_b", row[7]);
            intent.putExtra("row_salary_level", row[8]);
            intent.putExtra("row_reserve_rate", row[9]);
            intent.putExtra("row_flex_a_per_day", row[10]);
            intent.putExtra("row_flex_b_per_day", row[11]);
            intent.putExtra("row_this_month_days", row[12]);
            startActivity(intent);
        });

        rv.setAdapter(adapter);
    }
}
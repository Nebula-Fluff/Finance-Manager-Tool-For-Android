package com.example.financemanagertool;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import java.util.List;

public class CheckBalanceChangeLogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_balance_change_log);

        FinanceDB db = new FinanceDB(this);

        RecyclerView rv = findViewById(R.id.rv_table);
        rv.setLayoutManager(new LinearLayoutManager(this));

        List<String[]> data = db.getTransactionsLog();

        BalanceLogAdapter adapter = new BalanceLogAdapter(data, row -> {
            Intent intent = new Intent(this, BalanceLogDetailActivity.class);
            intent.putExtra("row_id", row[0]);
            intent.putExtra("row_date", row[1]);
            intent.putExtra("row_account", row[2]);
            intent.putExtra("row_change", row[3]);
            intent.putExtra("row_remark", row[4]);
            startActivity(intent);
        });

        rv.setAdapter(adapter);
    }
}
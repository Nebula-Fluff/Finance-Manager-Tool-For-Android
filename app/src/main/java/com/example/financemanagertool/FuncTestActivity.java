package com.example.financemanagertool;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class FuncTestActivity extends AppCompatActivity {

    Button btnTest1;
    Button btnTest2;

    FinanceDB FinanceDB;
    Map<String, String> balances;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_func_test);

        FinanceDB = new FinanceDB(this);
        balances = FinanceDB.getBalances();

        btnTest1 = findViewById(R.id.btn_test1);
        btnTest2 = findViewById(R.id.btn_test2);

        btnTest1.setOnClickListener(v -> {
            //Toast.makeText(this, "Data:" + FinanceDB.getBalances().toString(), Toast.LENGTH_SHORT).show();
            FinanceDB.changeBalance("Flex", "5678", "2026-05-03", "test test test");
            Toast.makeText(this, "ok", Toast.LENGTH_SHORT).show();
        });
        btnTest2.setOnClickListener(v -> {
            List<String[]> rows = FinanceDB.getTransactionsLog();
            List<Map<String, String>> result = new ArrayList<>();

            for (String[] row : rows) {
                Map<String, String> map = new LinkedHashMap<>();
                map.put("ID", row[0]);
                map.put("date", row[1]);
                map.put("account", row[2]);
                map.put("change_num", row[3]);
                map.put("remark", row[4]);
                result.add(map);
            }

            Toast.makeText(this, result.toString(), Toast.LENGTH_LONG).show();
        });
    }
}
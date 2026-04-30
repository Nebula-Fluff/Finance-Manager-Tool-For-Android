package com.example.financemanagertool;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.util.Log;
import android.widget.Toast;

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
            Toast.makeText(this, "Data:" + FinanceDB.getBalances().toString(), Toast.LENGTH_SHORT).show();
        });
        btnTest2.setOnClickListener(v -> {
            Toast.makeText(this, "Data:" + balances.get("Security"), Toast.LENGTH_SHORT).show();
        });
    }
}
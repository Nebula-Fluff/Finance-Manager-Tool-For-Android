package com.example.financemanagertool;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class FinanceDB extends SQLiteOpenHelper {
    private static final String DB_NAME = "finance.db";
    private static final int DB_VERSION = 1;

    public FinanceDB(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS account_balances (" +
                "account TEXT PRIMARY KEY," +
                "balance TEXT NOT NULL)");

        db.execSQL("CREATE TABLE IF NOT EXISTS transaction_detail (" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "date TEXT, account TEXT, change_num TEXT, remark TEXT)");

        db.execSQL("CREATE TABLE IF NOT EXISTS salary_log (" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "date TEXT, salary_in TEXT, Reserve_val TEXT," +
                "Daily_val TEXT, Flex_total TEXT, Flex_A_val TEXT," +
                "Flex_B_val TEXT, salary_level TEXT, Reserve_rate TEXT," +
                "Flex_A_perday TEXT, Flex_B_perday TEXT, this_month_days TEXT)");

        // 初始化账户余额
        String[] accounts = {"Security", "Emergency", "Health", "Overflow", "Flex"};
        for (String acc : accounts) {
            db.execSQL("INSERT OR IGNORE INTO account_balances VALUES ('" + acc + "', '0')");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 版本升级时调用，现在留空就行
    }

    public Map<String, String> getBalances() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM account_balances", null);
        Map<String, String> result = new LinkedHashMap<>();
        while (cursor.moveToNext()) {
            result.put(cursor.getString(0), cursor.getString(1));
        }
        cursor.close();
        return result;
    }
}

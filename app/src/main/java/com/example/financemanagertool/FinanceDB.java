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
    public List<String[]> getTransactionsLog() {
        // 返回列顺序：[0]ID [1]date [2]account [3]change_num [4]remark
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM transaction_detail ORDER BY ID DESC", null);
        List<String[]> result = new ArrayList<>();
        while (cursor.moveToNext()) {
            String[] row = new String[]{
                    cursor.getString(0), // ID
                    cursor.getString(1), // date
                    cursor.getString(2), // account
                    cursor.getString(3), // change_num
                    cursor.getString(4) // remark
            };
            result.add(row);
        }
        cursor.close();
        return result;
    }

    public List<String[]> getSalaryLog() {
        // 返回列顺序：[0]ID [1]date [2]salary_in [3]Reserve_val [4]Daily_val
        //             [5]Flex_total [6]Flex_A_val [7]Flex_B_val [8]salary_level
        //             [9]Reserve_rate [10]Flex_A_perday [11]Flex_B_perday [12]this_month_days
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM salary_log ORDER BY ID DESC", null);
        List<String[]> result = new ArrayList<>();
        while (cursor.moveToNext()) {
            String[] row = new String[]{
                    cursor.getString(0), // ID
                    cursor.getString(1), // date
                    cursor.getString(2), // salary_in
                    cursor.getString(3), // Reserve_val
                    cursor.getString(4), // Daily_val
                    cursor.getString(5), // Flex_total
                    cursor.getString(6), // Flex_A_val
                    cursor.getString(7), // Flex_B_val
                    cursor.getString(8), // salary_level
                    cursor.getString(9), // Reserve_rate
                    cursor.getString(10), // Flex_A_perday
                    cursor.getString(11), // Flex_B_perday
                    cursor.getString(12) // this_month_days
            };
            result.add(row);
        }
        cursor.close();
        return result;
    }

    public boolean changeBalance(String account, String changeVal, String date, String remark) {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction(); // 开启事务，对应 Python 的 with self.conn
        try {
            // 读当前余额
            Cursor cursor = db.rawQuery(
                    "SELECT balance FROM account_balances WHERE account=?",
                    new String[]{account});

            BigDecimal oldBalance = new BigDecimal("0");
            if (cursor.moveToFirst()) {
                oldBalance = new BigDecimal(cursor.getString(0));
            }
            cursor.close();

            // 计算新余额
            BigDecimal change = new BigDecimal(changeVal);
            BigDecimal newBalance = oldBalance.add(change);

            // 更新余额
            ContentValues values = new ContentValues();
            values.put("balance", newBalance.toString());
            db.update("account_balances", values, "account=?", new String[]{account});

            // 记录流水
            ContentValues log = new ContentValues();
            log.put("date", date);
            log.put("account", account);
            log.put("change_num", changeVal);
            log.put("remark", remark);
            db.insert("transaction_detail", null, log);

            db.setTransactionSuccessful(); // 提交事务
            return true;
        } catch (Exception e) {
            return false; // 事务自动回滚
        } finally {
            db.endTransaction();
        }
    }

    public boolean addSalaryLog(String date, String salaryIn, String reserveVal,
                                String dailyVal, String flexTotal, String flexAVal,
                                String flexBVal, String salaryLevel, String reserveRate,
                                String flexAPerday, String flexBPerday, String thisMonthDays) {
        SQLiteDatabase db = getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put("date", date);
            values.put("salary_in", salaryIn);
            values.put("Reserve_val", reserveVal);
            values.put("Daily_val", dailyVal);
            values.put("Flex_total", flexTotal);
            values.put("Flex_A_val", flexAVal);
            values.put("Flex_B_val", flexBVal);
            values.put("salary_level", salaryLevel);
            values.put("Reserve_rate", reserveRate);
            values.put("Flex_A_perday", flexAPerday);
            values.put("Flex_B_perday", flexBPerday);
            values.put("this_month_days", thisMonthDays);
            db.insert("salary_log", null, values);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}

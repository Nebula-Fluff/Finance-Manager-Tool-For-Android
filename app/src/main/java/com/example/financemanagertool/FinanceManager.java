package com.example.financemanagertool;

import android.content.Context;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Calendar;

public class FinanceManager {

    private FinanceDB db;
    private FinanceCal cal;

    public FinanceManager(Context context) {
        this.db = new FinanceDB(context);
        this.cal = new FinanceCal();
    }

    /**
     * 薪资入账，协调 FinanceCal 和 FinanceDB 完成计算和写入
     * 对应 Python 的 salary_sort_and_write
     * 返回 true 成功，false 失败
     */
    public boolean salarySortAndWrite(String salary) {
        // 自动获取日期和当月天数
        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        int days = Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH);

        // 第一步：分解薪资
        FinanceCal.CalResult result = cal.calcSalary(salary, days);
        if (result == null) return false;

        // 第二步：Reserve 细分
        List<FinanceCal.ReserveChange> reserveChanges = cal.calcReserve(
                result.reserve.toString(),
                db.getBalances()
        );

        // 第三步：写入余额和流水
        String remark = "薪资入账自动分配(总额:" + salary + ")";
        for (FinanceCal.ReserveChange change : reserveChanges) {
            boolean ok = db.changeBalance(change.acc, change.val, date, remark);
            if (!ok) return false;
        }
        boolean ok = db.changeBalance("Flex", result.flexTotal.toString(), date, remark);
        if (!ok) return false;

        // 第四步：写薪资日志
        db.addSalaryLog(
                date, salary,
                result.reserve.toString(),
                result.daily.toString(),
                result.flexTotal.toString(),
                result.flexA.toString(),
                result.flexB.toString(),
                result.salaryLevel,
                result.reserveRate,
                result.flexAPerday,
                result.flexBPerday,
                String.valueOf(days)
        );

        return true;
    }
}
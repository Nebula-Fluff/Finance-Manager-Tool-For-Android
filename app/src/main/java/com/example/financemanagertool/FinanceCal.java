package com.example.financemanagertool;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class FinanceCal {

    // ==================== 公开方法 ====================

    /**
     * 薪资初步分解，对应 Python 的 cul_sort
     * 输入薪资和当月天数，匹配档位后分解为三个一级账户分配额
     */
    public CalResult calcSalary(String salary, int days) {
        try {
            BigDecimal salaryVal = new BigDecimal(salary);
            BigDecimal daysVal = new BigDecimal(days);

            SalaryRule rule = matchRule(salaryVal);

            BigDecimal reserveRate = new BigDecimal(rule.reserveRate);
            BigDecimal flexAPerDay = new BigDecimal(rule.flexAPerDay);
            BigDecimal flexBPerDay = new BigDecimal(rule.flexBPerDay);

            BigDecimal reserve = salaryVal.multiply(reserveRate)
                    .setScale(0, RoundingMode.HALF_UP);
            BigDecimal flexA = flexAPerDay.multiply(daysVal);
            BigDecimal flexB = flexBPerDay.multiply(daysVal);
            BigDecimal flexTotal = flexA.add(flexB);
            BigDecimal daily = salaryVal.subtract(reserve).subtract(flexTotal);

            return new CalResult(reserve, daily, flexTotal, flexA, flexB,
                    rule.min, rule.reserveRate, rule.flexAPerDay, rule.flexBPerDay);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Reserve细分账户分解，对应 Python 的 Reserve_cal_sort
     * 输入Reserve总额和当前余额，按优先级填满各账户，溢出部分进Overflow
     * 返回：[{"acc": "Emergency", "val": "500"}, ...]
     */
    public List<ReserveChange> calcReserve(String reserveIn, java.util.Map<String, String> currentBalances) {
        List<ReserveChange> changes = new ArrayList<>();
        BigDecimal remaining = new BigDecimal(reserveIn);

        // 从配置取优先级列表
        JsonArray priority = ConfigManager.data
                .getAsJsonObject("allocation")
                .getAsJsonArray("priority");

        // 从配置取限额
        JsonObject limits = ConfigManager.data.getAsJsonObject("limits");

        for (JsonElement el : priority) {
            if (remaining.compareTo(BigDecimal.ZERO) <= 0) break;

            String accName = el.getAsString();
            BigDecimal maxVal = new BigDecimal(limits.get(accName).getAsString());
            BigDecimal nowVal = new BigDecimal(currentBalances.getOrDefault(accName, "0"));
            BigDecimal canFill = maxVal.subtract(nowVal);

            if (canFill.compareTo(BigDecimal.ZERO) > 0) {
                BigDecimal fill = remaining.min(canFill);
                changes.add(new ReserveChange(accName, fill.toString()));
                remaining = remaining.subtract(fill);
            }
        }

        // 剩余进 Overflow
        if (remaining.compareTo(BigDecimal.ZERO) > 0) {
            changes.add(new ReserveChange("Overflow", remaining.toString()));
        }

        return changes;
    }

    // ==================== 私有方法 ====================

    // 匹配薪资档位，从配置文件读取
    private SalaryRule matchRule(BigDecimal salary) {
        JsonArray levels = ConfigManager.data.getAsJsonArray("salary_levels");

        // 找到第一个 salary >= min 的档位（配置里已按从高到低排列）
        SalaryRule fallback = null;
        for (JsonElement el : levels) {
            JsonObject level = el.getAsJsonObject();
            BigDecimal min = new BigDecimal(level.get("min").getAsString());

            if (fallback == null) fallback = jsonToRule(level); // 保底用最低档

            if (salary.compareTo(min) >= 0) {
                return jsonToRule(level);
            }
        }
        return fallback;
    }

    private SalaryRule jsonToRule(JsonObject level) {
        return new SalaryRule(
                level.get("min").getAsString(),
                level.get("Reserve_rate").getAsString(),
                level.get("Flex_A_perday").getAsString(),
                level.get("Flex_B_perday").getAsString()
        );
    }

    // ==================== 数据结构 ====================

    // 档位规则
    private static class SalaryRule {
        String min;
        String reserveRate;
        String flexAPerDay;
        String flexBPerDay;

        SalaryRule(String min, String reserveRate, String flexAPerDay, String flexBPerDay) {
            this.min = min;
            this.reserveRate = reserveRate;
            this.flexAPerDay = flexAPerDay;
            this.flexBPerDay = flexBPerDay;
        }
    }

    // 计算结果
    public static class CalResult {
        public BigDecimal reserve;
        public BigDecimal daily;
        public BigDecimal flexTotal;
        public BigDecimal flexA;
        public BigDecimal flexB;
        public String salaryLevel;
        public String reserveRate;
        public String flexAPerday;
        public String flexBPerday;

        CalResult(BigDecimal reserve, BigDecimal daily, BigDecimal flexTotal,
                  BigDecimal flexA, BigDecimal flexB, String salaryLevel,
                  String reserveRate, String flexAPerday, String flexBPerday) {
            this.reserve = reserve;
            this.daily = daily;
            this.flexTotal = flexTotal;
            this.flexA = flexA;
            this.flexB = flexB;
            this.salaryLevel = salaryLevel;
            this.reserveRate = reserveRate;
            this.flexAPerday = flexAPerday;
            this.flexBPerday = flexBPerday;
        }
    }

    // Reserve 分配结果
    public static class ReserveChange {
        public String acc;
        public String val;

        ReserveChange(String acc, String val) {
            this.acc = acc;
            this.val = val;
        }
    }
}
package com.example.financemanagertool;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import android.content.Context;

public class ConfigManager {

    public static JsonObject data = null;

    public static void init(Context context) {
        File file = new File(context.getFilesDir(), "config.json");

        if (!file.exists()) {
            // 文件不存在，创建默认配置
            data = buildDefault();
            writeToFile(file, data);
        } else {
            // 文件存在，直接读取
            data = readFromFile(file);
        }
    }

    private static JsonObject buildDefault() {
        // 直接用字符串写好 JSON，然后解析成 JsonObject
        String defaultJson = "{"
                + "\"db_path\": \"finance.db\","
                + "\"salary_levels\": ["
                + "  {\"min\": \"5000\", \"Reserve_rate\": \"0.40\", \"Flex_A_perday\": \"20\", \"Flex_B_perday\": \"10\"},"
                + "  {\"min\": \"3000\", \"Reserve_rate\": \"0.30\", \"Flex_A_perday\": \"15\", \"Flex_B_perday\": \"5\"},"
                + "  {\"min\": \"0\",    \"Reserve_rate\": \"0.15\", \"Flex_A_perday\": \"7\",  \"Flex_B_perday\": \"0\"}"
                + "],"
                + "\"limits\": {"
                + "  \"Emergency\": \"3000.00\","
                + "  \"Health\": \"2000.00\","
                + "  \"Security\": \"36000.00\""
                + "},"
                + "\"allocation\": {"
                + "  \"priority\": [\"Emergency\", \"Health\", \"Security\"]"
                + "},"
                + "\"meta\":{"
                + "  \"version\": \"1.0\","
                + "  \"update_date\": \"2026-04-29\","
                + "  \"author\": \"NebulaFluff\""
                + "}"
                + "}";
        return JsonParser.parseString(defaultJson).getAsJsonObject();
    }

    private static JsonObject readFromFile(File file) {
        try {
            FileReader reader = new FileReader(file);
            JsonObject obj = JsonParser.parseReader(reader).getAsJsonObject();
            reader.close();
            return obj;
        } catch (Exception e) {
            // 读取失败（比如文件损坏），返回默认配置
            return buildDefault();
        }
    }

    private static void writeToFile(File file, JsonObject obj) {
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            FileWriter writer = new FileWriter(file);
            gson.toJson(obj, writer);
            writer.close();
        } catch (Exception e) {
            // 写入失败一般不处理，下次启动会重新创建
        }
    }
}

package com.example.financemanagertool;

import android.os.Bundle;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.OpenableColumns;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class DataIOFuncActivity extends AppCompatActivity {

    private static final int PICK_DB_FILE = 1;
    private static final int PICK_JSON_FILE = 2;

    Button btnImportJson;
    Button btnImportDB;
    Button btnExportJson;
    Button btnExportDB;
    Button btnReloadConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_io_func);

        btnImportJson = findViewById(R.id.btn_import_json);
        btnImportDB = findViewById(R.id.btn_import_db);
        btnExportJson = findViewById(R.id.btn_export_json);
        btnExportDB = findViewById(R.id.btn_export_db);
        btnReloadConfig = findViewById(R.id.btn_reload_config);

        btnImportJson.setOnClickListener(v -> openFilePicker(PICK_JSON_FILE));
        btnImportDB.setOnClickListener(v -> openFilePicker(PICK_DB_FILE));
        btnExportJson.setOnClickListener(v -> exportFile("config.json"));
        btnExportDB.setOnClickListener(v -> exportFile("finance.db"));
        btnReloadConfig.setOnClickListener(v -> {
            ConfigManager.init(this);
            Toast.makeText(this, "配置已重新读取", Toast.LENGTH_SHORT).show();
        });
    }

    private void openFilePicker(int requestCode) {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");
        startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK || data == null) return;

        Uri uri = data.getData();
        String fileName = getFileName(uri);

        if (requestCode == PICK_DB_FILE) {
            if (fileName == null || !fileName.endsWith(".db")) {
                Toast.makeText(this, "请选择 .db 文件", Toast.LENGTH_SHORT).show();
                return;
            }
            importFile(uri, "finance.db");
        } else if (requestCode == PICK_JSON_FILE) {
            if (fileName == null || !fileName.endsWith(".json")) {
                Toast.makeText(this, "请选择 .json 文件", Toast.LENGTH_SHORT).show();
                return;
            }
            importFile(uri, "config.json");
        }
    }

    private String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            try (Cursor cursor = getContentResolver().query(uri, null, null, null, null)) {
                if (cursor != null && cursor.moveToFirst()) {
                    int idx = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                    if (idx >= 0) result = cursor.getString(idx);
                }
            }
        }
        if (result == null) result = uri.getLastPathSegment();
        return result;
    }

    private void importFile(Uri uri, String targetFileName) {
        try {
            InputStream in = getContentResolver().openInputStream(uri);
            File dst;
            if (targetFileName.endsWith(".db")) {
                dst = getDatabasePath(targetFileName);
            } else {
                dst = new File(getFilesDir(), targetFileName);
            }
            dst.getParentFile().mkdirs();
            OutputStream out = new FileOutputStream(dst);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = in.read(buffer)) > 0) {
                out.write(buffer, 0, length);
            }
            in.close();
            out.close();
            Toast.makeText(this, targetFileName + " 导入成功，请重启App", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(this, "导入失败：" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void exportFile(String sourceFileName) {
        try {
            File src;
            if (sourceFileName.endsWith(".db")) {
                src = getDatabasePath(sourceFileName);
            } else {
                src = new File(getFilesDir(), sourceFileName);
            }
            if (!src.exists()) {
                Toast.makeText(this, "文件不存在：" + sourceFileName, Toast.LENGTH_SHORT).show();
                return;
            }
            File dst = new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOWNLOADS), sourceFileName);
            InputStream in = new FileInputStream(src);
            OutputStream out = new FileOutputStream(dst);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = in.read(buffer)) > 0) {
                out.write(buffer, 0, length);
            }
            in.close();
            out.close();
            Toast.makeText(this, "已导出到下载文件夹：" + sourceFileName, Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(this, "导出失败：" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
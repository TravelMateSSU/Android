package com.example.tourmate.managers;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IronFactory on 2016. 10. 6..
 * Manifest에 <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" /> 반드시 추가
 */
public class DBManager extends SQLiteOpenHelper {

    public static final String PACKAGE_NAME = "com.ironfactory.sampletravelmate";   //YourPackageName
    public static final String TAG = "DBManager";

    public static final String DIR_PATH = "/data/data/" + PACKAGE_NAME + "/databases";
    public static final String FILE_NAME = "travelmate.db";

    public DBManager(Context context) {
        super(context, FILE_NAME, null, 1);
    }

    public DBManager(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DBManager(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    /**
    * @return 카테고리 코드(KEY)와 이름(VALUE)를 가진 Map
    * */
    public Map<String, String> getCategories() {
        Map<String, String> categories = new HashMap<>();

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM categories", null);
        while (cursor.moveToNext()) {
            String code = cursor.getString(0);
            String name = cursor.getString(1);
            categories.put(code, name);
        }
        return categories;
    }


    /**
    * @params categoryCode = 카테고리 코드
    * @return 카테고리 코드에 맞는 이름을 리턴
    * */
    public String getCategoryName(String categoryCode) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM categories WHERE CODE = '" + categoryCode + "'", null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0)
            return cursor.getString(1);
        return "기타";
    }

    /**
    * 카테고리 db가 없다면 복사
    * */
    public void copyCategoriesDB(Context context) {
        AssetManager assetManager = context.getAssets();
        File file = new File(DIR_PATH + "/" + FILE_NAME);

        FileOutputStream fos = null;
        BufferedOutputStream bos = null;

        try {
            InputStream is = assetManager.open(FILE_NAME);
            BufferedInputStream bis = new BufferedInputStream(is);

            if (file.exists()) {
                Log.d(TAG, "exists");
                is.close();
                bis.close();
                return;
            }

            File dir = new File(DIR_PATH);
            if (!dir.exists())
                dir.mkdirs();

            if (!file.exists()){
                file.delete();
                file.createNewFile();
            }

            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);

            int read = -1;
            byte[] buffer = new byte[1024];
            while ((read = bis.read(buffer, 0, 1024)) != -1) {
                bos.write(buffer, 0, read);
            }

            bos.flush();
            fos.close();
            bos.close();
            is.close();
            bis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

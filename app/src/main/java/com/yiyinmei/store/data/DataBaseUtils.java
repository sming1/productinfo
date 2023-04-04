package com.yiyinmei.store.data;

import android.content.ContentValues;

public class DataBaseUtils {
    public static void update(MySqliteDatabase database,String table,ContentValues values,String where,String[] params) {
        database.getReadableDatabase().update(table,values,where,params);
    }
    public static void insert(MySqliteDatabase database,String table,ContentValues values) {
        database.getReadableDatabase().insert(table,null,values);
    }

    public static void delete(MySqliteDatabase database,String table,String where,String[] params) {
        database.getReadableDatabase().delete(table,where,params);
    }
}

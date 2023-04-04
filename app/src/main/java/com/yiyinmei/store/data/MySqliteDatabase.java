package com.yiyinmei.store.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MySqliteDatabase  extends SQLiteOpenHelper {
   private static final String mDataBase = "yiyinmei";
   private static final String mProductInfo = "product";
   private static final String mMaterial = "material";
   private static final String mSales = "sales";


   public MySqliteDatabase(@Nullable Context context) {
      super(context, mDataBase, null, 1);
   }

   @Override
   public void onCreate(SQLiteDatabase sqLiteDatabase) {
      createTables(sqLiteDatabase);
   }

   @Override
   public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

   }



   private void createTables (SQLiteDatabase sqLiteDatabase) {
      String productSql= "CREATE TABLE IF NOT EXISTS "+mProductInfo+"("+
              "_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"+
              "name VARCHAR NOT NULL,"+
              "store INTEGAER NOT NULL,"+
              "event VARCHAR)";
      sqLiteDatabase.execSQL(productSql);

   }
}

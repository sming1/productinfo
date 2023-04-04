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
   private static final String mSalary = "salary ";


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

      String material = "CREATE TABLE IF NOT EXISTS "+mMaterial+"("+
              "_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"+
              "name VARCHAR NOT NULL,"+
              "store INTEGAER NOT NULL,"+
              "event VARCHAR)";

      String salary = "CREATE TABLE IF NOT EXISTS "+mSalary+"("+
              "_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"+
              "name VARCHAR NOT NULL,"+
              "number VARCHAR NOT NULL,"+
              "salary VARCHAR NOT NULL,"+
              "event VARCHAR)";

      sqLiteDatabase.execSQL(productSql);
      sqLiteDatabase.execSQL(material);
      sqLiteDatabase.execSQL(salary);

   }
}

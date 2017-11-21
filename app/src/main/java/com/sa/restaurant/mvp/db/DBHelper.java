package com.sa.restaurant.mvp.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


class DBHelper  extends SQLiteOpenHelper
{

   private static  String DATABASE_NAME="restaurant.db";
    private static  int VERSION=1;
    private SQLiteDatabase  db;



   DBHelper(Context context)
   {
       super(context,DATABASE_NAME,null,VERSION);
       db=getWritableDatabase();

   }
    @Override
    public void onCreate(SQLiteDatabase db)
    {

        db.execSQL(DBUser.CREATE_TABLE_USER);
        db.execSQL(DBUser.CREATE_TABLE_RESTAURANT);
    }
    public synchronized void close() {
        if (db != null) {
            db.close();
        }
        SQLiteDatabase.releaseMemory();
        super.close();
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1)
    {
        db.execSQL("drop table if exists "+DBUser.TABLE_USER);
        db.execSQL("drop table if exists "+DBUser.TABLE_FAV_RESTAURANT);
        onCreate(db);

    }
     SQLiteDatabase getDb()
    {
     return db;
    }

}

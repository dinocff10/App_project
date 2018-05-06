package com.example.dino.fourlayout;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;




public class MyDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "RECVDATA";
    private static final int DATABASE_VERSION =1;
    public MyDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE titles (_id " +
                "integer primary key autoincrement, " +
                "data1 text ,"+
                "data2 text ,"+
                "data3 text ,"+
                "data4 text,"+
                "data5 text,"+
                "data6 text,"+
                "data7 text,"+
                "data8 text,"+
                "data9 text,"+
                "data10 text,"+
                "data11 text  )");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion) {
            db.beginTransaction();//建立交易

            boolean success = false;//判斷參數

            //由之前不用的版本，可做不同的動作
            switch (oldVersion) {
                case 1:
                    db.execSQL("ALTER TABLE titles ADD COLUMN data6 text");
                    db.execSQL("ALTER TABLE titles ADD COLUMN data7 text");
                    db.execSQL("ALTER TABLE titles ADD COLUMN data8 text");
                    db.execSQL("ALTER TABLE titles ADD COLUMN data9 text");
                    db.execSQL("ALTER TABLE titles ADD COLUMN data10 text");
                    db.execSQL("ALTER TABLE titles ADD COLUMN data11 text");

                    oldVersion++;

                    success = true;
                    break;
            }

            if (success) {
                db.setTransactionSuccessful();//正確交易才成功
            }
            db.endTransaction();
        }
        else {
            onCreate(db);
        }
    }


}
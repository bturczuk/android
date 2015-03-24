package com.example.beataturczuk.parser.Helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.example.beataturczuk.parser.Helpers.CommandData;



import java.util.ArrayList;

/**
 * Created by beataturczuk on 20.03.15.
 */
public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context, CommandData.DB_NAME, null, CommandData.DATABASE_VERSION);
    }


    private static final String DATABASE_CREATE = "create table " +
       CommandData.TABLE_NAME + "(" + CommandData.PRODUCT_COLUMN_ID
            + " integer primary key autoincrement, "
            + CommandData.PRODUCT_MESSAGE + " text not null, "
            + CommandData.PRODUCT_AUTHOR + " text not null); ";


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(DBHelper.class.getName(),
                "Upgrading database from version " + oldVersion +
                " to " + newVersion + ", which will destroy all old data"
        );
        db.execSQL("DROP TABLE IF EXIST " + CommandData.TABLE_NAME);
        onCreate(db);
    }



    public void insertData(String body, String author) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CommandData.PRODUCT_MESSAGE, body);
        values.put(CommandData.PRODUCT_AUTHOR, author);
        sqLiteDatabase.insert("products", null, values);
    }


    public ArrayList takeData() {
        ArrayList<String>stringArrayList = new ArrayList<String>();
        String takedata = "select * from products";
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(takedata, null);
        if (cursor.moveToFirst()) {
            do {
                stringArrayList.add(cursor.getString(0));
                stringArrayList.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }
        return stringArrayList;
    }

}

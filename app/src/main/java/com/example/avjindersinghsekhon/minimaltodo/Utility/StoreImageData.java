package com.example.avjindersinghsekhon.minimaltodo.Utility;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class StoreImageData extends SQLiteOpenHelper {
    Context context;
    public StoreImageData(Context context) {
        super(context, "Database", null, 1);
        this.context = context;

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "Create Table ImageData ( Title Text , Image Blob ) ;";
        sqLiteDatabase.execSQL(sql);
    }

    public long AddData(String Title , byte[] Image){
        ContentValues contentValues = new ContentValues();
        contentValues.put("Title",Title);
        contentValues.put("Image",Image);
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        long res = sqLiteDatabase.insert("ImageData",null , contentValues);
        return res;
    }

    public void DeleteData(String Title){
        String sql = "Delete From ImageData where Title = '"+Title+"';";
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.execSQL(sql);
    }

    public Cursor getCursor(){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String sql = "Select Title , Image from ImageData ; " ;
        Cursor cursor = sqLiteDatabase.rawQuery(sql , null);
        return cursor;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}

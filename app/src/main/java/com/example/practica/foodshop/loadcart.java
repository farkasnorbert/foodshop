package com.example.practica.foodshop;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;


public class loadcart extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "cart.db";
    public loadcart(Context context) {
        super(context, DATABASE_NAME , null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS items(id INT,name VARCHAR,price DOUBLE);");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS items");
        onCreate(db);
    }
    public void load(Item item) {
        SQLiteDatabase cart = this.getWritableDatabase();
        int id = item.getId();
        Cursor res = cart.rawQuery("select count(*) from items where id = "+Integer.toString(id)+";",null);
        res.moveToFirst();
        int ok = res.getInt(0);
        if(ok==0) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("id", Integer.toString(id));
            contentValues.put("name", item.getName());
            contentValues.put("price", Double.toString(item.getPrice()));
            cart.insert("items", null, contentValues);
        }
        res.close();
    }
    public ArrayList<Bundle> get(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from items", null );
        res.moveToFirst();
        ArrayList<Bundle> x = new ArrayList<Bundle>();
        while(res.isAfterLast() == false) {
            Bundle b = new Bundle();
            b.putInt("id",Integer.parseInt(res.getString(res.getColumnIndex("id"))));
            b.putString("name",res.getString(res.getColumnIndex("name")));
            b.putDouble("price",Double.parseDouble(res.getString(res.getColumnIndex("price"))));
            x.add(b);
            res.moveToNext();
        }
        res.close();
        return x;
    }
    public void delete(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("items","id="+Integer.toString(id),null);
    }
    public void drop(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("items","",null);
    }
}

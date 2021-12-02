package com.yajith.mybankingapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class MyDatabase extends SQLiteOpenHelper {
    public MyDatabase(Context context)
    {
        super(context,"MyBank.db",null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE Customer(ID integer not null unique primary key AUTOINCREMENT,Name varchar(50),CurrentBal float(10));";
        db.execSQL(CREATE_TABLE);
        initializedb(db);
    }
    public void initializedb(SQLiteDatabase db)
    {
        String sql_query="INSERT INTO Customer(Name,CurrentBal) VALUES " +
                "('Yajith',1000)," +
                "('Vishwa',2000)," +
                "('Tommy',5000)," +
                "('Sweety',10000)," +
                "('Cutie',40000)," +
                "('Simba',35000);";
        db.execSQL(sql_query);
    }
    public ArrayList<MyDataModel> getAllCustomer()
    {
        ArrayList<MyDataModel> arrayList=new ArrayList<>();
        String sql_query="SELECT * FROM Customer;";
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        Cursor cursor=sqLiteDatabase.rawQuery(sql_query,null);
        if (cursor.moveToFirst()) {
            do {
               MyDataModel myDataModel=new MyDataModel();
               myDataModel.id=Integer.parseInt(cursor.getString(0));
               myDataModel.name=cursor.getString(1);
               myDataModel.CurrentBal= Float.parseFloat(cursor.getString(2));
               arrayList.add(myDataModel);
            } while (cursor.moveToNext());
        }
        return arrayList;
    }
    public int tranfer(String dest_name,String from_name,float amount)
    {
        String sql_query="SELECT CurrentBal FROM Customer WHERE Name='"+from_name+"';";
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        Cursor from_cursor=sqLiteDatabase.rawQuery(sql_query,null);
        float from_CurrentBal=0;
        if(from_cursor.moveToFirst())
        {
            do {
                from_CurrentBal=Float.parseFloat(from_cursor.getString(0));
            }while (from_cursor.moveToNext());
        }
        from_CurrentBal-=amount;
        ContentValues from_values = new ContentValues();
        from_values.put("CurrentBal", from_CurrentBal);

        String dest_sql_query="SELECT CurrentBal FROM Customer WHERE Name='"+dest_name+"';";
        Cursor dest_cursor=sqLiteDatabase.rawQuery(dest_sql_query,null);
        float dest_CurrentBal=amount;
        if(dest_cursor.moveToFirst())
        {
            do {
                dest_CurrentBal+=Float.parseFloat(dest_cursor.getString(0));
            }while (dest_cursor.moveToNext());
        }
        ContentValues dest_value = new ContentValues();
        dest_value.put("CurrentBal", dest_CurrentBal);

        return sqLiteDatabase.update("Customer", dest_value, "Name" + " = ?",
                new String[] { dest_name })+sqLiteDatabase.update("Customer", from_values, "Name" + " = ?",
                new String[] { from_name });
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Customer");
        onCreate(db);
    }
}

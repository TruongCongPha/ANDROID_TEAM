package com.a12t4group.soccernetwork.DB;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.a12t4group.soccernetwork.BEAN.DISTRICTS;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * Created by User on 24/04/2016.
 */
public class Access_District_DB extends SQLiteOpenHelper {

    private static final String TAG = "SQLite";
    private static String DB_PATH = "/data/data/com.a12t4group.soccernetwork/databases/";
    private static final String DATABASE_NAME = "soccer_network.sqlite";
    private static final int DATABASE_VERSION =1;
    private SQLiteDatabase mDatabase;
    Context context;




    public Access_District_DB(Context context){
        super(context,DATABASE_NAME,null, DATABASE_VERSION );
        this.context = context;
        createDatabase();
    }
    @Override
    public  void onCreate(SQLiteDatabase db){
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void createDatabase(){
        Log.e("DATABASE-ABC","On Create");
        if (checkDatabase()) {
            openDatabase();
        } else {
            copyDatabase();
        }
    }
    public boolean checkDatabase() {
        File f = new File(DB_PATH + DATABASE_NAME);
        return f.exists();
    }

    public void copyDatabase() {
        try {
            InputStream is = context.getAssets().open(DATABASE_NAME);
            Log.e("DATABASE-ABC",is.toString());
            String outName = DB_PATH + DATABASE_NAME;
            Log.e("Path","Path "+DB_PATH + DATABASE_NAME);
            OutputStream os = new FileOutputStream(outName);

            Log.e("DATABASE-ABC",os.toString());
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
            os.flush();
            os.close();
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void openDatabase() {

        Log.e("DATABASE-ABC","Open database");
        mDatabase = SQLiteDatabase.openDatabase(DB_PATH + DATABASE_NAME, null, SQLiteDatabase.OPEN_READWRITE);

        Log.e("DATABASE-ABC","TAO DUOC DB " + mDatabase.toString());
    }

    public DISTRICTS getDistrict(int district_id){
        Log.i(TAG, "MyDatabaseHelper.getDistrict ... " + district_id);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from DISTRICTS where district_id="+district_id, null);
            if(cursor!=null)    cursor.moveToFirst();
        DISTRICTS district = new DISTRICTS(cursor.getInt(0), cursor.getString(1), cursor.getInt(2));
        return district;
    }

    public DISTRICTS getDistrict(String district_name){
        Log.i(TAG, "MyDatabaseHelper.getDistrict ... " + district_name);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from DISTRICTS where district_name='"+district_name+"'", null);
        if(cursor!=null)    cursor.moveToFirst();
        DISTRICTS district = new DISTRICTS(cursor.getInt(0), cursor.getString(1), cursor.getInt(2));
        return district;
    }

    public Cursor getListDistricts(){
        Log.i(TAG, "MyDatabaseHelper.getListDistrict ... " );
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from DISTRICTS ", null);
        if(cursor!=null)    cursor.moveToFirst();
        ArrayList<DISTRICTS> listDistricts = new ArrayList<>();

       /* if(cursor.moveToFirst()){
            do{
                DISTRICTS district = new DISTRICTS(cursor.getInt(0), cursor.getString(1), cursor.getInt(2));
                listDistricts.add(district);
            }while(cursor.moveToNext());
        }


        return listDistricts;*/
        if(cursor!=null) cursor.moveToFirst();
        return  cursor;
    }



}

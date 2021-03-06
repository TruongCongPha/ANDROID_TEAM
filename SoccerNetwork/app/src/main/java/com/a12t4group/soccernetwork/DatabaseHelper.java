package com.a12t4group.soccernetwork;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by User on 08/04/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    private static String DB_PATH ="data/data/com.12t4group.soccernetwork/databases/";
    private static String DB_NAME = "cndd.sqlite";
    private SQLiteDatabase mDatabase;
    private Context mContext;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, 1);
        mContext = context;
        createDatabase();
    }

    public void createDatabase() {
        if (checkDatabase()) {
            openDatabase();
        } else {
            copyDatabase();
        }
    }

    public boolean checkDatabase() {
        File f = new File(DB_PATH + DB_NAME);
        return f.exists();
    }

    public void copyDatabase() {
        try {
            InputStream is = mContext.getAssets().open(DB_NAME);
            String outName = DB_PATH + DB_NAME;
            OutputStream os = new FileOutputStream(outName);
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
        mDatabase = SQLiteDatabase.openDatabase(DB_PATH + DB_NAME, null, SQLiteDatabase.OPEN_READWRITE);
    }

    public void closeDatabase() {
        if (mDatabase != null) {
            mDatabase.close();
        }
    }

    public boolean checkAccount(String user, String pass) {
        openDatabase();
        Cursor cursor = mDatabase.rawQuery("SELECT * FROM user_profiles WHERE username = '" + user + "' AND password = '" + pass + "'", null);
        boolean check = cursor.moveToFirst();
        cursor.close();
        closeDatabase();
        return check;
    }

    public void addAccount(int id, String user, String pass){
        openDatabase();
        ContentValues cv = new ContentValues();
        cv.put("user_id", id);
        cv.put("username", user);
        cv.put("password", pass);
        mDatabase.insert("user_profiles", null, cv);
        closeDatabase();
    }

    @Override
    public  void onCreate(SQLiteDatabase sqLiteDatabase){

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }




}

package com.a12t4group.soccernetwork.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.a12t4group.soccernetwork.BEAN.MATCHES;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by User on 27/04/2016.
 */
public class Access_Matches_DB extends SQLiteOpenHelper {
    private static final String TAG = "SQLite";
    private static String DB_PATH = "/data/data/com.a12t4group.soccernetwork/databases/";
    private static final String DATABASE_NAME = "soccer_network.sqlite";
    private static final int DATABASE_VERSION =1;
    private SQLiteDatabase mDatabase;
    Context context;

    // Tên bảng: MATCHES.
    private static final String TABLE_MATCH= "MATCHES";
    private static final String COLUMN_MATCH_MATCH_ID="match_id";
    private static final String COLUMN_MATCH_ID_FG="id_fg";
    private static final String COLUMN_MATCH_USER_ID="user_id";
    private static final String COLUMN_MATCH_STATUS="status";
    private static final String COLUMN_MATCH_START_TIME   ="start_time";
    private static final String COLUMN_MATCH_END_TIME="end_time";
    private static final String COLUMN_MATCH_PRICE="price";
    private static final String COLUMN_MATCH_IS_VERIFIED="is_verified";
    private static final String COLUMN_MATCH_CREATED="created";
    private static final String COLUMN_MATCH_UPDATED="updated";
    private static final String COLUMN_MATCH_DELETED="deleted";

    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    public Access_Matches_DB(Context context){
        super(context,DATABASE_NAME,null, DATABASE_VERSION );
        this.context = context;
        createDatabase();
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


    public MATCHES getMatch(int match_id){
        Log.i(TAG, "MyDatabaseHelper.getMatch ... " + match_id);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from MATCHES where match_id="+match_id, null);
        if(cursor!=null)    cursor.moveToFirst();
        MATCHES matches = new MATCHES();
        try {
            matches.setMatch_id(cursor.getInt(0));
            Log.i("LOI", "LOI 1");
            matches.setId_fg(cursor.getInt(1));
            Log.i("LOI", "LOI 1");
            matches.setUser_id(cursor.getInt(2));
            Log.i("LOI", "LOI 1");
            matches.setStatus(Boolean.getBoolean(cursor.getString(3)));
            Log.i("LOI", "LOI 1");
            matches.setStart_time(format.parse(cursor.getString(4)));
            Log.i("LOI", "LOI 1");
            matches.setEnd_time(format.parse(cursor.getString(5)));
            Log.i("LOI", "LOI 1");
            matches.setPrice(cursor.getInt(6));
            Log.i("LOI", "LOI 1");
            matches.setIs_verified(Boolean.getBoolean(cursor.getString(7)));
            Log.i("LOI", "LOI 1");
            matches.setCreated(format.parse(cursor.getString(8)));
            Log.i("LOI", "LOI 1");
            matches.setUpdated(format.parse(cursor.getString(9)));
            Log.i("LOI", "LOI 1");
            matches.setDeleted(format.parse(cursor.getString(10)));
            Log.i("LOI", "LOI 1");
            /*matches = new MATCHES(cursor.getInt(0), cursor.getInt(1),
                    cursor.getInt(2), Boolean.getBoolean(cursor.getString(3)), format.parse(cursor.getString(4)),
                    format.parse(cursor.getString(5)), cursor.getInt(6), Boolean.getBoolean(cursor.getString(7)), format.parse(cursor.getString(8)),
                    format.parse(cursor.getString(9)), format.parse(cursor.getString(10)));*/
        } catch (Exception e){}
        return matches;
    }

    public Cursor getListMatches(){
        Log.i(TAG, "MyDatabaseHelper.getListMatches ... " );
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from MATCHES ", null);
        if(cursor!=null) cursor.moveToFirst();
        return  cursor;
    }

    public Cursor getListMatchesCorrespondWithUser(int user_id) {
        Log.i(TAG, "MyDatabaseHelper.getListMatches ... ");
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from MATCHES where user_id="+user_id, null);
        if (cursor != null) cursor.moveToFirst();
        return cursor;
    }

    public Cursor getListMatchesCorrespondMatchDay( String date) {
        //date đang ở định dạng yyyy-MM-dd
        Log.i(TAG, "MyDatabaseHelper.getListMatches with Day ... ");
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select * from MATCHES where date(start_time)='"+date+"'";
        Log.i("TRUY VAN", query);
        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null) cursor.moveToFirst();
        return cursor;
    }

    public long addMatch(MATCHES match){
        Log.i(TAG, "Add Match ... ");
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_MATCH_ID_FG, match.getId_fg());
        values.put(COLUMN_MATCH_USER_ID, match.getUser_id());
        values.put(COLUMN_MATCH_STATUS, "true");
            String start_time = format.format(match.getStart_time());
        values.put(COLUMN_MATCH_START_TIME, start_time);
            String end_time = format.format(match.getEnd_time());
        values.put(COLUMN_MATCH_END_TIME, end_time);
        values.put(COLUMN_MATCH_PRICE, match.getPrice());

        values.put(COLUMN_MATCH_IS_VERIFIED, "");
            String created = format.format(new Date());
        values.put(COLUMN_MATCH_CREATED, created);
        values.put(COLUMN_MATCH_UPDATED, "");
        values.put(COLUMN_MATCH_DELETED, "");
        //chèn 1 dòng dl vào bảng
        long kq= db.insert(TABLE_MATCH,null,values);
        db.close();
        return  kq;
    }


    //Update MATCHES
    public int updateMatch(MATCHES match){
        Log.i(TAG, "MyDatabaseHelper updatMatch.....");
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_MATCH_MATCH_ID, match.getMatch_id());
        values.put(COLUMN_MATCH_ID_FG, match.getId_fg());
        values.put(COLUMN_MATCH_USER_ID, match.getUser_id());
        values.put(COLUMN_MATCH_STATUS, match.isStatus());

            String start_time = format.format(match.getStart_time());
        values.put(COLUMN_MATCH_START_TIME, start_time);
            String end_time = format.format(match.getEnd_time());
        values.put(COLUMN_MATCH_END_TIME, end_time);

        values.put(COLUMN_MATCH_IS_VERIFIED, "true");

            String created = format.format(match.getCreated());
        values.put(COLUMN_MATCH_CREATED,created );
        values.put(COLUMN_MATCH_UPDATED, format.format(new Date()));
        values.put(COLUMN_MATCH_DELETED, "");
        return  db.update(TABLE_MATCH,values,COLUMN_MATCH_MATCH_ID + "=?", new String[] {String.valueOf(match.getMatch_id())});
    }

    //delete MATCHES
    public int deleteMatch(MATCHES match){
        Log.i(TAG, "MyDatabaseHelper  deleteUser.... "+match.getMatch_id());
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_MATCH ,COLUMN_MATCH_MATCH_ID +"=?", new  String[]{String.valueOf(match.getMatch_id())});
        db.close();
        return result;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

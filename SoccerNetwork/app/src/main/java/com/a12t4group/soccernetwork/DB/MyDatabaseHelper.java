package com.a12t4group.soccernetwork.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.a12t4group.soccernetwork.BEAN.USER;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by User on 11/04/2016.
 */
public class MyDatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "SQLite";
    private static String DB_PATH = "/data/data/com.a12t4group.soccernetwork/databases/";
    private static final String DATABASE_NAME = "soccer_network.sqlite";
    private static final int DATABASE_VERSION =1;
    private SQLiteDatabase mDatabase;
    Context context;

    // Tên bảng: USER.
    private static final String TABLE_USER = "USER";
    private static final String COLUMN_USER_USER_ID="user_id";
    private static final String COLUMN_USER_USERNAME="username";
    private static final String COLUMN_USER_PASSWORD="password";
    private static final String COLUMN_USER_EMAIL="email";
    private static final String COLUMN_USER_PHONE_NUMBER   ="phone_number";
    private static final String COLUMN_USER_ROLE="role";
    private static final String COLUMN_USER_DISTRICT_ID="district_id";
    public MyDatabaseHelper(Context context){
        super(context,DATABASE_NAME,null, DATABASE_VERSION );
        this.context = context;
        createDatabase();
    }

    public void createDatabase(){
        Log.e("DATABASE-ABC","On Create");
        if (checkDatabase()) {
            openDatabase();
        } else {
            this.getWritableDatabase();
            copyDatabase();
        }
    }
    @Override
    public  void onCreate(SQLiteDatabase db){
        //Script tạo bảng
        /*Log.i(TAG, "MyDatabaseHelper.onCreate ... ");
        String script = "CREATE TABLE "+TABLE_USER+" ("+COLUMN_USER_USER_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"
                                +COLUMN_USER_USERNAME+" TEXT , "+COLUMN_USER_PASSWORD+" TEXT )" ;
        Log.i(TAG, "++++++++ MyDatabaseHelper.onCreate SQL ...+++++ "+script);
        db.execSQL(script);*/
        /*Log.e("DATABASE-ABC","On Create");
        if (checkDatabase()) {
            openDatabase();
        } else {
            copyDatabase();
        }*/
    }

    public boolean checkDatabase() {
        File f = new File(DB_PATH + DATABASE_NAME);
        return f.exists();
    }

    public void copyDatabase() {
        try {
            InputStream is = context.getAssets().open(DATABASE_NAME);
            Log.e("DATABASE-ABC","InputStream: "+is.toString());
            String outName = DB_PATH + DATABASE_NAME;
            Log.e("Path","Path "+DB_PATH + DATABASE_NAME);
            OutputStream os = new FileOutputStream(outName);

            Log.e("DATABASE-ABC","OutputStream: "+os.toString());
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

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

    }

    public void addUser(USER user){
        Log.i(TAG, "Add new user ... "+user.getUsername() );
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
            values.put(COLUMN_USER_USERNAME, user.getUsername());
            values.put(COLUMN_USER_PASSWORD, user.getPass());
            values.put(COLUMN_USER_EMAIL, user.getEmail());
            values.put(COLUMN_USER_PHONE_NUMBER, user.getPhone_number());
            values.put(COLUMN_USER_ROLE, user.getRole());
            values.put(COLUMN_USER_DISTRICT_ID, user.getDistrict_id());
        //chèn 1 dòng dl vào bảng
        db.insert(TABLE_USER,null,values);
        db.close();
    }

    public USER getUser(String username){
        Log.i(TAG, "MyDatabaseHelper.getUser ... " + username);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from "+TABLE_USER+" where username='"+username+"'",null);
        if (cursor.moveToFirst()) {
            USER user = new USER(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2)
                    , cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getInt(6));
            return user;
        } else return null;

    }

    public boolean isExistUser(USER user){
        Log.i(TAG, "MyDatabaseHelper.isExitsUser ... " + user.getUsername());
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from "+TABLE_USER+" where username='"+user.getUsername()+"' and password='"+user.getPass()+"';",null);
        boolean check = cursor.moveToFirst();
            if(check == true) Log.i(TAG, "MyDatabaseHelper.isExitsUser ... " + user.getUsername()+" ------> Exist");
        cursor.close();
        db.close();
        return check;
    }

    public Cursor getAllUsers(){
        Log.i(TAG, "MyDatabaseHelper.getAllUsers ... ");
        //List<USER> listUsers = new ArrayList<USER>();

        //Select all user
        String query = "Select * from "+TABLE_USER;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query,null);

        /*//duyệt từng bản ghi, thêm vào list
        if(cursor.moveToFirst()){
            do{
                USER user = new USER(cursor.getString(1),cursor.getString(2));
                listUsers.add(user);
            }while(cursor.moveToNext());
        }


        return listUsers;*/
        if(cursor!=null) cursor.moveToFirst();
        return  cursor;
    }


    //Update USER
    public int updateUser(USER user){
        Log.i(TAG, "MyDatabaseHelper updateUser.....");
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
            values.put(COLUMN_USER_USERNAME,user.getUsername());
            values.put(COLUMN_USER_PASSWORD,user.getPass());
            values.put(COLUMN_USER_EMAIL, user.getEmail());
            values.put(COLUMN_USER_PHONE_NUMBER, user.getPhone_number());
            values.put(COLUMN_USER_ROLE, user.getRole());
            values.put(COLUMN_USER_DISTRICT_ID, user.getDistrict_id());
        return  db.update(TABLE_USER,values,COLUMN_USER_USER_ID + "=?", new String[] {String.valueOf(user.getUser_id())});
    }

    //delete User
    public int deleteUser(USER user){
        Log.i(TAG, "MyDatabaseHelper  deleteUser.... "+user.getUser_id());
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_USER,COLUMN_USER_USER_ID +"=?", new  String[]{String.valueOf(user.getUser_id())});
        db.close();
        return result;
    }

    public int getUSERsCount() {
        Log.i(TAG, "MyDatabaseHelper.getNotesCount ... " );

        Log.i(TAG, "++++++++ MYDATABASEHELPER PATH OF DATABASE ... ------->>>> "+context.getDatabasePath(DATABASE_NAME).toString());
        String countQuery = "SELECT  * FROM " + TABLE_USER;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();

        cursor.close();

        // return count
        return count;
    }
    // Nếu trong bảng USER chưa có dữ liệu,
    // Chèn vào mặc định 2 bản ghi.
    public void createDefaultUSERIfNeed()  {
        int count = this.getUSERsCount();
        if(count ==0 ) {
            USER user1 = new USER("truongcongpha",
                    "06082301","truongcongpha@gmail.com","01632939648","admin",3);
            USER user2 = new USER("Truong Cong Pha",
                    "06082301","truongcongpha@gmail.com","01632939648","admin",3);
            this.addUser(user1);
            this.addUser(user2);
        }
    }

    public String getAdress(int district_id){
        Log.i(TAG, "MyDatabaseHelper.getCityName ... ");
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select dt.district_name , ct.city from DISTRICTS dt, CITIES ct where district_id="+district_id+" and dt.city_id = ct.city_id", null);
        if(cursor!=null)    cursor.moveToFirst();
        return cursor.getString(0)+" , "+cursor.getString(1);
    }
}

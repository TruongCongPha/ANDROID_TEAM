package com.a12t4group.soccernetwork;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.a12t4group.soccernetwork.BEAN.USER;
import com.a12t4group.soccernetwork.DB.MyDatabaseHelper;

public class MainActivity extends Activity {
    TextView lbUser, lbPassword, txtAddUsername, txtAddPassword;
    Button btnAddUser;
    MyDatabaseHelper db;
    TableLayout tableLayout ;
    ProgressDialog PD;
    String addUserName,addPass;
    USER userOnclick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new MyDatabaseHelper(this);

            lbUser = (TextView) findViewById(R.id.lbUser);
            lbPassword = (TextView) findViewById(R.id.lbPassword);
            btnAddUser = (Button) findViewById(R.id.btnAddUser);

        txtAddUsername = (TextView) findViewById(R.id.txtAddUsername);
        txtAddPassword = (TextView) findViewById(R.id.txtAddPassword);

        tableLayout = (TableLayout) findViewById(R.id.table);
        Intent intent = getIntent();
            String username = intent.getStringExtra("username");
            String password = intent.getStringExtra("password");
            lbUser.setText(lbUser.getText()+" "+username);
            lbPassword.setText(lbPassword.getText()+" "+password);

        BuildTable();

            btnAddUser.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    addUserName = txtAddUsername.getText().toString();
                    addPass = txtAddPassword.getText().toString();

                    Log.i("LIST USER","   ==================>>> Username: "+addUserName+"        Pass: "+addPass);
                    if(!addUserName.equals("") || !addPass.equals("")) {
                        new MyAsync().execute();
                    }
                }
            });

    }


    public void BuildTable(){
            Cursor cursor = db.getAllUsers();
            int rows = cursor.getCount();
            int col = cursor.getColumnCount();
            cursor.moveToFirst();
            for(int i=0;i<rows; i++){
                if(cursor.getString(1).equals("")){
                    db.deleteUser(new USER(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2)
                            ,cursor.getString(3), cursor.getString(4),cursor.getString(5), cursor.getInt(6)));
                }
                final TableRow row = new TableRow(this);
                row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT));
                row.setClickable(true);
                row.setId(i);
                if(i%2==0) row.setBackgroundColor(Color.LTGRAY);
                else row.setBackgroundColor(Color.WHITE);


               for(int j=0;j<col;j++){
                   TextView txt = new TextView(this);
                   txt.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                           TableRow.LayoutParams.WRAP_CONTENT));
                   txt.setGravity(Gravity.LEFT);
                   txt.setPadding(8,8,8,8);
                   txt.setText(cursor.getString(j));
                   row.addView(txt);
               }
                row.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        userOnclick = new USER(Integer.parseInt(((TextView) row.getChildAt(0)).getText().toString()),
                                ((TextView) row.getChildAt(1)).getText().toString(),
                                ((TextView) row.getChildAt(2)).toString() ,"", "","", 0);
                        /*userOnclick.setUser_id(Integer.parseInt(((TextView) row.getChildAt(1)).getText().toString()));
                        userOnclick.setUsername(((TextView) row.getChildAt(1)).getText().toString());
                        userOnclick.setPass(((TextView) row.getChildAt(2)).toString());*/
                        Log.i("TAG", "Value row of table --------->>>>>>>>>>>>>>>>>> "+userOnclick.getUsername());
                        Toast message = Toast.makeText(getApplicationContext(), "Value row of Table: "+userOnclick.getUsername(), Toast.LENGTH_LONG);
                        message.show();
                    }
                });
                cursor.moveToNext();
                tableLayout.addView(row);
            }
        db.close();
    }


    private class MyAsync extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {

            super.onPreExecute();

            tableLayout.removeAllViews();

            PD = new ProgressDialog(MainActivity.this);
            PD.setTitle("Please Wait..");
            PD.setMessage("Loading...");
            PD.setCancelable(false);
            PD.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            USER user = new USER(addUserName  , addPass,addUserName+"@gmail.com","","user",1);

            // inserting data
            db.addUser(user);
            // BuildTable();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            BuildTable();
            PD.dismiss();
        }
    }

}

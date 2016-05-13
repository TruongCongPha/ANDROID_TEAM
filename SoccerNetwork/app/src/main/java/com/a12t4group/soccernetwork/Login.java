package com.a12t4group.soccernetwork;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.a12t4group.soccernetwork.BEAN.USER;
import com.a12t4group.soccernetwork.DB.MyDatabaseHelper;
import com.a12t4group.soccernetwork.Sliding.Sliding;

/**
 * Created by User on 11/04/2016.
 */
public class Login extends Activity {
    private TextView txtUsername, txtPass, txtError, createAcount;
    private Button btnLogin;
    public MyDatabaseHelper db;
    Intent intent;
    @Override
    public void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.login);
        db = new MyDatabaseHelper(this);
//            db.createDefaultUSERIfNeed();

        txtUsername = (TextView) findViewById(R.id.txtUsername);
        txtPass = (TextView) findViewById(R.id.txtPassword);
        txtError = (TextView) findViewById(R.id.txtError);
        createAcount = (TextView) findViewById(R.id.createAcount);
            createAcount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast ms = Toast.makeText(getApplicationContext(),"Create New Acount",Toast.LENGTH_SHORT);
                        ms.show();
                    intent = new Intent(Login.this, RegisterAcount.class);
                    startActivity(intent);
                }
            });
        btnLogin = (Button) findViewById(R.id.btnLogin);
            btnLogin.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    String username = txtUsername.getText().toString();
                    String pass = txtPass.getText().toString();
                    USER user = new USER(username,pass,"","","",0);
                   if( db.isExistUser(user)){
                       intent = new Intent(Login.this, Sliding.class);
                       intent.putExtra("username",user.getUsername());
                       intent.putExtra("password",user.getPass());
                      startActivity(intent);/*
                       intent = new Intent(Login.this, Home.class);
                       startActivity(intent);*/
                   } else{
                       txtError.setText("Vui lòng kiểm tra lại Tài khoản hoặc Mật khẩu");
                       txtError.setVisibility(View.VISIBLE);
                       txtError.setTextColor(Color.RED);
                   }
                }
            });
    }
}

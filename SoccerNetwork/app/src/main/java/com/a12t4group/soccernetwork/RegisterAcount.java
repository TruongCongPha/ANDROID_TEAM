package com.a12t4group.soccernetwork;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.a12t4group.soccernetwork.BEAN.USER;
import com.a12t4group.soccernetwork.DB.Access_District_DB;
import com.a12t4group.soccernetwork.DB.MyDatabaseHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 23/04/2016.
 */
public class RegisterAcount extends Activity {
    EditText txtUsername,  txtEmail, txtPassword, txtRepeatPassword, txtPhoneNumber;
    Spinner district;
    Button btnRegister, btnCancel;
    TextView message;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MyDatabaseHelper dbUser = new MyDatabaseHelper(this.getApplicationContext());
        final Access_District_DB dbDistrict = new Access_District_DB(this.getApplicationContext());


        setContentView(R.layout.register_acount_layout);
        district = (Spinner) findViewById(R.id.r_district);
        Cursor listDistrict =  dbDistrict.getListDistricts();
            int rows = listDistrict.getCount();
            listDistrict.moveToFirst();
        List<String> listDistrictName = new ArrayList<String>();
            for(int i=0;i<rows;i++){
                listDistrictName.add(listDistrict.getString(1));
                listDistrict.moveToNext();
            }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listDistrictName);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            district.setAdapter(dataAdapter);


        btnCancel = (Button) findViewById(R.id.btnCancel);
            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    intent = new Intent(RegisterAcount.this, Login.class);
                        startActivity(intent);
                }
            });
        btnRegister = (Button) findViewById(R.id.btnRegister);
            btnRegister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    txtUsername = (EditText) findViewById(R.id.r_username);
                    txtEmail = (EditText) findViewById(R.id.r_email);
                    txtPassword = (EditText) findViewById(R.id.r_password);
                    txtRepeatPassword = (EditText) findViewById(R.id.r_repeat_password);
                    txtPhoneNumber = (EditText) findViewById(R.id.r_phone_number);
                    message = (TextView) findViewById(R.id.message) ;

                    String username = String.valueOf(txtUsername.getText());
                    String email = String.valueOf(txtEmail.getText());
                    String password = String.valueOf(txtPassword.getText());
                    String repeatPassword = String.valueOf(txtRepeatPassword.getText());
                    String number_phone = String.valueOf(txtPhoneNumber.getText());



                    String nameDistrict = district.getSelectedItem().toString();


                    Log.i("Information","INFORMATION : "+txtUsername.getText()+"    "+txtPassword.getText()
                            +"      "+txtEmail.getText()+"      "+txtPhoneNumber.getText()+"     "+nameDistrict);

                    MyDatabaseHelper dbUser = new MyDatabaseHelper(getApplicationContext());

                    //Kiểm tra tài khoản bắt buộc
                    if(username.equals("")){
                        message.setVisibility(View.VISIBLE);
                        message.setText("Tài khoản là bắt buộc");
                    }
                    //kiểm tra username đã tồn tại???
                    else  if(dbUser.getUser(username)!=null){
                        message.setVisibility(View.VISIBLE);
                        message.setText("Tài khoản đã tồn tại");
                    }
                    //Kiểm tra username hợp lệ
                    else if(!Libraries.isValidate(username)){

                        message.setVisibility(View.VISIBLE);
                        message.setText("Tài khoản không hợp lệ");
                    }
                    //Kiểm tra email hợp lệ
                    else if(!Libraries.isValidateEmail(email)){
                        message.setVisibility(View.VISIBLE);
                        message.setText("Email không hợp lệ");
                    }
                    //kiểm tra mật khẩu họp lệ
                    else if(!Libraries.isValidate(password) || !Libraries.isValidate(repeatPassword) || password.equals("") || repeatPassword.equals("")){

                        message.setVisibility(View.VISIBLE);
                        message.setText("Mật khẩu không hợp lệ");
                    } //Kiểm tra mật khẩu trùng khớp
                    else if(!repeatPassword.equals(password)){
                        message.setVisibility(View.VISIBLE);
                        message.setText("Mật khẩu không trùng khớp");
                    }
                    //Kiểm tra sđt hợp lệ
                    else if(!Libraries.isValidatePhoneNumber(number_phone)){
                        message.setVisibility(View.VISIBLE);
                        message.setText("Số điện thoại không hợp lệ");
                    } else {
                        //thêm mới user
                        USER user = new USER(username,password,email,number_phone,"user",dbDistrict.getDistrict(nameDistrict).getDistrict_id());
                        dbUser.addUser(user);
                        message.setVisibility(View.GONE);
                         Toast.makeText(getApplicationContext(),"Thêm mới user \""+username+"\" thành công", Toast.LENGTH_LONG);
                        intent = new Intent(RegisterAcount.this, Login.class);
                        startActivity(intent);
                    }



                }
            });

    }
}

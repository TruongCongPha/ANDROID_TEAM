package com.a12t4group.soccernetwork.Sliding.Fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.a12t4group.soccernetwork.BEAN.USER;
import com.a12t4group.soccernetwork.DB.MyDatabaseHelper;
import com.a12t4group.soccernetwork.R;
import com.a12t4group.soccernetwork.Sliding.Sliding;

/**
 * Created by User on 07/05/2016.
 */
public class Edit_User extends Fragment {
    View v;
    EditText txtUsername, txtPassword, txtNewPassword, txtRepeatPassword, txtEmail, txtAddress, txtPhoneNumer;
    Button btnSave, btnCancel;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_list_football_grounds, container, false);

        USER user = Sliding.user;

        btnSave = (Button) v.findViewById(R.id.btnSaveEdit);
            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    txtUsername = (EditText) v.findViewById(R.id.edit_username);
                    txtAddress = (EditText) v.findViewById(R.id.edit_address);
                    txtEmail = (EditText) v.findViewById(R.id.edit_txtEmail);
                    txtPassword = (EditText) v.findViewById(R.id.edit_password);
                    txtNewPassword = (EditText) v.findViewById(R.id.edit_new_password);
                    txtRepeatPassword = (EditText) v.findViewById(R.id.edit_repeat_password);
                    txtPhoneNumer =(EditText) v.findViewById(R.id.edit_phone_number);

                    String error = "";

                    MyDatabaseHelper db_user = new MyDatabaseHelper(v.getContext());
                    Cursor cursor = db_user.getAllUsers();
                    if(!txtUsername.getText().toString().equals(Sliding.user.getUsername())){

                        do{
                            cursor.moveToFirst();
                            if(txtUsername.getText().toString().equals(cursor.getString(1)));
                            error = "Tên tài khoản đã tồn tại";
                            break;
                        }while (cursor.moveToNext());
                    }


                }
            });
         btnCancel = (Button) v.findViewById(R.id.btnCancelEdit);
            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getFragmentManager().popBackStack("User_Profile", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                }
            });


        v.setFocusableInTouchMode(true);
        v.requestFocus();
        v.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if( keyCode == KeyEvent.KEYCODE_BACK ) {
                    getFragmentManager().popBackStack("User_Profile", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    return true;
                } else {
                    return false;
                }
            }
        });
        return v;
    }
}

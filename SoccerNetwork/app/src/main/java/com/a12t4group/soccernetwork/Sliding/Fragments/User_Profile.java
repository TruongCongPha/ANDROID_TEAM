package com.a12t4group.soccernetwork.Sliding.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.a12t4group.soccernetwork.BEAN.USER;
import com.a12t4group.soccernetwork.DB.Access_District_DB;
import com.a12t4group.soccernetwork.R;
import com.a12t4group.soccernetwork.Sliding.Sliding;

/**
 * Created by User on 27/04/2016.
 */
public class User_Profile extends Fragment{
    EditText txtUsername, txtEmail, txtPhoneNumber, txtAddress;
    Button btnEdit, btnCancel;
    Access_District_DB db_district;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_user_profile, container, false);

        db_district = new Access_District_DB(v.getContext());

        txtUsername = (EditText) v.findViewById(R.id.username);
        txtEmail = (EditText) v.findViewById(R.id.txtEmail);
        txtPhoneNumber = (EditText) v.findViewById(R.id.phone_number);
        txtAddress = (EditText) v.findViewById(R.id.address);
        USER user = Sliding.user;
            txtUsername.setText(user.getUsername());
            txtEmail.setText(user.getEmail());
            txtPhoneNumber.setText(user.getPhone_number());
            txtAddress.setText(db_district.getDistrict(user.getDistrict_id()).getDistrict_name());

        /*btnCancel = (Button) v.findViewById(R.id.btnCancel);
            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getFragmentManager().popBackStack("Home", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                }
            });*/

        /*btnEdit = (Button) v.findViewById(R.id.btnEdit);
            btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Edit_User edit_user = new Edit_User();
                    FragmentTransaction fT_Edit_User = getActivity().getSupportFragmentManager().beginTransaction();
                    fT_Edit_User.replace(R.id.main_content, edit_user).addToBackStack("User_Profile").commit() ;
                }
            });*/

        v.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if( keyCode == KeyEvent.KEYCODE_BACK ) {
                    //getFragmentManager().popBackStack("Home", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    return true;
                } else {
                    return false;
                }
            }
        });

        return v ;
    }
}

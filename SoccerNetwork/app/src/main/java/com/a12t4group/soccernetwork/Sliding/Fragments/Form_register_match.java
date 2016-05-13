package com.a12t4group.soccernetwork.Sliding.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.a12t4group.soccernetwork.BEAN.MATCHES;
import com.a12t4group.soccernetwork.DB.Access_Matches_DB;
import com.a12t4group.soccernetwork.Libraries;
import com.a12t4group.soccernetwork.R;
import com.a12t4group.soccernetwork.Sliding.Sliding;

import java.util.Date;

/**
 * Created by User on 06/05/2016.
 */
public class Form_register_match extends Fragment {

    View v;
    private int id_branch, id_fg, price;
    String fg_name, branch_name, branch_address, match_time, match_date;
    EditText txt_branch_name, txt_fg_name, txt_branch_address, txt_match_time,txt_match_date, txt_match_price;
    Button btn_reg, btn_cancel;
    String start_time="", end_time="";

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_form_register_match_layout, container, false);

        txt_branch_name = (EditText) v.findViewById(R.id.txt_branch_name);
            txt_branch_name.setFocusable(false);
        txt_fg_name = (EditText) v.findViewById(R.id.txt_fg_name);
            txt_fg_name.setFocusable(false);
        txt_branch_address = (EditText) v.findViewById(R.id.txt_branch_address);
            txt_branch_address.setFocusable(false);
        txt_match_time = (EditText) v.findViewById(R.id.txt_match_time);
            txt_match_time.setFocusable(false);
        txt_match_date = (EditText) v.findViewById(R.id.txt_match_date);
            txt_match_date.setFocusable(false);
        txt_match_price = (EditText) v.findViewById(R.id.txt_match_price);
            txt_match_price.setFocusable(false);
        btn_reg = (Button) v.findViewById(R.id.btn_reg);
        btn_cancel = (Button) v.findViewById(R.id.btn_cancel);
        Bundle bundle = this.getArguments();
            id_branch = bundle.getInt("id_branch",1);
            branch_address = bundle.getString("branch_address","");
            branch_name = bundle.getString("branch_name","");
            id_fg = bundle.getInt("id_fg",1);
            fg_name = bundle.getString("fg_name","");
            match_date = bundle.getString("date","");
            match_time = bundle.getString("time","");
            price = bundle.getInt("price",0);
        txt_branch_name.setText(branch_name);
        txt_branch_address.setText(branch_address);
        txt_fg_name.setText(fg_name);
        txt_match_date.setText(match_date);
        txt_match_time.setText(match_time);
        txt_match_price.setText(String.valueOf(price));



        btn_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //lấy thời gian bắt đầu và kết thúc
                start_time = txt_match_time.getText().toString().split("h")[0];
                end_time=""+(Integer.parseInt(start_time)+1);
                MATCHES match = new MATCHES(id_fg, Sliding.user.getUser_id(),true, Libraries.convertStringToDate(match_date+" "+start_time+":00:00"),
                        Libraries.convertStringToDate(match_date+" "+end_time+":00:00"), price,new Date());
                String insert = "Insert into MATCHES(id_fg, user_id,status,start_time, end_time, price, is_verified, created, updated, deleted)" +
                        " values("+id_fg+","+ Sliding.user.getUser_id()+", 'true', '"+match_date+" "+start_time+":00:00' , "
                        + "'"+match_date+" "+end_time+":00:00' , "+price+", , '"+ Libraries.getCurrentDateString()+"', '' , '')";
                Log.i("INSERT MATCH","QUERY OF INSERT MATCH: "+insert);
                Access_Matches_DB db_matches = new Access_Matches_DB(v.getContext());
                    long kq = db_matches.addMatch(match);
                Toast message;
                if(kq==-1.0){ message = Toast.makeText(v.getContext(), "Đăng ký trận đấu thất bại. Kiểm tra lại thông tin đăn ký " , Toast.LENGTH_SHORT);
                            message.show();
                }
                else {
                    message = Toast.makeText(v.getContext(), "Đăng ký trận đấu thành công " , Toast.LENGTH_SHORT);
                    message.show();
                    Matches_profile matches_profile = new Matches_profile();
                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.main_content, matches_profile).commit() ;
                }

            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });


        v.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if( keyCode == KeyEvent.KEYCODE_BACK ) {
                    getFragmentManager().popBackStack("ListFootballGround", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    Log.i("RETURN", "COME BACK TO ListFootballGround");
                    return true;
                } else {
                    return false;
                }
            }
        });
        return v;
    }

}

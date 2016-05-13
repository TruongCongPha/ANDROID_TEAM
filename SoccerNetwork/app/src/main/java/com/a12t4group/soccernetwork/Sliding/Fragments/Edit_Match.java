package com.a12t4group.soccernetwork.Sliding.Fragments;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.a12t4group.soccernetwork.BEAN.MATCHES;
import com.a12t4group.soccernetwork.DB.Access_Branches_DB;
import com.a12t4group.soccernetwork.DB.Access_Football_Ground_DB;
import com.a12t4group.soccernetwork.DB.Access_Matches_DB;
import com.a12t4group.soccernetwork.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by User on 07/05/2016.
 */
public class Edit_Match extends Fragment {

    View v;
    Access_Matches_DB db_match ;
    Access_Branches_DB db_branch;
    Access_Football_Ground_DB db_fg;
    Spinner txtTime;
    EditText txtBranchName, txtFGName, txtDate, txtPrice, txtAddress;
    Button btnChooseDate, btnUpdateEditMatch, btnCancelEditMatch;
    DatePickerDialog datePickerDialog;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_edit_match_layout, container, false);

        db_match = new Access_Matches_DB(v.getContext());
        db_branch = new Access_Branches_DB(v.getContext());
        db_fg = new Access_Football_Ground_DB(v.getContext());
        final String[] time_label ={"07h - 08h", "08h - 09h", "09h - 10h","","13h - 14h", "14h - 15h",
                "15h - 16h", "16h - 17h", "17h - 18h", "18h - 19h", "19h - 20h",
                "20h - 21h", "21h - 22h", "22h - 23h"};
        Bundle bundle = this.getArguments();
            int match_id = bundle.getInt("match_id",1);
        final MATCHES match = db_match.getMatch(match_id);
            Log.i("ID FG ", "ID FG : "+match.getId_fg());
        txtAddress = (EditText) v.findViewById(R.id.txtEditMatchAddress);
            txtAddress.setFocusable(false);
            txtAddress.setText(db_branch.getBranch(db_fg.getFootball_Ground(match.getId_fg()).getId_branch()).getAddress());
        txtBranchName = (EditText) v.findViewById(R.id.txtEditMatchBranchName);
            txtBranchName.setFocusable(false);
            txtBranchName.setText(db_branch.getBranch(db_fg.getFootball_Ground(match.getId_fg()).getId_branch()).getName_branch());
        txtFGName = (EditText) v.findViewById(R.id.txtEditMatchFGName);
            txtFGName.setFocusable(false);
            txtFGName.setText(db_fg.getFootball_Ground(match.getId_fg()).getFg_name());
        txtTime = (Spinner) v.findViewById(R.id.txtEditMatchTime);

        final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        final SimpleDateFormat formatYMD = new SimpleDateFormat("yyyy-MM-dd");

        List<String> listTime = new ArrayList<String>();
        int indexOfTime=0;
            String start_time =format.format(match.getStart_time()).split(" ")[1].substring(0, 2);
            for(int i=0; i<time_label.length; i++){
                if(i!=3 ) {
                    listTime.add(time_label[i]);
                    if (time_label[i].substring(0, 2).equals(start_time)) {
                        Log.i("START TIME ", "START TIME: "+start_time);
                        indexOfTime=(i>3)?(i-1):i;
                    }
                }
            }
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(v.getContext(), android.R.layout.simple_spinner_item, listTime);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            txtTime.setAdapter(dataAdapter);
            txtTime.setSelection(indexOfTime);

        txtDate = (EditText) v.findViewById(R.id.txtEditMatchDate);
            txtDate.setFocusable(false);
            txtDate.setText(formatYMD.format(match.getStart_time()).split(" ")[0]);
        txtPrice = (EditText) v.findViewById(R.id.txtEditMatchPrice);
            txtPrice.setFocusable(false);
            txtPrice.setText(String.valueOf(match.getPrice()));

        btnChooseDate = (Button) v.findViewById(R.id.btnChooseDate);
            btnChooseDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Calendar newCalendar = Calendar.getInstance();
                    datePickerDialog = new DatePickerDialog(v.getContext(), new DatePickerDialog.OnDateSetListener() {

                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            Calendar newDate = Calendar.getInstance();
                            newDate.set(year, monthOfYear, dayOfMonth);
                            txtDate.setText(formatYMD.format(newDate.getTime()));
                        }

                    },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
                    datePickerDialog.getDatePicker().setMinDate(newCalendar.getTimeInMillis());
                    datePickerDialog.show();
                }
            });

        btnUpdateEditMatch = (Button) v.findViewById(R.id.btnUpdateEditMatch);
            btnUpdateEditMatch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Cursor cursor = db_match.getListMatchesCorrespondMatchDay(txtDate.getText().toString());
                    final String list_Start_time [] = new String[cursor.getCount()];
                    cursor.moveToFirst();
                    Log.i("LENGTH", "LENGTH: "+list_Start_time.length);
                    for (int k=0;k<list_Start_time.length;k++){
                        list_Start_time[k] = cursor.getString(4).split(":")[0].substring(11);
                        Log.i("StartTime", "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!START TIME: "+list_Start_time[k]);
                        cursor.moveToNext();
                    }

                    boolean isSet = false;
                    for (int k=0; k < list_Start_time.length; k++){
                        if(txtTime.getSelectedItem().toString().split("h")[0].equals(list_Start_time[k])){
                            isSet = true;
                            break;
                        }
                    }
                    if(isSet==true){
                        Toast message= Toast.makeText(v.getContext(), "Đã có trận đấu diễn ra vào thời gian  " +txtTime.getSelectedItem().toString()
                                +" "+txtDate.getText().toString(), Toast.LENGTH_SHORT);
                        message.show();
                        Log.i("LOI CC", "LOI CCCCCCCCC");
                    }else{
                        try {
                            Log.i("LOI CC", "LOI CCCCCCCCCAAAA");
                            String time = txtTime.getSelectedItem().toString();
                            Log.i("LOI CC", "LOI CCCCCCCCCAAAA "+time.substring(0,2));
                            match.setStart_time(format.parse(txtDate.getText().toString() + " " + time.substring(0, 2) + ":00:00"));
                            Log.i("LOI CC", "LOI CCCCCCCCCAAAA "+time.substring(6, 8));
                            match.setEnd_time(format.parse(txtDate.getText().toString() + " " + time.substring(6, 8) + ":00:00"));
                            Log.i("LOI CC", "LOI CCCCCCCCCAAAA");
                            int kq = db_match.updateMatch(match);
                            if(kq!=-1){
                                Toast message= Toast.makeText(v.getContext(), "Cập nhật thành công", Toast.LENGTH_SHORT);
                                message.show();
                                Matches_profile matches_profile = new Matches_profile();
                                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                                    fragmentTransaction.replace(R.id.main_content, matches_profile).commit() ;
                            } else {
                                Log.i("LOI CC", "LOI CCCCCCCCCBBBB");
                                Toast message = Toast.makeText(v.getContext(), "Có lỗi xảy ra :-((((", Toast.LENGTH_SHORT);
                                message.show();
                            }
                        } catch (Exception e){ }
                    }

                }
            });

        btnCancelEditMatch = (Button) v.findViewById(R.id.btnCancelEditMatch);
            btnCancelEditMatch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getFragmentManager().popBackStack();
                }
            });


        return v;
    }
}

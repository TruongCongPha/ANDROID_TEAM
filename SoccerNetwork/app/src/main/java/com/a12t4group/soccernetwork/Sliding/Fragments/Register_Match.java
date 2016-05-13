package com.a12t4group.soccernetwork.Sliding.Fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.a12t4group.soccernetwork.BEAN.BRANCH;
import com.a12t4group.soccernetwork.DB.Access_Branches_DB;
import com.a12t4group.soccernetwork.DB.Access_Football_Ground_DB;
import com.a12t4group.soccernetwork.DB.Access_Matches_DB;
import com.a12t4group.soccernetwork.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class Register_Match extends Fragment {

    EditText txtDate;
    Button btnChooseDate;

    TableLayout tableLayout ;
    DatePickerDialog datePickerDialog;
    SimpleDateFormat format;
    GoogleMap map;
    private int id_branch, id_fg, price;
    String fg_name;
    Access_Branches_DB db_branch;
    Access_Matches_DB db_match;
    Access_Football_Ground_DB db_football_ground;
    BRANCH branch = new BRANCH();
    float latitide, longitude;
    Context context;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_register_match, container, false);
        context = v.getContext();
        format = new SimpleDateFormat("yyyy-MM-dd");

        tableLayout = (TableLayout) v.findViewById(R.id.table);
        Bundle bundle = this.getArguments();
            id_branch = bundle.getInt("id_branch",1);
            id_fg = bundle.getInt("id_fg",1);
            fg_name = bundle.getString("fg_name","");
            price = bundle.getInt("price",0);
        db_branch = new Access_Branches_DB(v.getContext());
        db_match = new Access_Matches_DB(v.getContext());
        db_football_ground = new Access_Football_Ground_DB(v.getContext());
            branch = db_branch.getBranch(id_branch);
            latitide = branch.getLatitude();
            longitude = branch.getLongitude();
        Log.i("Latitude","Latitude --> "+latitide+"\t\t Longitude --> "+longitude);

        map = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapid)).getMap();
        Log.i("MAP", "MAP !!!!!!!!!!!!!!!");
        map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        Log.i("MAP", "MAP !!!!!!!!!!!!!!!");
        LatLng fg = new LatLng(latitide , longitude);
        Log.i("MAP", "MAP !!!!!!!!!!!!!!!");
        map.addMarker(new MarkerOptions().position(fg).title(fg_name));
        Log.i("MAP", "MAP !!!!!!!!!!!!!!!");
        map.moveCamera(CameraUpdateFactory.newLatLng(fg));
        Log.i("MAP", "MAP !!!!!!!!!!!!!!!");
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(fg)     //Set the center of the map to lacation user
                    .zoom(15)       // Set the zoom
                    .build();
        Log.i("MAP", "MAP !!!!!!!!!!!!!!!");
            map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        Log.i("MAP", "MAP !!!!!!!!!!!!!!!");

        txtDate = (EditText) v.findViewById(R.id.txtDate);
        txtDate.setFocusable(false);
        btnChooseDate = (Button) v.findViewById(R.id.btnChooseDate);
            btnChooseDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Calendar newCalendar = Calendar.getInstance();
                    datePickerDialog = new DatePickerDialog(v.getContext(), new DatePickerDialog.OnDateSetListener() {

                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            DatePicker datePicker = datePickerDialog.getDatePicker();
                            Calendar newDate = Calendar.getInstance();
                            newDate.set(year, monthOfYear, dayOfMonth);
                                txtDate.setText(format.format(newDate.getTime()));
                                tableLayout.removeAllViews();
                                BuildTable();
                        }

                    },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
                    datePickerDialog.getDatePicker().setMinDate(newCalendar.getTimeInMillis());
                    datePickerDialog.show();
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        Fragment f = getChildFragmentManager().findFragmentById(R.id.mapid);
        if (f != null)
            getFragmentManager().beginTransaction().remove(f).commit();
    }

    public void BuildTable(){
        Cursor cursor = db_match.getListMatchesCorrespondMatchDay(txtDate.getText().toString()) ;
        final String list_Start_time [] = new String[cursor.getCount()];
        Log.i("LENGTH","LENGTH OF LIST START TIME : "+list_Start_time.length);
        //yyyy-MM-dd HH:MM:ss
        cursor.moveToFirst();
        for (int k=0;k<list_Start_time.length;k++){
            Log.i("TIME", "TIME "+cursor.getString(4));
            list_Start_time[k] = cursor.getString(4).split(":")[0].substring(11);
            Log.i("StartTime", "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!START TIME: "+list_Start_time[k]);
            cursor.moveToNext();
        }
        final int rows =15;
        int col = 4;
        cursor.moveToFirst();
        TableRow rowTitle = new TableRow(this.getContext());
        rowTitle.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.MATCH_PARENT));
        rowTitle.setClickable(false);
        rowTitle.setId(0);
        rowTitle.setBackgroundColor(Color.argb(255,98,192,255));

        TextView[] views = new TextView[4];
        final String[] time_label ={"07h - 08h", "08h - 09h", "09h - 10h","","13h - 14h", "14h - 15h",
                                "15h - 16h", "16h - 17h", "17h - 18h", "18h - 19h", "19h - 20h",
                                "20h - 21h", "21h - 22h", "22h - 23h"};
        String[] title ={"Giờ", "Giá", "Trạng thái","Hành động"};
        for (int i=0;i<views.length;i++){
            TextView v = new TextView(this.getContext());
            v.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            v.setGravity(Gravity.CENTER_HORIZONTAL);
            v.setPadding(8,8,8,8);
            v.setText(title[i]);
            v.setMinimumWidth((int)(Home.xScreen/4));
            rowTitle.addView(v);
        }
        tableLayout.addView(rowTitle);

        for(int i=0;i<rows-1; i++){

            final TableRow row = new TableRow(this.getContext());
            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.MATCH_PARENT));
            row.setId(i+1);
            if(i%2==0) row.setBackgroundColor(Color.LTGRAY);
            else row.setBackgroundColor(Color.WHITE);
            if(i==3) {
                row.setClickable(false);
                row.setBackgroundColor(Color.rgb(232,150,241));
                for(int j=0;j<col;j++){
                    row.addView(new TextView(context));
                }
            }
                else {
                    row.setClickable(true);
                    for(int j=0;j<col;j++){
                        TextView txt = new TextView(this.getContext());
                        txt.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                                TableRow.LayoutParams.WRAP_CONTENT));
                        txt.setGravity(Gravity.LEFT);
                        txt.setPadding(8,8,8,8);
                        switch(j+1){
                            case 1: txt.setText(time_label[i]); break;
                            case 2:
                                if(i>=9){ //tương ứng >=18h
                                    txt.setText(String.valueOf(price*1.2)); //tăng giá thêm 20%
                                } else {
                                    txt.setText(String.valueOf(price));
                                }break;
                            case 3:
                                boolean isSet = false;
                                for (int k=0; k < list_Start_time.length; k++){
                                    if(time_label[i].split("h")[0].equals(list_Start_time[k])){
                                        txt.setText("Đã đặt");
                                        txt.setTextColor(Color.RED);
                                        isSet = true;
                                        break;
                                    }
                                }
                                if(isSet == false) txt.setText("Chưa đặt"); break;
                            case 4:
                                if(((TextView)row.getChildAt(2)).getText().toString().equals("Chưa đặt")){
                                    txt.setText("ĐĂNG KÝ");
                                    txt.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            
                                            Form_register_match form_register_match = new Form_register_match();
                                            Bundle bundle = new Bundle();
                                                bundle.putInt("id_branch", id_branch);
                                                bundle.putString("branch_name", branch.getName_branch());
                                                bundle.putString("branch_address", branch.getAddress());
                                                bundle.putInt("id_fg", id_fg);
                                                bundle.putString("fg_name", db_football_ground.getFootball_Ground(id_fg).getFg_name());
                                                bundle.putString("time",((TextView)row.getChildAt(0)).getText().toString());
                                                bundle.putString("date",txtDate.getText().toString());
                                                bundle.putInt("price", price);
                                            form_register_match.setArguments(bundle);
                                            Log.i("ERROR","ERROR 2 ===================");
                                            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                                                fragmentTransaction.replace(R.id.main_content, form_register_match).addToBackStack("RegisterMatch").commit() ;
                                        }
                                    });
                                }
                                break;
                        }
                        row.addView(txt);
                    }
            }
            tableLayout.addView(row);
        }
        db_match.close();
        db_match.close();
    }
}

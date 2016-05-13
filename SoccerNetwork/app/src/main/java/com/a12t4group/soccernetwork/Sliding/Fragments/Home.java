package com.a12t4group.soccernetwork.Sliding.Fragments;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.a12t4group.soccernetwork.BEAN.BRANCH;
import com.a12t4group.soccernetwork.DB.Access_Branches_DB;
import com.a12t4group.soccernetwork.DB.Access_District_DB;
import com.a12t4group.soccernetwork.R;

/**
 * Created by User on 27/04/2016.
 */
public class Home extends Fragment{
    TableLayout tableLayout ;
    Access_Branches_DB db_branches;
    Access_District_DB db_district;
    BRANCH branch_OnClick;
    View v;

    public static float xScreen, yScreen;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_list_branches, container, false);

        db_branches = new Access_Branches_DB(v.getContext());
        db_district = new Access_District_DB(v.getContext());
        TextView titleHome = (TextView) v.findViewById(R.id.title_home);
            titleHome.setShadowLayer(10,0,0,Color.BLUE);
        Display display = ((WindowManager)this.getContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        xScreen = display.getWidth();
        yScreen =  display.getHeight();

        tableLayout = (TableLayout) v.findViewById(R.id.table);

        BuildTable();

        /*v.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if( keyCode == KeyEvent.KEYCODE_BACK ) {
                    Log.i("RETURN", "COME BACK TO NULL");
                    return true;
                } else {
                    return false;
                }
            }
        });*/
        return v ;

    }
    public void BuildTable(){
        Cursor cursor = db_branches.getListBranchs();
        int rows = cursor.getCount()+1;
        int col = cursor.getColumnCount();

        cursor.moveToFirst();
        TableRow rowTitle = new TableRow(this.getContext());
        rowTitle.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.MATCH_PARENT));
        rowTitle.setClickable(false);
        rowTitle.setId(0);
        rowTitle.setBackgroundColor(Color.argb(255,98,192,255));

        TextView[] views = new TextView[6];
        String[] title ={"ID_BRANCH","TÊN TRUNG TÂM","SĐT", "ĐỊA CHỈ","VĨ ĐỘ","KINH ĐỘ"};
        for (int i=0;i<views.length;i++){
            TextView v = new TextView(this.getContext());
            v.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            v.setGravity(Gravity.CENTER_HORIZONTAL);
            v.setPadding(8,8,8,8);
            v.setText(title[i]);
            if(i==0 || i == 4 || i==5) v.setVisibility(View.GONE);
            v.setMinimumWidth((i-1)*60 + (int)(xScreen/3));
            rowTitle.addView(v);
        }
        tableLayout.addView(rowTitle);

        for(int i=0;i<rows-1; i++){
            final TableRow row = new TableRow(this.getContext());
            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.MATCH_PARENT));
            row.setClickable(true);
            row.setPadding(15,4,15,4);
            row.setId(i+1);
            if(i%2==0) row.setBackgroundColor(Color.LTGRAY);
            else row.setBackgroundColor(Color.WHITE);


            for(int j=0;j<col;j++){
                TextView txt = new TextView(this.getContext());
                txt.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT));
                txt.setGravity(Gravity.LEFT);
                txt.setPadding(8,8,8,8);
                // không hiển thị id_branch
                if(j==0){
                    txt.setText(cursor.getString(j));
                    txt.setVisibility(View.GONE);
                } else if(j==4 || j==5){  //k hiển thị vĩ độ và kinh độ

                    txt.setText(cursor.getString(j));
                    txt.setVisibility(View.GONE);
                }
                //đặt địa chỉ cho cột cuối cùng, nhưng không hiển thị
                else if(j==(col-1)){
                    txt.setText(db_district.getDistrict(cursor.getInt(j)).getDistrict_name());
                    txt.setVisibility(View.GONE);
                }else txt.setText(cursor.getString(j));
                row.addView(txt);
            }
            row.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try{
                        branch_OnClick = new BRANCH();
                        branch_OnClick.setId_branch(Integer.parseInt(((TextView) row.getChildAt(0)).getText().toString()));
                        branch_OnClick.setName_branch(((TextView) row.getChildAt(1)).getText().toString());

                        branch_OnClick.setNumber_phone(((TextView) row.getChildAt(2)).getText().toString());
                        branch_OnClick.setAddress(((TextView)row.getChildAt(3)).getText().toString());
                        branch_OnClick.setLatitude(Float.parseFloat(((TextView) row.getChildAt(4)).getText().toString()));
                        branch_OnClick.setLongitude(Float.parseFloat(((TextView) row.getChildAt(5)).getText().toString()));
                        branch_OnClick.setDistrict_id( db_district.getDistrict(((TextView) row.getChildAt(6)).getText().toString()).getDistrict_id());
                        Log.i("TAG", "Value row of table --------->>>>>>>>>>>>>>>>>> " + branch_OnClick.getId_branch());
                        Toast message = Toast.makeText(v.getContext(), "Trung tâm sân bóng đã chọn: " + branch_OnClick.getName_branch(), Toast.LENGTH_SHORT);
                        message.show();

                        List_Football_Ground list_football_ground = new List_Football_Ground();
                            Bundle bundle = new Bundle();
                                bundle.putInt("id_branch", branch_OnClick.getId_branch());
                        list_football_ground.setArguments(bundle);
                        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.main_content, list_football_ground).addToBackStack("Home").commit() ;
                    } catch (Exception e){
                        Log.e("ERROR", "LOI KHOI TAO MATCHES -------------------------------");
                    }
                }
            });
            cursor.moveToNext();
            tableLayout.addView(row);
        }
        db_branches.close();
        db_district.close();
    }


}

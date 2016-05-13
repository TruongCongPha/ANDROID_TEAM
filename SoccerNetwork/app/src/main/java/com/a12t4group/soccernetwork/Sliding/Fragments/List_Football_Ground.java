package com.a12t4group.soccernetwork.Sliding.Fragments;

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
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.a12t4group.soccernetwork.BEAN.FOOTBALL_GROUND;
import com.a12t4group.soccernetwork.DB.Access_Branches_DB;
import com.a12t4group.soccernetwork.DB.Access_District_DB;
import com.a12t4group.soccernetwork.DB.Access_Football_Ground_DB;
import com.a12t4group.soccernetwork.R;

/**
 * Created by User on 28/04/2016.
 */
public class List_Football_Ground  extends Fragment {
    TableLayout tableLayout ;
    Access_Branches_DB db_branches;
    Access_District_DB db_district;
    Access_Football_Ground_DB db_football_ground;
    FOOTBALL_GROUND football_Ground;
    View v;
    int id_branch;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_list_football_grounds, container, false);

        db_branches = new Access_Branches_DB(v.getContext());
        db_district = new Access_District_DB(v.getContext());
        db_football_ground = new Access_Football_Ground_DB(v.getContext());
            Bundle bundle = this.getArguments();
            id_branch = bundle.getInt("id_branch",1);
        Log.i("ID BRANCH","ID BRANCH = "+id_branch+" ********************************");
        TextView title_Football_ground = (TextView) v.findViewById(R.id.title_football_ground);
            title_Football_ground.setText(title_Football_ground.getText()+" "+db_branches.getBranch(id_branch).getName_branch());

        tableLayout = (TableLayout) v.findViewById(R.id.table);

        BuildTable();


        /*v.setFocusableInTouchMode(true);
        v.requestFocus();*/
        v.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if( keyCode == KeyEvent.KEYCODE_BACK ) {
                    getFragmentManager().popBackStack("Home", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    Log.i("RETURN", "COME BACK TO HOME");
                    return true;
                } else {
                    return false;
                }
            }
        });
        return v ;

    }
    public void BuildTable(){
        Cursor cursor = db_football_ground.getListFootballGrounds(id_branch) ;
        int rows = cursor.getCount()+1;
        int col = cursor.getColumnCount();
        cursor.moveToFirst();
        TableRow rowTitle = new TableRow(this.getContext());
        rowTitle.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.MATCH_PARENT));
        rowTitle.setClickable(false);
        rowTitle.setId(0);
        rowTitle.setBackgroundColor(Color.argb(255,98,192,255));

        TextView[] views = new TextView[4];
        String[] title ={"ID_FG", "TÊN SÂN", "SỐ NGƯỜI","GIÁ"};
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
            row.setClickable(true);
            row.setId(i+1);
            if(i%2==0) row.setBackgroundColor(Color.LTGRAY);
            else row.setBackgroundColor(Color.WHITE);


            //không lấy id_branch
            for(int j=0;j<col-1;j++){
                TextView txt = new TextView(this.getContext());
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
                    try{
                        football_Ground = new FOOTBALL_GROUND();
                        football_Ground.setId_fg(Integer.parseInt(((TextView) row.getChildAt(0)).getText().toString()));
                        football_Ground.setFg_name(((TextView) row.getChildAt(1)).getText().toString());
                        football_Ground.setMax_player(Integer.parseInt(((TextView) row.getChildAt(2)).getText().toString()));
                        football_Ground.setPrice(Integer.parseInt(((TextView) row.getChildAt(3)).getText().toString()));
                        football_Ground.setId_branch(id_branch);
                        Log.i("TAG", "Value of FOOTBAL GROUND --------->>>>>>>>>>>>>>>>>> " + football_Ground.getId_fg());
                        Toast message = Toast.makeText(v.getContext(), "Sân bóng đã chọn: " +football_Ground.getFg_name(), Toast.LENGTH_SHORT);
                        message.show();

                        Register_Match register_match = new Register_Match();
                        Bundle bundle = new Bundle();
                            bundle.putInt("id_fg", football_Ground.getId_fg());
                            bundle.putString("fg_name", football_Ground.getFg_name());
                            bundle.putInt("price", football_Ground.getPrice());
                            bundle.putInt("id_branch", id_branch);
                        register_match.setArguments(bundle);
                        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.main_content, register_match).addToBackStack("ListFootballGround").commit() ;


                    } catch (Exception e){
                        Log.e("ERROR", "LOI KHOI TAO FOOTBAL GROUND-------------------------------");
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

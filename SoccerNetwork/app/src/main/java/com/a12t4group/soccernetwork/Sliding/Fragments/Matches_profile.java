package com.a12t4group.soccernetwork.Sliding.Fragments;

import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.a12t4group.soccernetwork.BEAN.MATCHES;
import com.a12t4group.soccernetwork.BEAN.USER;
import com.a12t4group.soccernetwork.DB.Access_Matches_DB;
import com.a12t4group.soccernetwork.R;
import com.a12t4group.soccernetwork.Sliding.Sliding;

import java.text.SimpleDateFormat;

/**
 * Created by User on 27/04/2016.
 */
public class Matches_profile extends Fragment {
    TableLayout tableLayout ;
    Access_Matches_DB db_Matches;
    MATCHES matches_OnClick;
    SimpleDateFormat format, format1;
    View v;
    int indexSelected;
    USER user;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list_matches_corresponed_with_user, container, false);
        user = Sliding.user;
        format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        format1 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        db_Matches = new Access_Matches_DB(v.getContext());
        tableLayout = (TableLayout) v.findViewById(R.id.table);

        BuildTable();

        v.setFocusableInTouchMode(true);
        v.requestFocus();
        v.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if( keyCode == KeyEvent.KEYCODE_BACK ) {
                    Log.i("Return", "RETURN HOME");
                    return true;
                } else {
                    return false;
                }
            }
        });


        return v ;
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.edit_item:
                Edit_Match edit_match = new Edit_Match();
                Bundle bundle = new Bundle();
                    bundle.putInt("match_id",indexSelected);
                edit_match.setArguments(bundle);
                FragmentTransaction fT_edit_match = getActivity().getSupportFragmentManager().beginTransaction();
                    fT_edit_match.replace(R.id.main_content, edit_match).addToBackStack("Matches_profile").commit();
                break;
            case R.id.delete_item:
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());

                // Setting Dialog Title
                alertDialog.setTitle("Delete...");

                // Setting Dialog Message
                alertDialog.setMessage("Do you want to delete this?");


                // Setting Positive "Yes" Button
                alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // User pressed YES button. Write Logic Here
                        int kq_delete = db_Matches.deleteMatch(db_Matches.getMatch(indexSelected));
                        if(kq_delete!=-1){
                            Toast.makeText(getActivity(), "Xóa trận đấu thành công", Toast.LENGTH_SHORT).show();
                            tableLayout.removeAllViews();
                            BuildTable();
                        }else{
                            Toast.makeText(getActivity(), "Có lỗi đã xảy ra", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

                // Setting Negative "NO" Button
                alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // User pressed No button. Write Logic Here
                        Toast.makeText(getActivity(), "You clicked on NO", Toast.LENGTH_SHORT).show();
                    }
                });

                alertDialog.show();
                break;
            case R.id.detail_item:
                Match_Detail match_detail = new Match_Detail();
                Bundle bundle1 = new Bundle();
                    bundle1.putInt("match_id",indexSelected);
                match_detail.setArguments(bundle1);
                FragmentTransaction fT_infor_match = getActivity().getSupportFragmentManager().beginTransaction();
                fT_infor_match.replace(R.id.main_content, match_detail).addToBackStack("Matches_profile").commit();
                break;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Hành động");
        MenuInflater menuInflater = getActivity().getMenuInflater();
            menuInflater.inflate(R.menu.menu_item, menu);
    }


    public void BuildTable(){
        Cursor cursor = db_Matches.getListMatchesCorrespondWithUser(user.getUser_id());
        int rows = cursor.getCount()+1;
        int col = cursor.getColumnCount();
        cursor.moveToFirst();
        TableRow rowTitle = new TableRow(this.getContext());
        rowTitle.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.MATCH_PARENT));
        rowTitle.setClickable(false);
        rowTitle.setId(0);
        rowTitle.setBackgroundColor(Color.argb(255,98,192,255));

        TextView[] views = new TextView[11];
        String[] title ={"MATCH_ID", "FG_ID", "USER_ID","Status", "Start","End", "Price", "Verified", "Created","Updated","Deleted"};
        for (int i=0;i<views.length;i++){
            TextView v = new TextView(this.getContext());
            v.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            v.setGravity(Gravity.CENTER_HORIZONTAL);
            v.setPadding(8,8,8,8);
            v.setText(title[i]);
            if(i==0 || i==1 || i==2 || i==3 || i==7 || i==9 || i==10) v.setVisibility(View.GONE);
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
            if(cursor.getString(4).toString().split(" ")[0].equals(format.format(System.currentTimeMillis()).split(" ")[0])) row.setBackgroundColor(getResources().getColor(R.color.lightRed));

            // không hiển thị "MATCH_ID", "FG_ID", "USER_ID","Status","Verified", "Created","Updated","Deleted"
            for(int j=0;j<col;j++){
                TextView txt = new TextView(this.getContext());
                txt.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT));
                txt.setGravity(Gravity.LEFT);
                txt.setPadding(8,8,8,8);
                txt.setText(cursor.getString(j));
                if(j==0 || j==1 || j==2 || j==3 || j==7 || j==9 || j==10) txt.setVisibility(View.GONE);
                row.addView(txt);
            }
            row.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    v.showContextMenu();
                    indexSelected = Integer.parseInt(((TextView)row.getChildAt(0)).getText().toString());
                    Log.i("MATCH_ID", "MATCH ID: "+indexSelected);
                    return false;
                }
            });
            registerForContextMenu(row);
            cursor.moveToNext();
            tableLayout.addView(row);
        }
        db_Matches.close();
    }



}

package com.a12t4group.soccernetwork.Sliding.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.a12t4group.soccernetwork.BEAN.MATCHES;
import com.a12t4group.soccernetwork.DB.Access_Branches_DB;
import com.a12t4group.soccernetwork.DB.Access_Football_Ground_DB;
import com.a12t4group.soccernetwork.DB.Access_Matches_DB;
import com.a12t4group.soccernetwork.R;

import java.text.SimpleDateFormat;

/**
 * Created by User on 08/05/2016.
 */
public class Match_Detail extends Fragment {
    View v;
    Access_Matches_DB db_match ;
    Access_Branches_DB db_branch;
    Access_Football_Ground_DB db_fg;
    EditText txtBranchName, txtFGName, txtDate, txtPrice, txtAddress, txtTime;
    Button btnCancelInforMatch;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_match_detail_layout, container, false);
        db_match = new Access_Matches_DB(v.getContext());
        db_branch = new Access_Branches_DB(v.getContext());
        db_fg = new Access_Football_Ground_DB(v.getContext());


        final String[] time_label ={"07h - 08h", "08h - 09h", "09h - 10h","","13h - 14h", "14h - 15h",
                "15h - 16h", "16h - 17h", "17h - 18h", "18h - 19h", "19h - 20h",
                "20h - 21h", "21h - 22h", "22h - 23h"};
        Bundle bundle = this.getArguments();
        int match_id = bundle.getInt("match_id",1);
        Log.i("MATCH ID", "MATCH ID: "+match_id);
        final MATCHES match = db_match.getMatch(match_id);

        txtAddress = (EditText) v.findViewById(R.id.txtInforMatchAddress);
            txtAddress.setFocusable(false);
        txtAddress.setText(db_branch.getBranch(db_fg.getFootball_Ground(match.getId_fg()).getId_branch()).getAddress());
        txtBranchName = (EditText) v.findViewById(R.id.txtInforMatchBranchName);
        txtBranchName.setFocusable(false);
        txtBranchName.setText(db_branch.getBranch(db_fg.getFootball_Ground(match.getId_fg()).getId_branch()).getName_branch());
        txtFGName = (EditText) v.findViewById(R.id.txtInforMatchFGName);
        txtFGName.setFocusable(false);
        txtFGName.setText(db_fg.getFootball_Ground(match.getId_fg()).getFg_name());
        txtPrice = (EditText) v.findViewById(R.id.txtInforMatchPrice);
        txtPrice.setFocusable(false);
        txtPrice.setText(String.valueOf(match.getPrice()));

        txtTime = (EditText) v.findViewById(R.id.txtInforMatchTime);
            txtTime.setFocusable(false);

        final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        final SimpleDateFormat formatYMD = new SimpleDateFormat("yyyy-MM-dd");

        String start_time =format.format(match.getStart_time()).split(" ")[1].substring(0, 2);
        for(int i=0; i<time_label.length; i++){
            if(i!=3 ) {
                if (time_label[i].substring(0, 2).equals(start_time)) {
                    Log.i("START TIME ", "START TIME: "+start_time);
                    txtTime.setText(time_label[i]);
                }
            }
        }

        txtDate = (EditText) v.findViewById(R.id.txtInforMatchDate);
            txtDate.setText(formatYMD.format(match.getStart_time()).split(" ")[0]);
        txtDate.setFocusable(false);

        btnCancelInforMatch = (Button) v.findViewById(R.id.btnCancelInforMatch);
            btnCancelInforMatch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getFragmentManager().popBackStack();
                }
            });

        return v;
    }
}

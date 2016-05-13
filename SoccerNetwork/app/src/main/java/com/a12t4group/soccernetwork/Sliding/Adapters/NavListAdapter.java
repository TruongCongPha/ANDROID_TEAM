package com.a12t4group.soccernetwork.Sliding.Adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.a12t4group.soccernetwork.R;
import com.a12t4group.soccernetwork.Sliding.Models.NavItem;

import java.util.List;

/**
 * Created by User on 23/04/2016.
 */
public class NavListAdapter extends ArrayAdapter<NavItem> {
    Context context;
    int resLayout;
    List<NavItem> listNavItems;
    public NavListAdapter(Context context, int resLayout, List<NavItem> listNavItems) {
        super(context, resLayout, listNavItems);
        this.context = context;
        this.resLayout = resLayout;
        this.listNavItems = listNavItems;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = View.inflate(context, resLayout, null);
        TextView txtTitle = (TextView) view.findViewById(R.id.title);
        TextView txtSubTitle = (TextView) view.findViewById(R.id.subTitle);
        ImageView navIcon = (ImageView) view.findViewById(R.id.nav_icon);

        NavItem navItem = listNavItems.get(position);
        txtTitle.setText(navItem.getTitle());
        txtSubTitle.setText(navItem.getSubTitle());
        navIcon.setImageResource(navItem.getResIcon());

        return  view;
    }
}

package com.a12t4group.soccernetwork.Sliding;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.a12t4group.soccernetwork.BEAN.USER;
import com.a12t4group.soccernetwork.DB.Access_Matches_DB;
import com.a12t4group.soccernetwork.DB.MyDatabaseHelper;
import com.a12t4group.soccernetwork.R;
import com.a12t4group.soccernetwork.Sliding.Adapters.NavListAdapter;
import com.a12t4group.soccernetwork.Sliding.Fragments.Home;
import com.a12t4group.soccernetwork.Sliding.Fragments.Matches_profile;
import com.a12t4group.soccernetwork.Sliding.Fragments.Setting;
import com.a12t4group.soccernetwork.Sliding.Fragments.User_Profile;
import com.a12t4group.soccernetwork.Sliding.Models.NavItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 23/04/2016.
 */
public class Sliding extends AppCompatActivity {
    DrawerLayout drawerLayout;
    RelativeLayout drawerPane;
    ListView lvNav;
    Cursor cursor;
    List<NavItem> listNavItems;
    List<Fragment> listFragments;
    ActionBarDrawerToggle actionBarDrawerToggle;
    public static USER user;
    Access_Matches_DB db_match;
    Boolean isCancelNotif = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sliding_layout);

        db_match = new Access_Matches_DB(getApplicationContext());


        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerPane = (RelativeLayout) findViewById(R.id.drawer_pane);
        lvNav = (ListView) findViewById(R.id.nav_listView);

        listNavItems = new ArrayList<NavItem>();
        listNavItems.add(new NavItem("Home", "Home page", R.drawable.home));
        listNavItems.add(new NavItem("Profile", "Profile Page", R.drawable.people_128));
        listNavItems.add(new NavItem("Matches Profile", "", R.drawable.football_3_128));
        listNavItems.add(new NavItem("Setting", "", R.drawable.setting));

        NavListAdapter navListAdapter = new NavListAdapter(getApplicationContext(), R.layout.item_nav_list, listNavItems);

        lvNav.setAdapter(navListAdapter);


        listFragments = new ArrayList<Fragment>();
        listFragments.add(new Home());
        listFragments.add(new User_Profile());
        listFragments.add(new Matches_profile());
        listFragments.add(new Setting());




        Intent intent = getIntent();
        //chuyển fragment Matches_profile nếu click notification
        if(intent.getStringExtra("fragment")!=null){
            isCancelNotif=true;
            FragmentManager fragmentMatchesProfile = getSupportFragmentManager();
            fragmentMatchesProfile.beginTransaction().replace(R.id.main_content,new Matches_profile()).commit();
            Log.i("NEW FRAGMENT", "NEW FRAGMENT MATCHES PROFILE");

            setTitle(listNavItems.get(2).getTitle());
            lvNav.setItemChecked(2, true);
            drawerLayout.closeDrawer(drawerPane);
        }
            String username  = intent.getStringExtra("username");

        MyDatabaseHelper db_User = new MyDatabaseHelper(getApplicationContext());
            user = db_User.getUser(username);




        //Log.i("USER ID", "USER ID: "+user.getUser_id());

        cursor = db_match.getListMatchesCorrespondWithUser(user.getUser_id());
        Log.i("ROWS", "SO LUONG TRAN DAU: "+cursor.getCount());
        if(cursor.getCount()!=0 && isCancelNotif==false){
            showNotification();
        }

        //đặt thông báo nếu có trận đấu diễn ra trong ngày


            TextView tvUsername = (TextView) findViewById(R.id.username);
                tvUsername.setText(user.getUsername());
            TextView tvEmail = (TextView) findViewById(R.id.email);
                tvEmail.setText( user.getEmail());
            TextView tvCity = (TextView) findViewById(R.id.city);
                tvCity.setText(db_User.getAdress(user.getDistrict_id()));



        getSupportActionBar().setDisplayHomeAsUpEnabled(true);




        if(isCancelNotif==false) {
            //load first Fragment
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.main_content, listFragments.get(0)).addToBackStack("Home").commit();
            setTitle(listNavItems.get(0).getTitle());
            lvNav.setItemChecked(0, true);
            drawerLayout.closeDrawer(drawerPane);
        }
        // set listener for navigation items
        lvNav.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //replace the fragment with the selection correspondingly
                if(listNavItems.get(position).getTitle().equals("Home")){
                    Log.i("RETURN", "SET FRAGMENT HOME");

                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.main_content, listFragments.get(position)).addToBackStack("Home").commit();
                }else { if(listNavItems.get(position).getTitle().equals("Profile")) {
                         Log.i("RETURN", "DON'T SET FRAGMENT PROFILE");
                        }
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.main_content, listFragments.get(position)).commit();
                }
                setTitle(listNavItems.get(position).getTitle());
                lvNav.setItemChecked(position, true);
                drawerLayout.closeDrawer(drawerPane);
            }
        });

        //create listener for drawerLayout
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_opened, R.string.drawer_closed) {
            @Override
            public void onDrawerOpened(View drawerView) {
                invalidateOptionsMenu();
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                invalidateOptionsMenu();
                super.onDrawerClosed(drawerView);
            }
        };
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

    }

   /* //quay trở lại các Fragments khi chạm nút BACK
    @Override
    public void onBackPressed() {
        if(getFragmentManager().getBackStackEntryCount() == 0) {
            super.onBackPressed();
        }
        else {
            getFragmentManager().popBackStack();
        }
    }
*/

    @Override
    public void onPostCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item))
            return true;
        return super.onOptionsItemSelected(item);
    }


    public void showNotification(){

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = format.format(System.currentTimeMillis());
        Log.i("CURRENT DATE", "CURRENT DATE: "+currentDate);
        for(int i=0; i<cursor.getCount(); i++){
            if(cursor.getString(4).toString().split(" ")[0].equals(currentDate)){
                NotificationCompat.Builder mBuilder =
                        new NotificationCompat.Builder(getApplicationContext())
                                .setSmallIcon(R.drawable.ball_64)
                                .setContentTitle("Bạn có trận đấu ngày hôm nay")
                                .setContentText("Bấm vào để xem chi tiết !!!!")
                                .setAutoCancel(true);
                mBuilder.setVisibility(NotificationCompat.VISIBILITY_PUBLIC);
// Creates an explicit intent for an Activity in your app
                Intent resultIntent = new Intent(this, Sliding.class);
                    resultIntent.putExtra("fragment", "Matches_profile");
                    resultIntent.putExtra("username",user.getUsername());

// The stack builder object will contain an artificial back stack for the
// started Activity.
// This ensures that navigating backward from the Activity leads out of
// your application to the Home screen.
                    /*TaskStackBuilder stackBuilder = TaskStackBuilder.create(getApplicationContext());
// Adds the back stack for the Intent (but not the Intent itself)
                    stackBuilder.addParentStack(ResultActivity.class);
// Adds the Intent that starts the Activity to the top of the stack
                    stackBuilder.addNextIntent(resultIntent);*/
                PendingIntent pendingIntent =
                        PendingIntent.getActivity(
                                getApplicationContext(),0, resultIntent,0);
                mBuilder.setContentIntent(pendingIntent);
                NotificationManager mNotificationManager =
                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
// mId allows you to update the notification later on.
                mNotificationManager.notify(1, mBuilder.build());
                break;
            }
        }
    }

}

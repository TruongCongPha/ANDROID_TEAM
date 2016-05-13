package com.a12t4group.soccernetwork;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by User on 27/04/2016.
 */
public class DateTime extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.datetime_layout);
        EditText time = (EditText) findViewById(R.id.txtTime);
        SimpleDateFormat format1 = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
        SimpleDateFormat format2 = new SimpleDateFormat("hh:mm:ss dd-MM-yyyy");
        String s = "17:05:21 14/04/2016";
        try {
            Date date = format1.parse(s);
            System.out.println("DATE: ====>>>> "+date);
            time.setText(format2.format(date));
        }catch (Exception e){}
    }
}

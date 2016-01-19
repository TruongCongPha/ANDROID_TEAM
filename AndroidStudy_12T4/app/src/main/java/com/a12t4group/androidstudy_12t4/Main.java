package com.a12t4group.androidstudy_12t4;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class Main extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new MyView(this));

    }


}

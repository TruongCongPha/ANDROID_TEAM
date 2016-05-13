package com.a12t4group.soccernetwork;

import android.util.Log;
import android.util.Patterns;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by User on 25/04/2016.
 */
public class Libraries {
    public static boolean isValidate(String s){
        for(int i=0;i<s.length();i++){
            if(s.charAt(i) <'0' || (s.charAt(i)>'9' && s.charAt(i)<'A') || (s.charAt(i)>'Z' && s.charAt(i)<'a')
                    || s.charAt(i)>'z') return false;
        }
        return true;
    }
    public  static boolean isValidatePhoneNumber(String pn){
        if (pn.length()>11 || pn.length() <10) return  false;
        return  true;
    }

    public static boolean isValidateEmail(String email){
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static Date convertStringToDate(String dateS){
        Log.i("DATE","DATE : "+dateS);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date =null;
       try{
           date = format.parse(dateS);
           Log.i("FORMAT DATE","DATE FORMAT: "+date.toString());
       } catch (Exception e){}
        return date;
    }

    public static String getCurrentDateString(){
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }

}

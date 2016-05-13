package com.a12t4group.soccernetwork;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by User on 10/05/2016.
 */
public class PushNotificationDemo extends Activity {
    EditText txtNameOfNotification, txtSubject, txtContent;
    Button btnPushNotification;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pushnotification_demo);

        txtNameOfNotification = (EditText) findViewById(R.id.txtNameOfNotification);
        txtContent = (EditText) findViewById(R.id.txtContent);
        txtSubject = (EditText) findViewById(R.id.txtSubject);

        btnPushNotification = (Button) findViewById(R.id.btnpushnotification);
            btnPushNotification.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String name = txtNameOfNotification.getText().toString().trim();
                    String subject = txtSubject.getText().toString().trim();
                    String content = txtContent.getText().toString().trim();
                    /*NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    Notification notification = new Notification(R.drawable.football_3, name, System.currentTimeMillis());
                    PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, new Intent(), 0);

                    notification.
                    notification.setLatestEventInfo(getApplicationContext(),subject,content,pendingIntent);
                    notificationManager.notify(1, notification);*/
                    NotificationCompat.Builder mBuilder =
                            new NotificationCompat.Builder(getApplicationContext())
                                    .setSmallIcon(R.drawable.iconfootball)
                                    .setContentTitle(subject)
                                    .setContentText(content);
                    mBuilder.setVisibility(NotificationCompat.VISIBILITY_PUBLIC);
// Creates an explicit intent for an Activity in your app
                    Intent resultIntent = new Intent();

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
                }
            });

    }

}

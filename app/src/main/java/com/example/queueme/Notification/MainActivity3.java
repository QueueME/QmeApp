package com.example.queueme.Notification;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.Button;

import com.example.queueme.R;

public class MainActivity3 extends AppCompatActivity {

    NotificationCompat.Builder notification;
    private static final int uniqueID = 45612;
    private Button button2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        button2=(Button) findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //bygg notifikasjonen
                notification.setSmallIcon(R.drawable.queueme);
                notification.setTicker("Dette er ticker");
                notification.setWhen(System.currentTimeMillis());
                notification.setContentTitle("Dette er tittel");
                notification.setContentText("Dette er bodytext av notification");

                Uri alarmSound = RingtoneManager.getActualDefaultRingtoneUri(MainActivity3.this, RingtoneManager.TYPE_NOTIFICATION);
                notification.setSound(alarmSound);

                Intent intent = new Intent(MainActivity3.this, MainActivity3.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity3.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                notification.setContentIntent(pendingIntent);

                // bygg notifikasjon og send det ut til enhet
                NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                nm.notify(uniqueID, notification.build());

            }
        });


        notification = new NotificationCompat.Builder(this);
        notification.setAutoCancel(true);
    }

    public void studassNextClicked(View view){

        //bygg notifikasjonen
        notification.setSmallIcon(R.drawable.queueme);
        notification.setTicker("Dette er ticker");
        notification.setWhen(System.currentTimeMillis());
        notification.setContentTitle("Dette er tittel");
        notification.setContentText("Dette er bodytext av notification");

        Uri alarmSound = RingtoneManager.getActualDefaultRingtoneUri(this, RingtoneManager.TYPE_NOTIFICATION);
        notification.setSound(alarmSound);

        Intent intent = new Intent(this, MainActivity3.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        notification.setContentIntent(pendingIntent);

        // bygg notifikasjon og send det ut til enhet
        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        nm.notify(uniqueID, notification.build());

    }
}

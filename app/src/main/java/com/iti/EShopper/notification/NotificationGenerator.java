package com.iti.EShopper.notification;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.iti.EShopper.Activity.MainActivity;
import com.iti.EShopper.R;


public class NotificationGenerator {

    private static final int NOTIFICATION_ID_OPEN_ACTIVITY = 8;
    private static final int NOTIFICATION_ID_CUSTOM_BIG = 9;
    private static final int NOTIFICATION_ID_OREO_CUSTOM_BIG = 9;
    private static final String CHANNEL_ID = "com.example.android.tfakor";
    private static final String CHANNEL_NAME = "tfakor";

    public static NotificationManager nm;


    public static void openActivityNotification(Context context){

        NotificationCompat.Builder nc = new NotificationCompat.Builder(context);
        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Intent notifyIntent = new Intent(context, MainActivity.class);

        notifyIntent.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        nc.setContentIntent(pendingIntent);
        nc.setSmallIcon( R.drawable.notification);
        nc.setAutoCancel(true);
        nc.setContentTitle("رسالة جديدة");
        nc.setContentText("");
        nc.setSound(alarmSound);
        nc.setLights(Color.BLUE, 1, 1);


        nm.notify(NOTIFICATION_ID_OPEN_ACTIVITY, nc.build());

    }

    @TargetApi(Build.VERSION_CODES.O)
    public static void oreocustomNotification(Context context){

        Intent notifyIntent = new Intent(context, MainActivity.class);
        notifyIntent.setAction("android.intent.action.MAIN");
        notifyIntent.addCategory("android.intent.category.LAUNCHER");
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        Uri defaultSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        OreoNotification oreoNotification = new OreoNotification(context);
        Notification.Builder builder = oreoNotification.getOreoNotification("رسالة جديدة", "", pendingIntent, defaultSound ,"");

        oreoNotification.getManager().notify(NOTIFICATION_ID_CUSTOM_BIG, builder.build());
    }
}



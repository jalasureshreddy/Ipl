package com.krazynutz.iplt20info.services;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.krazynutz.iplt20info.R;
import com.krazynutz.iplt20info.activites.DashBoard;

public class MyFirebaseMessagingService extends FirebaseMessagingService
{
    private static final String TAG = "MyFirebaseMsgService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage)
    {

        Log.d(TAG, "From: " + remoteMessage.getFrom());

        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getSound());
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getTitle());
            sendNotification(remoteMessage.getNotification().getBody());
        }

    }

    public void sendNotification(String messageBody) {
        Intent intent = new Intent(this, DashBoard.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);


       // Uri sound = Uri.parse("android.resource://" + getPackageName() + "/"+ R.raw.ipl_ring);
        Uri sound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE
                + "://" + getPackageName() +"/"+R.raw.ipl_ring);
       // Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setLargeIcon(BitmapFactory.decodeResource(getApplicationContext().getResources(),R.drawable.logo_48))
                .setSmallIcon(R.drawable.logo_36)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(messageBody))
                .setContentTitle("IPL Updates")
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(sound)
                .setContentIntent(pendingIntent);


        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

       /* Notification notification = notificationBuilder.build();
        notification.sound = Uri.parse("android.resource://" + getPackageName() + "/" +R.raw.ipl_ring);
        notification.defaults |= Notification.DEFAULT_VIBRATE;*/

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
}

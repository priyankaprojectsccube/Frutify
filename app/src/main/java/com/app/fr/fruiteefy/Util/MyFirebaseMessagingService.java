package com.app.fr.fruiteefy.Util;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.fr.fruiteefy.Chat.MessageActivity;
import com.app.fr.fruiteefy.R;
import com.app.fr.fruiteefy.user_antigaspi.UserAntigaspiHomeActivity;
import com.app.fr.fruiteefy.user_client.home.OrderDetailActivity;
import com.app.fr.fruiteefy.user_picorear.OrderpicoDetailActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.HashMap;
import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        Log.d("dsfdffdsd", String.valueOf(remoteMessage.getData()));


        if(remoteMessage.getData().containsKey("user_type")){
           // if(remoteMessage.getData().get("sented").equals("usernoti")){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    senduserOreoNotification(remoteMessage);

                } else {
                    senduserNotification(remoteMessage);
                }
          //  }
        }

        if(remoteMessage.getData().containsKey("sented")){
            if(remoteMessage.getData().get("sented").equals("chatnoti")){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                    sendOreoNotification(remoteMessage);
                } else {
                    sendNotification(remoteMessage);
                }
            }
        }}

    private void sendOreoNotification(RemoteMessage remoteMessage){
        String userid = remoteMessage.getData().get("user");
        String icon = remoteMessage.getData().get("icon");
        String title = remoteMessage.getData().get("title");
        String body = remoteMessage.getData().get("body");

        String token = remoteMessage.getData().get("token");
        String username = remoteMessage.getData().get("name");


        RemoteMessage.Notification notification = remoteMessage.getNotification();
        int j = Integer.parseInt(userid.replaceAll("[\\D]", ""));

        Intent intent = new Intent(this, MessageActivity.class);

        intent.putExtra("receiverid",userid);
        intent.putExtra("name",username);
        intent.putExtra("token",token);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, j, intent, PendingIntent.FLAG_ONE_SHOT);
        Uri defaultSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        OreoNotification oreoNotification = new OreoNotification(this);
        Notification.Builder builder = oreoNotification.getOreoNotification(title, body, pendingIntent,
                defaultSound, icon);

        int i = 0;
        if (j > 0){
            i = j;
        }

        oreoNotification.getManager().notify(i, builder.build());

    }







    private void sendNotification(RemoteMessage remoteMessage) {

        String userid = remoteMessage.getData().get("user");
        String icon = remoteMessage.getData().get("icon");
        String title = remoteMessage.getData().get("title");
        String body = remoteMessage.getData().get("body");
        String token = remoteMessage.getData().get("token");
        String username = remoteMessage.getData().get("name");

        RemoteMessage.Notification notification = remoteMessage.getNotification();
        int j = Integer.parseInt(userid);
        Intent intent = new Intent(this, MessageActivity.class);

        intent.putExtra("receiverid",userid);
        intent.putExtra("name",username);
        intent.putExtra("token",token);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, j, intent, PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(Integer.parseInt(icon))
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setSound(defaultSound)
                .setContentIntent(pendingIntent);
        NotificationManager noti = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

        int i = 0;
        if (j > 0){
            i = j;
        }

        noti.notify(i, builder.build());
    }



    private void senduserOreoNotification(RemoteMessage remoteMessage){
        String userid = remoteMessage.getData().get("noti_id");
        String title = "Fruiteefy";
        String body = remoteMessage.getData().get("notification");
        String notiurl = remoteMessage.getData().get("order_id");
        String usertype=remoteMessage.getData().get("user_type");



        RemoteMessage.Notification notification = remoteMessage.getNotification();
        int j = Integer.parseInt(userid.replaceAll("[\\D]", ""));

        Intent intent = null;

        if(usertype.equals("1")) {




            intent=new Intent(this, OrderDetailActivity.class);
                 intent.putExtra("order_id",notiurl);

        }
        else if(usertype.equals("2")){
            intent=new Intent(this, UserAntigaspiHomeActivity.class);
        }
        else if(usertype.equals("3")){
             intent=new Intent(this, OrderpicoDetailActivity.class);
            intent.putExtra("order_id",notiurl);
        }



        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, j, intent, PendingIntent.FLAG_ONE_SHOT);
        Uri defaultSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        OreoNotification oreoNotification = new OreoNotification(this);
        Notification.Builder builder = oreoNotification.getOreoNotification(title, body, pendingIntent,
                defaultSound,"https://fruiteefy.fr/assets_antigaspi/img/core-img/icon.png");

        int i = 0;
        if (j > 0){
            i = j;
        }

        oreoNotification.getManager().notify(i, builder.build());

    }


    private void senduserNotification(RemoteMessage remoteMessage) {

        String userid = remoteMessage.getData().get("noti_id");
        String title = "Fruiteefy";
        String body = remoteMessage.getData().get("notification");
        String notiurl = remoteMessage.getData().get("order_id");
        String usertype=remoteMessage.getData().get("user_type");


        RemoteMessage.Notification notification = remoteMessage.getNotification();
        int j = Integer.parseInt(userid);
        Intent intent = null;

        if(usertype.equals("1")) {




            intent=new Intent(this, OrderDetailActivity.class);
            intent.putExtra("order_id",notiurl);

        }
        else if(usertype.equals("2")){
            intent=new Intent(this, UserAntigaspiHomeActivity.class);
        }
        else if(usertype.equals("3")){
            intent=new Intent(this, OrderpicoDetailActivity.class);
            intent.putExtra("order_id",notiurl);
        }

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, j, intent, PendingIntent.FLAG_ONE_SHOT);

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("change_notifi_status"), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> param = new HashMap<>();
                param.put("noti_id", userid);
                param.put("read_status", "1");
                return param;
            }
        };
        requestQueue.add(stringRequest);

        Uri defaultSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.logo)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setSound(defaultSound)
                .setContentIntent(pendingIntent);
        NotificationManager noti = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

        int i = 0;
        if (j > 0){
            i = j;
        }

        noti.notify(i, builder.build());
    }
}

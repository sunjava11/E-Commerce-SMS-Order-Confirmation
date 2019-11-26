package com.example.arslan.androidsmsgatewaybyarslan.Service;

import android.app.Activity;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v7.app.NotificationCompat;
import android.util.Log;


import com.example.arslan.androidsmsgatewaybyarslan.ClientRcvSms;
import com.example.arslan.androidsmsgatewaybyarslan.ConfirmationMessage;
import com.example.arslan.androidsmsgatewaybyarslan.ErrorLog;
import com.example.arslan.androidsmsgatewaybyarslan.GlobalVars;
import com.example.arslan.androidsmsgatewaybyarslan.MainActivity;
import com.example.arslan.androidsmsgatewaybyarslan.R;
import com.orm.util.NamingHelper;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Arslan on 04-Aug-16.
 */
public class ConfirmationMessageService extends Service {

    private static final String TagName = "ConfirmationService";
ErrorLog errorLog = ErrorLog.getInstance();

    @Override
    public void onCreate() {
        Log.i(TagName, "Service OnCreate");

        Intent notificationIntent = new Intent(this, MainActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                notificationIntent, 0);

        Notification notification = new NotificationCompat.Builder(this)
                .setSmallIcon(getNotificationIcon())
                .setContentTitle("A SMS Gateway")
                .setContentText("Service is running")
                .setContentIntent(pendingIntent).build();

        startForeground(1337, notification);
    }

    @Override
    public IBinder onBind(Intent arg0)
    {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        final ConfirmationMessage confirmationMessage = new ConfirmationMessage();



        //region Get Messages From Server & Send SMS Thread

            new Thread(new Runnable() {
                @Override
                public void run() {
                    Log.i(TagName, "Get Message From Server executed");

                        confirmationMessage.GetMessageFromServer();
                }

            }).start();
            //endregion


        //region Send Message on Server Thread
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.i(TagName,"Send Message To Server executed");

                try {
                    List<ClientRcvSms> NotSentonServerSms = ClientRcvSms.find(ClientRcvSms.class, NamingHelper.toSQLNameDefault("IsSentToServer") + "=?", "0");

                    if(NotSentonServerSms.size()>0) {
                        Log.i(TagName,"Sms found to send on server"+NotSentonServerSms.size());
                        confirmationMessage.SendMessageOnServer(NotSentonServerSms);
                    }
                } catch (Exception e) {
                    Log.i(TagName,"Exception Occured",e);
                    errorLog.LogException(e);
                }

            }

        }).start();
        //endregion



        return START_STICKY;
    }

    @Override
    public void onDestroy()
    {
        Log.i(TagName,"Service Destroyed");
    }

    private int getNotificationIcon() {
        boolean useWhiteIcon = (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP);
        return useWhiteIcon ? R.mipmap.ic_launcher : R.mipmap.ic_launcher;
    }
}

package com.example.arslan.androidsmsgatewaybyarslan.BroadCastReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.util.Log;

import com.example.arslan.androidsmsgatewaybyarslan.Service.ConfirmationMessageService;

/**
 * Created by Arslan on 05-Aug-16.
 */
public class AlarmManagerBroadCastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent)
    {
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,"");
        wl.acquire();
        Log.i("AlarmBroadCastReceiver","Alarm Manager Broadcast Reciver Fired");
        context.startService(new Intent(context, ConfirmationMessageService.class));

        wl.release();
    }
}

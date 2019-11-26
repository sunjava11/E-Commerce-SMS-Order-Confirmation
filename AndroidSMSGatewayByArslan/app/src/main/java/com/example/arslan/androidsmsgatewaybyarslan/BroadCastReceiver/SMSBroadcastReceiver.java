package com.example.arslan.androidsmsgatewaybyarslan.BroadCastReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import com.example.arslan.androidsmsgatewaybyarslan.ClientRcvSms;
import com.example.arslan.androidsmsgatewaybyarslan.ConfirmationMessage;
import com.example.arslan.androidsmsgatewaybyarslan.ErrorLog;

/**
 * Created by Arslan on 31-Jul-16.
 */
public class SMSBroadcastReceiver extends BroadcastReceiver {
    private static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
    private static final String TAG = "SMSBroadcastReceiver";
    ErrorLog errorLog = ErrorLog.getInstance();

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            if (intent.getAction() == SMS_RECEIVED) {
                Bundle bundle = intent.getExtras();
                if (bundle != null) {
                    Object[] pdus = (Object[]) bundle.get("pdus");
                    final SmsMessage[] messages = new SmsMessage[pdus.length];
                    for (int i = 0; i < pdus.length; i++) {
                        messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                    }
                    if (messages.length > -1) {
                        if (messages[0].getMessageBody().length() < 150) {

                                ClientRcvSms crs = new ClientRcvSms(messages[0].getOriginatingAddress(), messages[0].getMessageBody(), 0);

                                ConfirmationMessage confirmationMessage = new ConfirmationMessage();
                                confirmationMessage.SendMessageOnServer(crs);
                        }
                    }
                }
            }
        } catch (Exception ex) {
            Log.i(TAG, "Message Saving Failed", ex);
            errorLog.LogException(ex);
        }
    }
}

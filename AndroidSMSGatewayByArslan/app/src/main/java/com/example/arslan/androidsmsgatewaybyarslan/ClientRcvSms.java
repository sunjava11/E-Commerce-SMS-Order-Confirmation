package com.example.arslan.androidsmsgatewaybyarslan;

import android.view.LayoutInflater;

import com.orm.SugarRecord;

/**
 * Created by Arslan on 30-Jul-16.
 */
public class ClientRcvSms extends SugarRecord{
    String FromNumber;
    String Message;
    int IsSentToServer;

    public ClientRcvSms()
    {

    }

    public ClientRcvSms(String fromNumber, String message, int isSentToServer)
    {
        this.FromNumber= fromNumber;
        this.Message= message;
        this.IsSentToServer= isSentToServer;
    }

}

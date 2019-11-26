package com.example.arslan.androidsmsgatewaybyarslan;

import com.orm.SugarRecord;

/**
 * Created by Arslan on 30-Jul-16.
 */
public class ServerRcvSms extends SugarRecord {
    String ToNumber;
    String Message;
    int IsSmsSent;

    public ServerRcvSms()
    {

    }
    public ServerRcvSms(String toNumber, String message, int isSmsSent)
    {
        this.ToNumber=toNumber;
        this.Message=message;
        this.IsSmsSent=isSmsSent;
    }

}

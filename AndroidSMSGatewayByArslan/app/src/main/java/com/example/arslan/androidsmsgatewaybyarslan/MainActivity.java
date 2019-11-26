package com.example.arslan.androidsmsgatewaybyarslan;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arslan.androidsmsgatewaybyarslan.BroadCastReceiver.AlarmManagerBroadCastReceiver;
import com.example.arslan.androidsmsgatewaybyarslan.Model.ServerRcvSmsModel;
import com.example.arslan.androidsmsgatewaybyarslan.RestApi.ApiClient;
import com.example.arslan.androidsmsgatewaybyarslan.RestApi.IServerRcvAPI;
import com.example.arslan.androidsmsgatewaybyarslan.Service.ConfirmationMessageService;
import com.orm.util.NamingHelper;

import java.io.File;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ErrorLog errorLog = ErrorLog.getInstance();
        errorLog.setContext(getApplicationContext());

        Intent alarmIntent = new Intent(this, AlarmManagerBroadCastReceiver.class);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,System.currentTimeMillis(),60000, PendingIntent.getBroadcast(this,1,alarmIntent,PendingIntent.FLAG_UPDATE_CURRENT));
        Log.i("MainActivity","Alarm Set for 1 minutes");

        startService(new Intent(this, ConfirmationMessageService.class));


        TextView tv = (TextView) findViewById(R.id.textView);
        tv.setText("Service Running");

//        Intent intent = new Intent(this, ConfirmationMessageService.class);
//        startService(intent);
        /*if(Build.VERSION.SDK_INT>9)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        */
    }

    public void RestartService (View arg)

    {
        startService(new Intent(this, ConfirmationMessageService.class));
        TextView tv = (TextView) findViewById(R.id.textView);
        tv.setText("Service Restarted");

//        ConfirmationMessage confirmationMessage = new ConfirmationMessage();
//        final Button  btn = (Button) findViewById(R.id.buttonSaveMessage);
//        btn.setEnabled(false);
//        confirmationMessage.GetMessageFromServer();
//        btn.setEnabled(true);
      //  ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.SEND_SMS},1);
//        try {
//
//            IServerRcvAPI apiService = ApiClient.GetClient().create(IServerRcvAPI.class);
//            Call<List<ServerRcvSmsModel>> apiCall = apiService.GetServerMessages();
//            final Button  btn = (Button) findViewById(R.id.buttonSaveMessage);
//            btn.setEnabled(false);
//
//            apiCall.enqueue(new Callback<List<ServerRcvSmsModel>>() {
//                @Override
//                public void onResponse(Call<List<ServerRcvSmsModel>> call,Response<List<ServerRcvSmsModel>> response) {
//
//                    List<ServerRcvSmsModel> responseSmsList = response.body();
//                    TextView tv =(TextView) findViewById(R.id.textView);
//
//                    btn.setEnabled(true);
//                    Toast.makeText(MainActivity.this, "SMS Fetched:"+responseSmsList.size(), Toast.LENGTH_SHORT).show();
//                    int savedSmsCounter = 1;
//
//                    for (ServerRcvSmsModel rcvSms: responseSmsList)
//                    {
//                        ServerRcvSms serverSms = new ServerRcvSms(rcvSms.to,rcvSms.message,0);
//                        serverSms.save();
//                        tv.setText("SMS Saved:"+savedSmsCounter);
//                        savedSmsCounter++;
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<List<ServerRcvSmsModel>> call, Throwable t) {
//                    Log.d("CallingService","Exception Occured",t);
//                    Toast.makeText(MainActivity.this, "Exception Occured", Toast.LENGTH_SHORT).show();
//                    btn.setEnabled(true);
//                }
//            });
//
//
//
//
//        }
//        catch (Exception ex)
//        {
//            Log.d("saveBtn","Error saving record",ex);
//        }
    }
    public void SendMessageListOnServer (View arg)
    {
//        try {
//
//            final List<ClientRcvSms> RcvdSmsforServerList = ClientRcvSms.find(ClientRcvSms.class, NamingHelper.toSQLNameDefault("IsSentToServer")+"=?","0");
//            IServerRcvAPI apiService = ApiClient.GetClient().create(IServerRcvAPI.class);
//            Call<Boolean> apiCall = apiService.SendClientMessagesOnServer(RcvdSmsforServerList);
//            final Button  btn = (Button) findViewById(R.id.SendOnServerbutton);
//            btn.setEnabled(false);
//
//            apiCall.enqueue(new Callback<Boolean>() {
//                @Override
//                public void onResponse(Call<Boolean> call,Response<Boolean> response) {
//
//                    Log.i("UpdatingSMS","Total msg list:"+RcvdSmsforServerList.size());
//
//                    if(response.body()==true)
//                    {
//                        for (ClientRcvSms rcvSms: RcvdSmsforServerList)
//                        {
//                            ClientRcvSms clientSms = ClientRcvSms.findById(ClientRcvSms.class,rcvSms.getId());
//                            clientSms.IsSentToServer=1;
//                            clientSms.save();
//                            Log.i("UpdatingSMS",rcvSms.getId()+ " Updated");
//                        }
//                        Toast.makeText(MainActivity.this, "Messages Sent on Server", Toast.LENGTH_SHORT).show();
//                    }
//                    else{
//                        Toast.makeText(MainActivity.this, "Response Body is False", Toast.LENGTH_SHORT).show();
//                    }
//                    btn.setEnabled(true);
//                }
//
//                @Override
//                public void onFailure(Call<Boolean> call, Throwable t) {
//                    Log.i("UpdatingSMS","Exception Occured",t);
//                    btn.setEnabled(true);
//                }
//            });
//
//
//        }
//        catch (Exception ex)
//        {
//            Log.d("SendtoServer","Error saving record",ex);
//        }
    }

    public void SendEmail(View view)
    {
        String filename= GlobalVars.GlobalSession.LOGS_FILE_NAME;
        File filelocation = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/logFiles/", filename);


        if(filelocation.exists())
        {
            Uri path = Uri.fromFile(filelocation);

            Intent emailIntent = new Intent(Intent.ACTION_SEND);
            emailIntent .setType("vnd.android.cursor.dir/email");
            String to[] = {"sunjava11@gmail.com"};
            emailIntent .putExtra(Intent.EXTRA_EMAIL, to);

            emailIntent .putExtra(Intent.EXTRA_STREAM, path);
            emailIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            emailIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

            emailIntent .putExtra(Intent.EXTRA_SUBJECT, "SMS Gateway Logs");
            startActivity(Intent.createChooser(emailIntent , "Send email..."));
        }
        else{
            Toast.makeText(this, "No File Found", Toast.LENGTH_SHORT).show();
        }

    }
}

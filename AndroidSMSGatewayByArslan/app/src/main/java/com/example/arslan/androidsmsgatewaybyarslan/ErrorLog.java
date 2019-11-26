package com.example.arslan.androidsmsgatewaybyarslan;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.util.Date;

/**
 * Created by Arslan on 27-Jan-17.
 */

public class ErrorLog {



    Context context=null;
    private static ErrorLog errorLog= new ErrorLog();


    private ErrorLog()
    {

    }

    public static ErrorLog getInstance()
    {

        return errorLog;
    }



    public void setContext(Context cntx)
    {
        this.context=cntx;
    }


    public void LogException(Object ex)
    {
        Gson json = new Gson();
        String jsonString = "";
        try {
            jsonString = json.toJson(ex);
String filePath = Environment.getExternalStorageDirectory().getAbsolutePath();


            if(isExternalStorageWritable())
            {
                File filelocation = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "/logFiles/");


                if(filelocation.exists()==false)
                {
                    filelocation.mkdirs();
                }


                File filelocation1 = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "/logFiles/"+ GlobalVars.GlobalSession.LOGS_FILE_NAME);
                FileOutputStream fileOutputStream = new FileOutputStream(filelocation1,true);
                PrintWriter pw = new PrintWriter(fileOutputStream);
                pw.println(DateFormat.getDateTimeInstance().format(new Date()));
                pw.print(jsonString);
                pw.println("_______________________");
                pw.println("_______________________");
                pw.println("_______________________");
                pw.flush();
                pw.close();
                fileOutputStream.close();
            }

        } catch (Exception e) {

            Log.i("asd","ss",e);
        }
    }


    /* Checks if external storage is available for read and write */
    private boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

}

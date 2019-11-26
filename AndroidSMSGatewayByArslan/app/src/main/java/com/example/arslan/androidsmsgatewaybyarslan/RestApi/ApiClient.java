package com.example.arslan.androidsmsgatewaybyarslan.RestApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Arslan on 30-Jul-16.
 */
public class ApiClient {
    public static final String BASE_URL = "http://bazaarkey.com/AndroidSMS/";
   // public static final String BASE_URL_Abbazzar = "http://abbazaar.net/AndroidSMS/";

    private static Retrofit rft =null;
    private static Retrofit rft_mamji =null;
    private static Retrofit rft_saim =null;
    private static Retrofit rft_budgetbazaar=null;
    private static Retrofit rft_fabi =null;

    public static Retrofit GetClient(String siteName)
    {
        if(rft==null)
        {
            rft = new Retrofit.Builder().baseUrl(siteName).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return  rft;
    }


    public static Retrofit GetClient_Mamji(String siteName)
    {
        if(rft_mamji==null)
        {
            rft_mamji = new Retrofit.Builder().baseUrl(siteName).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return  rft_mamji;
    }

    public static Retrofit GetClient_Saim(String siteName)
    {
        if(rft_saim==null)
        {
            rft_saim= new Retrofit.Builder().baseUrl(siteName).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return  rft_saim;
    }

    public static Retrofit GetClient_BudgetBazaar(String siteName)
    {
        if(rft_budgetbazaar==null)
        {
            rft_budgetbazaar = new Retrofit.Builder().baseUrl(siteName).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return  rft_budgetbazaar;
    }

    public static Retrofit GetClient_Fabi(String siteName)
    {
        if(rft_fabi==null)
        {
            rft_fabi = new Retrofit.Builder().baseUrl(siteName).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return  rft_fabi;
    }



}

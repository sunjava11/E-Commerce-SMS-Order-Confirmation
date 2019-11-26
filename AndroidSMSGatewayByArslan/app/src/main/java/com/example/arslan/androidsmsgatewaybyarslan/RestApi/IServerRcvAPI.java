package com.example.arslan.androidsmsgatewaybyarslan.RestApi;

import com.example.arslan.androidsmsgatewaybyarslan.ClientRcvSms;
import com.example.arslan.androidsmsgatewaybyarslan.Model.ServerRcvSmsModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by Arslan on 30-Jul-16.
 */
public interface IServerRcvAPI {
    @GET("SendSms.php")
    Call<List<ServerRcvSmsModel>> GetServerMessages();

    @POST("ReceiveSms.php")
    Call<Boolean> SendClientMessagesOnServer(@Body List<ClientRcvSms> clientsMessagesList);

}

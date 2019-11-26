package com.example.arslan.androidsmsgatewaybyarslan;

import android.provider.Settings;
import android.telephony.SmsManager;
import android.util.Log;

import com.example.arslan.androidsmsgatewaybyarslan.Model.ServerRcvSmsModel;
import com.example.arslan.androidsmsgatewaybyarslan.RestApi.ApiClient;
import com.example.arslan.androidsmsgatewaybyarslan.RestApi.IServerRcvAPI;
import com.orm.util.NamingHelper;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Arslan on 02-Aug-16.
 */
public class ConfirmationMessage {
    final String TagName = "GetMessageFromServer";

    private IServerRcvAPI apiService = ApiClient.GetClient(GlobalVars.GlobalSession.BASE_URL_BAZAARKEY).create(IServerRcvAPI.class);
//    private IServerRcvAPI apiService_Mamji = ApiClient.GetClient_Mamji(GlobalVars.GlobalSession.BASE_URL_MAMJI).create(IServerRcvAPI.class);
//    private IServerRcvAPI apiService_Saim = ApiClient.GetClient_Saim(GlobalVars.GlobalSession.BASE_URL_Saim).create(IServerRcvAPI.class);
//    private IServerRcvAPI apiService_BudgetBazaar = ApiClient.GetClient_BudgetBazaar(GlobalVars.GlobalSession.BASE_URL_BudgetBazaar).create(IServerRcvAPI.class);
//    private IServerRcvAPI apiService_Fabi = ApiClient.GetClient_Fabi(GlobalVars.GlobalSession.BASE_URL_Fabi).create(IServerRcvAPI.class);

    ErrorLog errorLog = ErrorLog.getInstance();

    public void SendMessageOnServer(ClientRcvSms clientRcvSms) {
        final String TagName = "SendMessageOnServer";
        try {

            if (clientRcvSms != null) {
                final List<ClientRcvSms> clientSmsList = new ArrayList<ClientRcvSms>();
                //final List<ClientRcvSms> clientSmsList_Abbazzar = new ArrayList<ClientRcvSms>();


                clientSmsList.add(clientRcvSms);


                if(clientSmsList.size()>0)
                {
                    Call<Boolean> apiCall=null;
//                   if(ValidateMessage(clientRcvSms.Message, GlobalVars.GlobalSession.MAMJI)==true)
//                   {
//                       apiCall = apiService_Mamji.SendClientMessagesOnServer(clientSmsList);
//                   }
//                    else if(ValidateMessage(clientRcvSms.Message, GlobalVars.GlobalSession.SAIMSHOP)==true)
//                    {
//                        apiCall = apiService_Saim.SendClientMessagesOnServer(clientSmsList);
//                    }
//                   else if(ValidateMessage(clientRcvSms.Message, GlobalVars.GlobalSession.BUDGETBAZAAR)==true)
//                   {
//                       apiCall = apiService_BudgetBazaar.SendClientMessagesOnServer(clientSmsList);
//                   }
//                   else if(ValidateMessage(clientRcvSms.Message, GlobalVars.GlobalSession.FABI)==true)
//                   {
//                       apiCall = apiService_Fabi.SendClientMessagesOnServer(clientSmsList);
//                   }
                    if(ValidateMessage(clientRcvSms.Message, GlobalVars.GlobalSession.BAZAARKEY)==true)
                   {
                       apiCall = apiService.SendClientMessagesOnServer(clientSmsList);
                   }



                    apiCall.enqueue(new Callback<Boolean>() {
                        @Override
                        public void onResponse(Call<Boolean> call, Response<Boolean> response) {

                            if (response != null && response.body() == true) {
                                for (ClientRcvSms rcvSms : clientSmsList) {
                                    ClientRcvSms clientSms = rcvSms;
                                    clientSms.IsSentToServer = 1;
                                    clientSms.save();
                                    Log.i(TagName, "SMS from:"+clientSms.FromNumber+"\nMessage:"+clientSms.Message+" Saved on Server");
                                }
                            } else {
                                for (ClientRcvSms rcvSms : clientSmsList) {
                                    Log.i(TagName, "Response body is null");
                                    ClientRcvSms clientSms = rcvSms;
                                    clientSms.IsSentToServer = 0;
                                    clientSms.save();
                                    Log.i(TagName, "SMS Failed to Save on Server. from:"+clientSms.FromNumber+"\nMessage:"+clientSms.Message+" Saved Locally");
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<Boolean> call, Throwable t) {
                            Log.i(TagName, "Exception Occured", t);
                            errorLog.LogException(t);

                        }
                    });
                }

//                if(clientSmsList_Abbazzar.size()>0)
//                {
//                    Call<Boolean> apiCall_Bazzarkey = apiService_Abbazzar.SendClientMessagesOnServer(clientSmsList_Abbazzar);
//
//                    apiCall_Bazzarkey.enqueue(new Callback<Boolean>() {
//                        @Override
//                        public void onResponse(Call<Boolean> call, Response<Boolean> response) {
//
//                            if (response != null && response.body() == true) {
//                                for (ClientRcvSms rcvSms : clientSmsList) {
//                                    ClientRcvSms clientSms = rcvSms;
//                                    clientSms.IsSentToServer = 1;
//                                    clientSms.save();
//                                    Log.i(TagName, "SMS from:"+clientSms.FromNumber+"\nMessage:"+clientSms.Message+" Saved on Server");
//                                }
//                            } else {
//                                for (ClientRcvSms rcvSms : clientSmsList) {
//                                    Log.i(TagName, "Response body is null");
//                                    ClientRcvSms clientSms = rcvSms;
//                                    clientSms.IsSentToServer = 0;
//                                    clientSms.save();
//                                    Log.i(TagName, "SMS Failed to Save on Server. from:"+clientSms.FromNumber+"\nMessage:"+clientSms.Message+" Saved Locally");
//                                }
//                            }
//                        }
//
//                        @Override
//                        public void onFailure(Call<Boolean> call, Throwable t) {
//                            Log.i(TagName, "Exception Occured", t);
//                        }
//                    });
//                }

            }

        } catch (Exception ex) {
            Log.i(TagName, "Failed to send confirmation message on server", ex);
            errorLog.LogException(ex);
        }
    }

    public void SendMessageOnServer(List<ClientRcvSms> clientRcvSmsList) {
        final String TagName = "SendMessageOnServer";
        try {

            if (clientRcvSmsList != null) {

                final List<ClientRcvSms> clientSmsList_Mamji = new ArrayList<>();
                final List<ClientRcvSms> clientSmsList_Saim = new ArrayList<>();
                final List<ClientRcvSms> clientSmsList_BudgetBazaar= new ArrayList<>();
                final List<ClientRcvSms> clientSmsList_Fabi = new ArrayList<>();
                final List<ClientRcvSms> clientSmsList = new ArrayList<>();

                for (ClientRcvSms crs:
                        clientRcvSmsList ) {

//                    if(ValidateMessage(crs.Message, GlobalVars.GlobalSession.MAMJI)==true)
//                    {
//                        clientSmsList_Mamji.add(crs);
//                    }
//                    else if(ValidateMessage(crs.Message, GlobalVars.GlobalSession.SAIMSHOP)==true)
//                    {
//                        clientSmsList_Saim.add(crs);
//                    }
//                    else if(ValidateMessage(crs.Message, GlobalVars.GlobalSession.BUDGETBAZAAR)==true)
//                    {
//                        clientSmsList_BudgetBazaar.add(crs);
//                    }
//                    else if(ValidateMessage(crs.Message, GlobalVars.GlobalSession.FABI)==true)
//                    {
//                        clientSmsList_Fabi.add(crs);
//                    }
                    //else
                    if(ValidateMessage(crs.Message, GlobalVars.GlobalSession.BAZAARKEY)==true)
                    {
                        clientSmsList.add(crs);
                    }


                }


                    Call<Boolean> apiCall = apiService.SendClientMessagesOnServer(clientSmsList);
//                    Call<Boolean> apiCall_Mamji = apiService_Mamji.SendClientMessagesOnServer(clientSmsList_Mamji);
//                    Call<Boolean> apiCall_Saim = apiService_Saim.SendClientMessagesOnServer(clientSmsList_Saim);
//                    Call<Boolean> apiCall_BudgetBazaar = apiService_BudgetBazaar.SendClientMessagesOnServer(clientSmsList_BudgetBazaar);
//                    Call<Boolean> apiCall_Fabi = apiService_Fabi.SendClientMessagesOnServer(clientSmsList_Fabi);

                    apiCall.enqueue(new Callback<Boolean>() {
                        @Override
                        public void onResponse(Call<Boolean> call, Response<Boolean> response) {

                            if (response != null && response.body() == true) {
                                for (ClientRcvSms rcvSms : clientSmsList) {
                                    ClientRcvSms clientSms = rcvSms;
                                    clientSms.IsSentToServer = 1;
                                    clientSms.save();
                                    Log.i(TagName, "SMS from:"+clientSms.FromNumber+"\nMessage:"+clientSms.Message+" Saved on Server");
                                }
                            } else {
                                for (ClientRcvSms rcvSms : clientSmsList) {
                                    Log.i(TagName, "Response body is null");
                                    ClientRcvSms clientSms = rcvSms;
                                    clientSms.IsSentToServer = 0;
                                    clientSms.save();
                                    Log.i(TagName, "SMS Failed to Save on Server. from:"+clientSms.FromNumber+"\nMessage:"+clientSms.Message+" Saved Locally");
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<Boolean> call, Throwable t) {
                            Log.i(TagName, "Exception Occured", t);
                            errorLog.LogException(t);
                        }
                    });

//                    apiCall_Mamji.enqueue(new Callback<Boolean>() {
//                        @Override
//                        public void onResponse(Call<Boolean> call, Response<Boolean> response) {
//
//                            if (response != null && response.body() == true) {
//                                for (ClientRcvSms rcvSms : clientSmsList) {
//                                    ClientRcvSms clientSms = rcvSms;
//                                    clientSms.IsSentToServer = 1;
//                                    clientSms.save();
//                                    Log.i(TagName, "SMS from:"+clientSms.FromNumber+"\nMessage:"+clientSms.Message+" Saved on Server");
//                                }
//                            } else {
//                                for (ClientRcvSms rcvSms : clientSmsList) {
//                                    Log.i(TagName, "Response body is null");
//                                    ClientRcvSms clientSms = rcvSms;
//                                    clientSms.IsSentToServer = 0;
//                                    clientSms.save();
//                                    Log.i(TagName, "SMS Failed to Save on Server. from:"+clientSms.FromNumber+"\nMessage:"+clientSms.Message+" Saved Locally");
//                                }
//                            }
//                        }
//
//                        @Override
//                        public void onFailure(Call<Boolean> call, Throwable t) {
//                            Log.i(TagName, "Exception Occured", t);
//                            errorLog.LogException(t);
//                        }
//                    });
//
//                    apiCall_Saim.enqueue(new Callback<Boolean>() {
//                    @Override
//                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {
//
//                        if (response != null && response.body() == true) {
//                            for (ClientRcvSms rcvSms : clientSmsList) {
//                                ClientRcvSms clientSms = rcvSms;
//                                clientSms.IsSentToServer = 1;
//                                clientSms.save();
//                                Log.i(TagName, "SMS from:"+clientSms.FromNumber+"\nMessage:"+clientSms.Message+" Saved on Server");
//                            }
//                        } else {
//                            for (ClientRcvSms rcvSms : clientSmsList) {
//                                Log.i(TagName, "Response body is null");
//                                ClientRcvSms clientSms = rcvSms;
//                                clientSms.IsSentToServer = 0;
//                                clientSms.save();
//                                Log.i(TagName, "SMS Failed to Save on Server. from:"+clientSms.FromNumber+"\nMessage:"+clientSms.Message+" Saved Locally");
//                            }
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<Boolean> call, Throwable t) {
//                        Log.i(TagName, "Exception Occured", t);
//                        errorLog.LogException(t);
//                    }
//                });
//
//                    apiCall_BudgetBazaar.enqueue(new Callback<Boolean>() {
//                    @Override
//                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {
//
//                        if (response != null && response.body() == true) {
//                            for (ClientRcvSms rcvSms : clientSmsList) {
//                                ClientRcvSms clientSms = rcvSms;
//                                clientSms.IsSentToServer = 1;
//                                clientSms.save();
//                                Log.i(TagName, "SMS from:"+clientSms.FromNumber+"\nMessage:"+clientSms.Message+" Saved on Server");
//                            }
//                        } else {
//                            for (ClientRcvSms rcvSms : clientSmsList) {
//                                Log.i(TagName, "Response body is null");
//                                ClientRcvSms clientSms = rcvSms;
//                                clientSms.IsSentToServer = 0;
//                                clientSms.save();
//                                Log.i(TagName, "SMS Failed to Save on Server. from:"+clientSms.FromNumber+"\nMessage:"+clientSms.Message+" Saved Locally");
//                            }
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<Boolean> call, Throwable t) {
//                        Log.i(TagName, "Exception Occured", t);
//                        errorLog.LogException(t);
//                    }
//                });
//
//                    apiCall_Fabi.enqueue(new Callback<Boolean>() {
//                    @Override
//                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {
//
//                        if (response != null && response.body() == true) {
//                            for (ClientRcvSms rcvSms : clientSmsList) {
//                                ClientRcvSms clientSms = rcvSms;
//                                clientSms.IsSentToServer = 1;
//                                clientSms.save();
//                                Log.i(TagName, "SMS from:"+clientSms.FromNumber+"\nMessage:"+clientSms.Message+" Saved on Server");
//                            }
//                        } else {
//                            for (ClientRcvSms rcvSms : clientSmsList) {
//                                Log.i(TagName, "Response body is null");
//                                ClientRcvSms clientSms = rcvSms;
//                                clientSms.IsSentToServer = 0;
//                                clientSms.save();
//                                Log.i(TagName, "SMS Failed to Save on Server. from:"+clientSms.FromNumber+"\nMessage:"+clientSms.Message+" Saved Locally");
//                            }
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<Boolean> call, Throwable t) {
//                        Log.i(TagName, "Exception Occured", t);
//                        errorLog.LogException(t);
//                    }
//                });

            }

        } catch (Exception ex) {
            Log.i(TagName, "Failed to send confirmation message on server", ex);
            errorLog.LogException(ex);
        }
    }

    public void GetMessageFromServer() {

        try {

            Call<List<ServerRcvSmsModel>> apiCall = apiService.GetServerMessages();
//           Call<List<ServerRcvSmsModel>> apiCall_Mamji = apiService_Mamji.GetServerMessages();
//            Call<List<ServerRcvSmsModel>> apiCall_Saim = apiService_Saim.GetServerMessages();
//            Call<List<ServerRcvSmsModel>> apiCall_BudgetBazaar = apiService_BudgetBazaar.GetServerMessages();
//            Call<List<ServerRcvSmsModel>> apiCall_Fabi = apiService_Fabi.GetServerMessages();


            apiCall.enqueue(new Callback<List<ServerRcvSmsModel>>() {
                @Override
                public void onResponse(Call<List<ServerRcvSmsModel>> call, Response<List<ServerRcvSmsModel>> response) {

                    if (response != null) {
                        try{
                            List<ServerRcvSmsModel> responseSmsList = response.body();

                            Log.i(TagName, "Response from Server List Size:" + responseSmsList.size());
                            if ( responseSmsList.size() > 0) {
                                for (ServerRcvSmsModel rcvSms : responseSmsList) {
                                    ServerRcvSms serverSms = new ServerRcvSms(rcvSms.to, rcvSms.message, 0);
                                    long id = serverSms.save();
                                    Log.i(TagName, "Message Saved with Id:" + id);
                                }

                            }
                        }catch (Exception ex)
                        {
                            errorLog.LogException(ex);
                        }

                    } else {
                        Log.i(TagName, "Response is null");
                    }
                    SendSms();
                }

                @Override
                public void onFailure(Call<List<ServerRcvSmsModel>> call, Throwable t) {
                    Log.i(TagName, "Message Getting Failed", t);
                }
            });

//            apiCall_Mamji.enqueue(new Callback<List<ServerRcvSmsModel>>() {
//                @Override
//                public void onResponse(Call<List<ServerRcvSmsModel>> call, Response<List<ServerRcvSmsModel>> response) {
//
//                    if (response != null) {
//                        try {
//                            List<ServerRcvSmsModel> responseSmsList = response.body();
//
//                            Log.i(TagName, "Response from Server List Size:" + responseSmsList.size());
//                            if (responseSmsList.size() > 0) {
//                                for (ServerRcvSmsModel rcvSms : responseSmsList) {
//                                    ServerRcvSms serverSms = new ServerRcvSms(rcvSms.to, rcvSms.message, 0);
//                                    long id = serverSms.save();
//                                    Log.i(TagName, "Message Saved with Id:" + id);
//                                }
//
//                            }
//                        }catch (Exception ex)
//                        {
//                            errorLog.LogException(ex);
//                        }
//
//                    } else {
//                        Log.i(TagName, "Response is null");
//                    }
//                    SendSms();
//                }
//
//                @Override
//                public void onFailure(Call<List<ServerRcvSmsModel>> call, Throwable t) {
//                    Log.i(TagName, "Message Getting Failed", t);
//                }
//            });
//
//            apiCall_Saim.enqueue(new Callback<List<ServerRcvSmsModel>>() {
//                @Override
//                public void onResponse(Call<List<ServerRcvSmsModel>> call, Response<List<ServerRcvSmsModel>> response) {
//
//                    if (response != null) {
//                        try {
//                            List<ServerRcvSmsModel> responseSmsList = response.body();
//
//                            Log.i(TagName, "Response from Server List Size:" + responseSmsList.size());
//                            if (responseSmsList.size() > 0) {
//                                for (ServerRcvSmsModel rcvSms : responseSmsList) {
//                                    ServerRcvSms serverSms = new ServerRcvSms(rcvSms.to, rcvSms.message, 0);
//                                    long id = serverSms.save();
//                                    Log.i(TagName, "Message Saved with Id:" + id);
//                                }
//
//                            }
//                        }catch (Exception ex)
//                        {
//                            errorLog.LogException(ex);
//                        }
//
//                    } else {
//                        Log.i(TagName, "Response is null");
//                    }
//                    SendSms();
//                }
//
//                @Override
//                public void onFailure(Call<List<ServerRcvSmsModel>> call, Throwable t) {
//                    Log.i(TagName, "Message Getting Failed", t);
//                }
//            });
//
//            apiCall_BudgetBazaar.enqueue(new Callback<List<ServerRcvSmsModel>>() {
//                @Override
//                public void onResponse(Call<List<ServerRcvSmsModel>> call, Response<List<ServerRcvSmsModel>> response) {
//
//                    if (response != null) {
//                        try {
//                            List<ServerRcvSmsModel> responseSmsList = response.body();
//
//                            Log.i(TagName, "Response from Server List Size:" + responseSmsList.size());
//                            if (responseSmsList.size() > 0) {
//                                for (ServerRcvSmsModel rcvSms : responseSmsList) {
//                                    ServerRcvSms serverSms = new ServerRcvSms(rcvSms.to, rcvSms.message, 0);
//                                    long id = serverSms.save();
//                                    Log.i(TagName, "Message Saved with Id:" + id);
//                                }
//
//                            }
//                        }catch (Exception ex)
//                        {
//                            errorLog.LogException(ex);
//                        }
//
//                    } else {
//                        Log.i(TagName, "Response is null");
//                    }
//                    SendSms();
//                }
//
//                @Override
//                public void onFailure(Call<List<ServerRcvSmsModel>> call, Throwable t) {
//                    Log.i(TagName, "Message Getting Failed", t);
//                }
//            });
//
//            apiCall_Fabi.enqueue(new Callback<List<ServerRcvSmsModel>>() {
//                @Override
//                public void onResponse(Call<List<ServerRcvSmsModel>> call, Response<List<ServerRcvSmsModel>> response) {
//
//                    if (response != null) {
//                        try {
//                            List<ServerRcvSmsModel> responseSmsList = response.body();
//
//                            Log.i(TagName, "Response from Server List Size:" + responseSmsList.size());
//                            if (responseSmsList.size() > 0) {
//                                for (ServerRcvSmsModel rcvSms : responseSmsList) {
//                                    ServerRcvSms serverSms = new ServerRcvSms(rcvSms.to, rcvSms.message, 0);
//                                    long id = serverSms.save();
//                                    Log.i(TagName, "Message Saved with Id:" + id);
//                                }
//
//                            }
//                        }catch (Exception ex)
//                        {
//                            errorLog.LogException(ex);
//                        }
//
//                    } else {
//                        Log.i(TagName, "Response is null");
//                    }
//                    SendSms();
//                }
//
//                @Override
//                public void onFailure(Call<List<ServerRcvSmsModel>> call, Throwable t) {
//                    Log.i(TagName, "Message Getting Failed", t);
//                }
//            });
//

        } catch (
                Exception ex
                )

        {
            Log.i(TagName, "Exception Occured", ex);
            errorLog.LogException(ex);

        }
    }

    private void SendSms() {
        try {
            List<ServerRcvSms> smsList = ServerRcvSms.find(ServerRcvSms.class, NamingHelper.toSQLNameDefault("IsSmsSent") + "=?", "0");


            Log.i(TagName, "Total Messages to Send are:" + smsList.size());
            SmsManager smsmanager = SmsManager.getDefault();
            for (ServerRcvSms smsToBeSend : smsList) {

                try {
                    ServerRcvSms serverRcvSms_update = ServerRcvSms.findById(ServerRcvSms.class, smsToBeSend.getId());
                    smsmanager.sendTextMessage(smsToBeSend.ToNumber, null, smsToBeSend.Message, null, null);
                    Log.i(TagName,"Message to:"+smsToBeSend.ToNumber+" Message:"+smsToBeSend.Message);

                    if (serverRcvSms_update != null) {
                        serverRcvSms_update.IsSmsSent = 1;
                        serverRcvSms_update.save();
                        Log.i(TagName, "Message Id:" + serverRcvSms_update.getId() + " Updated");
                    }
                } catch (Exception ex) {
                    Log.i(TagName, "Exception Occured", ex);
                    errorLog.LogException(ex);
                }
            }

        } catch (Exception ex) {
            Log.i(TagName, "Exception Occured", ex);
            errorLog.LogException(ex);
        }


    }

    private boolean ValidateMessage(String message, int siteCode)
    {

        if(siteCode== GlobalVars.GlobalSession.MAMJI)
        {
            return false;
//            if(message.length()==1)
//            {
//                int msg =0;
//                try {
//                    msg = Integer.parseInt(message);
//                }
//                catch(Exception ex) {
//
//            }
//                if(msg==1 || msg==2)
//                {
//                   // String af = "";
//                    return true;
//                }
//            }
        }
        else if(siteCode== GlobalVars.GlobalSession.SAIMSHOP)
        {
            return false;
//            if(message.length()==6 || message.length()==7)
//            {
//                if(message.toLowerCase().contains("confirm") || message.toLowerCase().contains("cancel"))
//                {
//                    return true;
//                }
//            }
        }
        else if(siteCode== GlobalVars.GlobalSession.BUDGETBAZAAR)
        {
            return false;
//            if(message.length()==1)
//            {
//                if(message.toLowerCase().contains("y") || message.toLowerCase().contains("n"))
//                {
//                    return true;
//                }
//            }
        }
        else if(siteCode== GlobalVars.GlobalSession.FABI)
        {
//            if(message.length()==4 || message.length()==3)
//            {
//                if(message.toLowerCase().contains("done") || message.toLowerCase().contains("not"))
//                {
//                    return true;
//                }
//            }
        }
        else if(siteCode== GlobalVars.GlobalSession.BAZAARKEY)
        {
            return true;
        }

        return false;
    }

}

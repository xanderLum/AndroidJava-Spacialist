package com.spacialist.ui.main;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.spacialist.data.Constants;
import com.spacialist.data.dto.MessageJSON;
import com.spacialist.data.dto.NotificationDTO;
import com.spacialist.data.dto.RegisterDTO;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

public class NotificationViewModel extends ViewModel {
    private JSONArray restulJsonArray;
    private int success = 0;

    protected RegisterDTO userDTO;
    private MessageJSON messageJSON;

    private MutableLiveData<MessageJSON> message = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>();

    LiveData<MessageJSON> getMessageJSON() {
        return message;
    }

    LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public void saveNotification(NotificationDTO notificationDTO) {
        new NotificationViewModel.ServiceStubAsyncTask(notificationDTO).execute();
    }

    private class ServiceStubAsyncTask extends AsyncTask<Void, Void, Void> {

        private Context mContext;
        private Activity mActivity;
        String response = "";
        HashMap<String, String> postDataParams;
        String username, password;
        NotificationDTO notificationDTO;

        public ServiceStubAsyncTask(NotificationDTO notificationDTO) {
            this.notificationDTO = notificationDTO;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            isLoading.setValue(Boolean.TRUE);
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            System.out.println("Registration in progress...");
            postDataParams = new HashMap<String, String>();
            postDataParams.put("sender_user_id", notificationDTO.getUserId());
            postDataParams.put("receiver_bus_id", notificationDTO.getBusId());
            postDataParams.put("appointment_id", notificationDTO.getAppointmentId());

            JSONObject jsonRequest = new JSONObject(postDataParams);
            String saveNotifForBusinessResponse = callPOST(jsonRequest.toString());
            System.out.println("RESPONSE: " + saveNotifForBusinessResponse);

            postDataParams = new HashMap<String, String>();
            postDataParams.put("title", notificationDTO.getTitle());
            postDataParams.put("message", notificationDTO.getMessage());
            postDataParams.put("image_url", notificationDTO.getImage_url());
            postDataParams.put("firebase_token", notificationDTO.getFirebaseToken());
            postDataParams.put("firebase_api", notificationDTO.getFirebaseAPI());
            jsonRequest = new JSONObject(postDataParams);

            String sendFirebaseNotifResponse = callPOSTSendFireBaseNotif(jsonRequest.toString());
            System.out.println("RESPONSE: " + sendFirebaseNotifResponse);
            messageJSON = new MessageJSON();
            messageJSON.setMessage(sendFirebaseNotifResponse);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            if (null != messageJSON && messageJSON.getMessage().equalsIgnoreCase(Constants.FIREBASE_SEND_NOTIF_SUCCESS)) {
                System.out.println("Success == 1 true");
                isLoading.setValue(Boolean.FALSE);
                message.setValue(messageJSON);
            } else {
                isLoading.setValue(Boolean.FALSE);
                System.out.println("Notification creation failed.");
                message.setValue(messageJSON);
            }
        }
    }//end of async task

    private String callPOST(String json) {

        System.out.println("POST PARAM: " + json);
        String urlString = Constants.CREATE_NOTIFICATION_FOR_APPOINTMENT; // URL to call
        System.out.println("RegisterAPIPath = " + urlString);
        String data = json; //data to post
        OutputStream out = null;

        try {
            URL url = new URL(urlString);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json; utf-8");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);

            try (OutputStream os = con.getOutputStream()) {
                byte[] input = json.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            if (con.getResponseCode() == 201 || con.getResponseCode() == 200) {
                try (BufferedReader br = new BufferedReader(
                        new InputStreamReader(con.getInputStream(), "utf-8"))) {
                    StringBuilder response = new StringBuilder();
                    String responseLine = null;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }
                    System.out.println(response.toString());
                }
            } else {
                System.out.println("Status code: " + con.getResponseCode() + "\nResponse Message: " + con.getResponseMessage());
                try (BufferedReader br = new BufferedReader(
                        new InputStreamReader(con.getErrorStream(), "utf-8"))) {
                    StringBuilder response = new StringBuilder();
                    String responseLine = null;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }
                    System.out.println("Error Stream response: " + response.toString());
                }

                try (BufferedReader br = new BufferedReader(
                        new InputStreamReader(con.getInputStream(), "utf-8"))) {
                    StringBuilder response = new StringBuilder();
                    String responseLine = null;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }
                    System.out.println("Input Stream response: " + response.toString());
                }

                return Constants.NOTIFICATION_FAILURE;
            }
        } catch (Exception e) {
            System.out.println("EXCEPTION HERE : " + e.getMessage());
            return Constants.NOTIFICATION_FAILURE;
        }
        return Constants.NOTIFICATION_SUCCESS;
    }

    private String callPOSTSendFireBaseNotif(String json) {

        System.out.println("POST PARAM: " + json);
        String urlString = Constants.SEND_FIREBASE_NOTIFICATION_FOR_APPOINTMENT; // URL to call
        System.out.println("RegisterAPIPath = " + urlString);
        String data = json; //data to post
        OutputStream out = null;

        try {
            URL url = new URL(urlString);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json; utf-8");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);

            try (OutputStream os = con.getOutputStream()) {
                byte[] input = json.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            if (con.getResponseCode() == 201 || con.getResponseCode() == 200) {
                try (BufferedReader br = new BufferedReader(
                        new InputStreamReader(con.getInputStream(), "utf-8"))) {
                    StringBuilder response = new StringBuilder();
                    String responseLine = null;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }
                    System.out.println(response.toString());
                }
            } else {
                System.out.println("Status code: " + con.getResponseCode() + "\nResponse Message: " + con.getResponseMessage());
                try (BufferedReader br = new BufferedReader(
                        new InputStreamReader(con.getErrorStream(), "utf-8"))) {
                    StringBuilder response = new StringBuilder();
                    String responseLine = null;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }
                    System.out.println("Error Stream response: " + response.toString());
                }

                try (BufferedReader br = new BufferedReader(
                        new InputStreamReader(con.getInputStream(), "utf-8"))) {
                    StringBuilder response = new StringBuilder();
                    String responseLine = null;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }
                    System.out.println("Input Stream response: " + response.toString());
                }

                return Constants.FIREBASE_SEND_NOTIF_FAILED;
            }
        } catch (Exception e) {
            System.out.println("EXCEPTION HERE : " + e.getMessage());
            return Constants.FIREBASE_SEND_NOTIF_FAILED;
        }
        return Constants.FIREBASE_SEND_NOTIF_SUCCESS;
    }

}

package com.spacialist.ui.main;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spacialist.data.Constants;
import com.spacialist.data.dto.MessageJSON;
import com.spacialist.data.dto.PaymentDetailsDTO;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

public class PaymentViewModel extends ViewModel {
    private int success = 0;

    private MessageJSON messageJSON;
    private String appointmentId;

    private MutableLiveData<MessageJSON> message = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>();

    LiveData<MessageJSON> getMessageJSON() {
        return message;
    }

    LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public void saveTransaction(PaymentDetailsDTO paymentDetailsDTO) {
        new PaymentViewModel.ServiceStubAsyncTask(paymentDetailsDTO).execute();
    }

    private class ServiceStubAsyncTask extends AsyncTask<Void, Void, Void> {

        private Context mContext;
        private Activity mActivity;
        String response = "";
        HashMap<String, String> postDataParams;
        String username, password;
        PaymentDetailsDTO paymentDetailsDTO;

        public ServiceStubAsyncTask(PaymentDetailsDTO paymentDetailsDTO) {
            this.username = username;
            this.password = password;
            this.paymentDetailsDTO = paymentDetailsDTO;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            isLoading.setValue(Boolean.TRUE);
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            System.out.println("Transaction in progress...");
            postDataParams = new HashMap<String, String>();
            postDataParams.put("user_id", paymentDetailsDTO.getUserId());
            postDataParams.put("bus_id", paymentDetailsDTO.getBusId());
            postDataParams.put("payment_id", paymentDetailsDTO.getPaymentId());
            postDataParams.put("payment_details", paymentDetailsDTO.getPaymentDetails());
            postDataParams.put("amount", paymentDetailsDTO.getAmount());
            postDataParams.put("staff_service_id", paymentDetailsDTO.getStaff_service_id());
            postDataParams.put("start_time", paymentDetailsDTO.getStartTime());
            postDataParams.put("end_time", paymentDetailsDTO.getEndTime());
            postDataParams.put("sched_date", paymentDetailsDTO.getSchedDate());
            postDataParams.put("appointment_name", paymentDetailsDTO.getAppointmentName());
            postDataParams.put("appointment_desc", paymentDetailsDTO.getAppointmentDesc());

            // Construct a JSONObject from a Map.
            JSONObject jsonRequest = new JSONObject(postDataParams);

            messageJSON = new MessageJSON();
            String response = callPOST(jsonRequest.toString());
            System.out.println("RESPONSE: " + response);
            messageJSON.setMessage(response);

            appointmentId = messageJSON.getAppointmentId();
            System.out.println("Appointment ID: " + appointmentId);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            if (null != messageJSON) {
                System.out.println("Success == 1 true");
                isLoading.setValue(Boolean.FALSE);
                message.setValue(messageJSON);
            } else {
                isLoading.setValue(Boolean.FALSE);
                System.out.println("Saving Transaction Failed");
                message.setValue(messageJSON);
            }
        }
    }//end of async task

    private String callPOST(String json) {
        System.out.println("json String: " + json);
        String urlString = Constants.CREATE_TRANSACTION_APPOINTMENT_SCHEDULE; // URL to call
        System.out.println("Save Transaction API PATH = " + urlString);
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

                    JSONObject jsonObject = new JSONObject(response.toString());
                    messageJSON.setAppointmentId(jsonObject.getString("appointment_id"));
                    System.out.println("API call response: "+response.toString());
                    System.out.println("JSON appointment ID: "+messageJSON.getAppointmentId());
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

                return Constants.TRANSACTION_FAILURE;
            }
        } catch (Exception e) {
            System.out.println("EXCEPTION HERE : " + e.getMessage());
            return Constants.TRANSACTION_FAILURE;
        }
        return Constants.TRANSACTION_SUCCESS;
    }

}

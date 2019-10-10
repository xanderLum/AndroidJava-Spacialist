package com.spacialist.ui.login;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Patterns;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.spacialist.R;
import com.spacialist.data.Constants;
import com.spacialist.data.dto.MessageJSON;
import com.spacialist.data.dto.RegisterDTO;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

public class EditProfileViewModel extends ViewModel {
    private int success = 0;

    protected RegisterDTO userDTO;
    private MessageJSON messageJSON;

    private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    private MutableLiveData<MessageJSON> message = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>();

    LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    public LiveData<MessageJSON> getMessageJSON() {
        return message;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public void register(RegisterDTO registerDTO) {
        new EditProfileViewModel.ServiceStubAsyncTask(registerDTO).execute();
    }

    public void registerDataChanged(String username, String password) {
        if (!isUserNameValid(username)) {
            loginFormState.setValue(new LoginFormState(R.string.invalid_username, null));
        } else if (!isPasswordValid(password)) {
            loginFormState.setValue(new LoginFormState(null, R.string.invalid_password));
        } else {
            loginFormState.setValue(new LoginFormState(true));
        }
    }

    // A placeholder username validation check
    private boolean isUserNameValid(String username) {
        if (username == null) {
            return false;
        }
        if (username.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        } else {
            return !username.trim().isEmpty();
        }
    }

    // A placeholder password validation check
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }

    private class ServiceStubAsyncTask extends AsyncTask<Void, Void, Void> {

        private Context mContext;
        private Activity mActivity;
        String response = "";
        HashMap<String, String> postDataParams;
        String username, password;
        RegisterDTO updatedProfile;

        public ServiceStubAsyncTask(RegisterDTO registerDTO) {
            this.username = username;
            this.password = password;
            this.updatedProfile = registerDTO;
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
            postDataParams.put("user_id", updatedProfile.getUserId());
            postDataParams.put("username", updatedProfile.getUsername());
            postDataParams.put("password", updatedProfile.getPassword());
            postDataParams.put("firstname", updatedProfile.getFirstname());
            postDataParams.put("lastname", updatedProfile.getLastname());
            postDataParams.put("phone_num", updatedProfile.getPhoneNum());
            postDataParams.put("address", updatedProfile.getAddress());
            postDataParams.put("email", updatedProfile.getEmail());

            JSONObject jsonObject = new JSONObject(postDataParams);
            String response = callPOST(jsonObject.toString());
            System.out.println("RESPONSE: " + response);
            messageJSON = new MessageJSON();
            messageJSON.setMessage(response);
            messageJSON.setUpdatedProfile(updatedProfile);

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            if (null != messageJSON && messageJSON.getMessage().equalsIgnoreCase(Constants.UPDATE_SUCCESS)
                    && messageJSON.getUpdatedProfile() != null) {
                System.out.println("Success == 1 true");
                isLoading.setValue(Boolean.FALSE);
                message.setValue(messageJSON);
            } else {
                isLoading.setValue(Boolean.FALSE);
                System.out.println("User creation failed.");
                MessageJSON failed = new MessageJSON();
                failed.setError(R.string.update_profile_failed);
                message.setValue(failed);
            }
        }
    }//end of async task

    private String callPOST(String json) {

        System.out.println("POST PARAM: " + json);
        String urlString = Constants.UPDATE_USER_PROFILE_API_PATH; // URL to call
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

            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(con.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                System.out.println(response.toString());
            }

        } catch (Exception e) {
            System.out.println("EXCEPTION HERE : " + e.getMessage());
            return Constants.UPDATE_FAILED;
        }
        return Constants.UPDATE_SUCCESS;
    }

}

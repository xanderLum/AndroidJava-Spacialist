package com.spacialist.ui.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Patterns;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spacialist.data.Constants;
import com.spacialist.data.Result;
import com.spacialist.data.dto.UserDTO;
import com.spacialist.data.model.LoggedInUser;
import com.spacialist.R;
import com.spacialist.service.HttpConnectionService;

import org.json.JSONArray;

import java.io.IOException;
import java.util.HashMap;

public class LoginViewModel extends ViewModel {
    //    private String loginAPIPath= Constants.HOST+Constants.APP+Constants.BACKSLASH+Constants.ENDPOINT_LOGIN+Constants.BACKSLASH;
//    private String loginAPIPath ="http://172.20.10.5/spacialist/userController/read_OneUser.php?username=%s&password=%s";
    private JSONArray restulJsonArray;
    private int success = 0;

    protected UserDTO userDTO;

    private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    private MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>();

    LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    LiveData<LoginResult> getLoginResult() {
        return loginResult;
    }

    LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public void login(String username, String password) {
        new ServiceStubAsyncTask(username, password).execute();
    }

    public void loginDataChanged(String username, String password) {
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

        public ServiceStubAsyncTask(String username, String password) {
            this.username = username;
            this.password = password;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            isLoading.setValue(Boolean.TRUE);
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            System.out.println("Xander do in background");
            postDataParams = new HashMap<String, String>();
            postDataParams.put("HTTP_ACCEPT", "application/json");

            HttpConnectionService service = new HttpConnectionService();
//            final String fPath = loginAPIPath.concat(username.concat(Constants.BACKSLASH+password)).concat(Constants.BACKSLASH);

            final String fPath = String.format(Constants.LOGIN_API_PATH, username, password);
            System.out.println("fPath = " + fPath);
            response = service.sendRequest(fPath, postDataParams);
            System.out.println("RESPONSE: " + response);
            ObjectMapper mapper = new ObjectMapper();
            try {
                userDTO = mapper.readValue(response, UserDTO.class);
                success = 1;
                System.out.println("Query Response" + response);
                System.out.println("Display JSON list: " + userDTO.toString());
                System.out.println("Success Query");
            } catch (IOException e) {
                success = 0;
                System.out.println("Failed Query");
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            System.out.println("Xander On Post Execute");

            if (success == 1 && null != userDTO) {
                System.out.println("Success == 1 true");
                isLoading.setValue(Boolean.FALSE);
                System.out.println("UserDTO is NOT NULL");
                LoggedInUser user = null;
                System.out.println("userDTO = " + userDTO.toString());
                user = new LoggedInUser(userDTO.getUser_id(), userDTO.getUsername());
                LoggedInUser data = new Result.Success<>(user).getData();
                LoggedInUserView userView = new LoggedInUserView(data.getDisplayName());
                userView.setAddress(userDTO.getAddress());
                userView.setFirstname(userDTO.getFirstname());
                userView.setLastname(userDTO.getLastname());
                userView.setPassword(this.password);
                userView.setPhone_num(userDTO.getPhone_num());
                userView.setEmail(userDTO.getEmail());
                userView.setUserId(userDTO.getUser_id());

                loginResult.setValue(new LoginResult(userView));
            } else {
                isLoading.setValue(Boolean.FALSE);
                System.out.println("UserList is EMPTY");
                loginResult.setValue(new LoginResult(R.string.login_failed));
            }
        }
    }//end of async task
}

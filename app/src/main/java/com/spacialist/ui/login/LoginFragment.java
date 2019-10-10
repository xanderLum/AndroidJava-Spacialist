package com.spacialist.ui.login;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.spacialist.R;
import com.spacialist.data.Constants;
import com.spacialist.ui.main.NavigationFragment;
import com.spacialist.ui.main.NavigationHost;

public class LoginFragment extends Fragment {

    private LoginViewModel loginViewModel;
    private ProgressDialog processDialog;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_login, container, false);
        loginViewModel = ViewModelProviders.of(this, new ViewModelFactory())
                .get(LoginViewModel.class);

        final TextInputEditText usernameEditText = view.findViewById(R.id.username);
        final TextInputEditText passwordEditText = view.findViewById(R.id.password);
        final TextInputLayout passwordTextInput = view.findViewById(R.id.password_text_input);
        MaterialButton loginButton = view.findViewById(R.id.login_button);
        MaterialButton registerButton = view.findViewById(R.id.register_button);

        loginViewModel.getLoginResult().observe(this, new Observer<LoginResult>() {
            @Override
            public void onChanged(@Nullable LoginResult loginResult) {
                if (loginResult == null) {
                    return;
                }
                if (loginResult.getError() != null) {
                    showLoginFailed(loginResult.getError());
                }
                if (loginResult.getSuccess() != null) {
                    updateUiWithUser(loginResult.getSuccess());
                }
            }
        });

        // Set an error if the password is less than 8 characters.
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isPasswordValid(passwordEditText.getText())) {
                    passwordTextInput.setError(getString(R.string.shr_error_password));
                } else {
                    passwordTextInput.setError(null); // Clear the error
                    loginViewModel.login(usernameEditText.getText().toString(),
                            passwordEditText.getText().toString());
                }
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((NavigationHost) getActivity()).navigateTo(new RegisterFragment(), true); // Navigate to the next Fragment
            }
        });

        loginViewModel.getIsLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if (aBoolean) {
                    processDialog = new ProgressDialog(getActivity());
                    processDialog.setMessage("Please  Wait ...");
                    processDialog.setCancelable(false);
                    processDialog.show();
                } else {
                    processDialog.dismiss();
                }
            }
        });

        // Clear the error once more than 8 characters are typed.
        passwordEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (isPasswordValid(passwordEditText.getText())) {
                    passwordTextInput.setError(null); //Clear the error
                }
                return false;
            }
        });
        return view;
    }

    /*
        In reality, this will have more complex logic including, but not limited to, actual
        authentication of the username and password.
     */
    private boolean isPasswordValid(@Nullable Editable text) {
        return text != null && text.length() >= 5;
    }

    private void updateUiWithUser(LoggedInUserView model) {
        String welcome = String.format(getString(R.string.welcome), model.getDisplayName());
        Toast.makeText(getActivity(), welcome, Toast.LENGTH_LONG).show();
        Fragment bookFragment = new NavigationFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.USER_ID, model.getUserId());
        bundle.putString(Constants.USER_DISPLAY_NAME, model.getDisplayName());
        bundle.putString(Constants.USER_PASS, model.getPassword());
        bundle.putString(Constants.USER_ADDRESS, model.getAddress());
        bundle.putString(Constants.USER_FIRST_NAME, model.getFirstname());
        bundle.putString(Constants.USER_LAST_NAME, model.getLastname());
        bundle.putString(Constants.USER_FULL_NAME, model.getFirstname() + " " + model.getLastname());
        bundle.putString(Constants.USER_PHONE_NUM, model.getPhone_num());
        bundle.putString(Constants.USER_EMAIL, model.getEmail());
        bookFragment.setArguments(bundle);
        ((NavigationHost) getActivity()).navigateTo(bookFragment, false); // Navigate to the next Fragment
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getActivity(), errorString, Toast.LENGTH_SHORT).show();
    }
}

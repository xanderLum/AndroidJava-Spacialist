package com.spacialist.ui.login;

import android.app.ProgressDialog;
import android.os.Bundle;
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
import com.spacialist.data.dto.MessageJSON;
import com.spacialist.data.dto.RegisterDTO;
import com.spacialist.ui.main.NavigationHost;

public class RegisterFragment extends Fragment {

    private RegisterViewModel registerViewModel;
    private ProgressDialog processDialog;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_register, container, false);
        registerViewModel = ViewModelProviders.of(this, new ViewModelFactory())
                .get(RegisterViewModel.class);

        final TextInputEditText usernameEditText = view.findViewById(R.id.username);
        final TextInputEditText passwordEditText = view.findViewById(R.id.password);
        final TextInputLayout passwordTextInput = view.findViewById(R.id.password_text_input);
        final TextInputEditText address = view.findViewById(R.id.address);
        final TextInputEditText phoneNum = view.findViewById(R.id.phone_num);
        final TextInputEditText firstname = view.findViewById(R.id.firstname);
        final TextInputEditText lastname = view.findViewById(R.id.lastname);
        final TextInputEditText email = view.findViewById(R.id.email);

        MaterialButton cancel_button = view.findViewById(R.id.cancel_button);
        MaterialButton registerButton = view.findViewById(R.id.register_button);

        registerViewModel.getMessageJSON().observe(this, new Observer<MessageJSON>() {
            @Override
            public void onChanged(@Nullable MessageJSON message) {
                if (message == null) {
                    return;
                }
                if (message.getError() != null) {
                    showRegistrationFailed(message.getError());
                }
                if (message.getMessage() != null) {
                    updateUiWithUser(message.getMessage());
                }
            }
        });

        // Set an error if the password is less than 8 characters.
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isPasswordValid(passwordEditText.getText())) {
                    passwordTextInput.setError(getString(R.string.shr_error_password));
                } else {
                    passwordTextInput.setError(null); // Clear the error
                    RegisterDTO registerDTO = new RegisterDTO();
                    registerDTO.setAddress(address.getText().toString());
                    registerDTO.setFirstname(firstname.getText().toString());
                    registerDTO.setLastname(lastname.getText().toString());
                    registerDTO.setPhoneNum(phoneNum.getText().toString());
                    registerDTO.setPassword(passwordEditText.getText().toString());
                    registerDTO.setUsername(usernameEditText.getText().toString());
                    registerDTO.setEmail(email.getText().toString());
                    registerViewModel.register(registerDTO);
                }
            }
        });

        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((NavigationHost) getActivity()).navigateTo(new LoginFragment(), false); // Navigate to the next Fragment
            }
        });

        registerViewModel.getIsLoading().observe(this, new Observer<Boolean>() {
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

    private void updateUiWithUser(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
        Fragment loginFragment = new LoginFragment();
        ((NavigationHost) getActivity()).navigateTo(loginFragment, false); // Navigate to the next Fragment
    }

    private void showRegistrationFailed(@StringRes Integer errorString) {
        Toast.makeText(getActivity(), errorString, Toast.LENGTH_SHORT).show();
    }
}

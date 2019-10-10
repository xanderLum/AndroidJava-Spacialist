package com.spacialist.ui.nav;

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
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.spacialist.R;
import com.spacialist.data.Constants;
import com.spacialist.data.dto.MessageJSON;
import com.spacialist.data.dto.RegisterDTO;
import com.spacialist.ui.login.EditProfileViewModel;
import com.spacialist.ui.login.ViewModelFactory;
import com.spacialist.ui.main.NavigationFragment;
import com.spacialist.ui.main.NavigationHost;

public class EditProfileFragment extends Fragment {

    private Bundle bundle;
    TextInputEditText usernameEditText;
    TextInputEditText passwordEditText;
    TextInputLayout passwordTextInput;
    TextInputEditText address;
    TextInputEditText phoneNum;
    TextInputEditText firstname;
    TextInputEditText lastname;
    TextInputEditText email;

    private ProgressDialog processDialog;

    private EditProfileViewModel editProfileViewModel;


    public EditProfileFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.activity_edit_profile, container, false);
        bundle = this.getArguments();
        initViews(view);
        setViewElements(bundle);
        // Set up the tool bar
        setUpToolbar(view);

        MaterialButton cancel_button = view.findViewById(R.id.cancel_button);
        MaterialButton saveProfile_button = view.findViewById(R.id.save_profile_button);

        editProfileViewModel = ViewModelProviders.of(this, new ViewModelFactory())
                .get(EditProfileViewModel.class);

        editProfileViewModel.getMessageJSON().observe(this, new Observer<MessageJSON>() {
            @Override
            public void onChanged(@Nullable MessageJSON message) {
                if (message == null && message.getUpdatedProfile() == null) {
                    return;
                }
                if (message.getError() != null) {
                    showUpdateProfileFailed(message.getError());
                }
                if (message.getMessage() != null && message.getUpdatedProfile() != null) {
                    updateUiWithUpdatedUser(message.getMessage(), message.getUpdatedProfile());
                }
            }
        });

        // Set an error if the password is less than 8 characters.
        saveProfile_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isPasswordValid(passwordEditText.getText())) {
                    passwordTextInput.setError(getString(R.string.shr_error_password));
                } else {
                    passwordTextInput.setError(null); // Clear the error
                    RegisterDTO registerDTO = new RegisterDTO();
                    registerDTO.setUserId(bundle.getString(Constants.USER_ID));
                    registerDTO.setAddress(address.getText().toString());
                    registerDTO.setFirstname(firstname.getText().toString());
                    registerDTO.setLastname(lastname.getText().toString());
                    registerDTO.setPhoneNum(phoneNum.getText().toString());
                    registerDTO.setPassword(passwordEditText.getText().toString());
                    registerDTO.setUsername(usernameEditText.getText().toString());
                    registerDTO.setEmail(email.getText().toString());
                    editProfileViewModel.register(registerDTO);
                }
            }
        });

        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment viewProfileFragment = new NavigationFragment();
                ((NavigationFragment) viewProfileFragment).setSelectedItem(R.id.action_view_profile);
                ((NavigationFragment) viewProfileFragment).setHasPreSelectedNav(true);
                viewProfileFragment.setArguments(bundle);
                ((NavigationHost) getActivity()).navigateTo(viewProfileFragment, false);
            }
        });

        editProfileViewModel.getIsLoading().observe(this, new Observer<Boolean>() {
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

    private void setUpToolbar(View view) {
        Toolbar toolbar = view.findViewById(R.id.app_bar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity != null) {
            activity.setSupportActionBar(toolbar);
        }
    }

    private boolean isPasswordValid(@Nullable Editable text) {
        return text != null && text.length() >= 5;
    }

    private void updateUiWithUpdatedUser(String message, RegisterDTO updatedProfile) {
        Fragment viewProfileFragment = new NavigationFragment();
        bundle.putString(Constants.USER_ID, updatedProfile.getUserId());
        bundle.putString(Constants.USER_DISPLAY_NAME, updatedProfile.getUsername());
        bundle.putString(Constants.USER_ADDRESS, updatedProfile.getAddress());
        bundle.putString(Constants.USER_FULL_NAME, updatedProfile.getFirstname() + " " + updatedProfile.getLastname());
        bundle.putString(Constants.USER_PHONE_NUM, updatedProfile.getPhoneNum());
        bundle.putString(Constants.USER_EMAIL, updatedProfile.getEmail());
        viewProfileFragment.setArguments(bundle);

        ((NavigationFragment) viewProfileFragment).setSelectedItem(R.id.action_view_profile);
        ((NavigationFragment) viewProfileFragment).setHasPreSelectedNav(true);
        ((NavigationHost) getActivity()).navigateTo(viewProfileFragment, false);

        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }

    private void showUpdateProfileFailed(@StringRes Integer errorString) {
        Toast.makeText(getActivity(), errorString, Toast.LENGTH_SHORT).show();
    }

    private void initViews(View view) {
        usernameEditText = view.findViewById(R.id.username);
        passwordEditText = view.findViewById(R.id.password);
        passwordTextInput = view.findViewById(R.id.password_text_input);
        address = view.findViewById(R.id.address);
        phoneNum = view.findViewById(R.id.phone_num);
        firstname = view.findViewById(R.id.firstname);
        lastname = view.findViewById(R.id.lastname);
        email = view.findViewById(R.id.email);
    }

    private void setViewElements(Bundle bundle) {
        usernameEditText.setText(bundle.getString(Constants.USER_DISPLAY_NAME));
        passwordEditText.setText(bundle.getString(Constants.USER_PASS));
        address.setText(bundle.getString(Constants.USER_ADDRESS));
        phoneNum.setText(bundle.getString(Constants.USER_PHONE_NUM));
        firstname.setText(bundle.getString(Constants.USER_FIRST_NAME));
        lastname.setText(bundle.getString(Constants.USER_LAST_NAME));
        email.setText(bundle.getString(Constants.USER_EMAIL));
    }
}

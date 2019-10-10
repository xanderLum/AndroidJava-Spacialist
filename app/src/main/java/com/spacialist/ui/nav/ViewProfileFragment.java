package com.spacialist.ui.nav;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.spacialist.R;
import com.spacialist.data.Constants;
import com.spacialist.ui.main.MainActivity;
import com.spacialist.ui.main.NavigationHost;

public class ViewProfileFragment extends Fragment {

    private Bundle bundle;
    private TextView username;
    private TextView phoneNum;
    private TextView fullName;
    private TextView address;
    private TextView updateProfile;
    private TextView email;
    private MaterialButton logout;

    public ViewProfileFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.activity_profile, container, false);
        bundle = this.getArguments();
        initViews(view);
        setViewElements(bundle);

        updateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment editProfileFragment = new EditProfileFragment();
                editProfileFragment.setArguments(bundle);
                ((NavigationHost) getActivity()).navigateTo(editProfileFragment, true);
            }
        });

//        logout.getBackground().setColorFilter(getResources().getColor(R.color.logout), PorterDuff.Mode.MULTIPLY);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
        return view;
    }

    private void initViews(View view) {
        username = view.findViewById(R.id.username);
        fullName = view.findViewById(R.id.full_name);
        address = view.findViewById(R.id.address);
        phoneNum = view.findViewById(R.id.phone_num);
        email = view.findViewById(R.id.email);
        updateProfile = view.findViewById(R.id.updateProfileTxtView);
        logout = view.findViewById(R.id.logout_button);
    }

    private void setViewElements(Bundle bundle) {
        username.setText(bundle.getString(Constants.USER_DISPLAY_NAME));
        fullName.setText(bundle.getString(Constants.USER_FULL_NAME));
        address.setText(bundle.getString(Constants.USER_ADDRESS));
        phoneNum.setText(bundle.getString(Constants.USER_PHONE_NUM));
        email.setText(bundle.getString(Constants.USER_EMAIL));
    }
}

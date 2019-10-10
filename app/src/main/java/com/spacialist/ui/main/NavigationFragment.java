package com.spacialist.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.spacialist.R;
import com.spacialist.data.Constants;
import com.spacialist.ui.nav.BookSpaFragment;
import com.spacialist.ui.nav.CheckHistoryFragment;
import com.spacialist.ui.nav.ViewProfileFragment;

public class NavigationFragment extends Fragment {

    String userDisplayName = null;
    Bundle userBundle = null;
    private BottomNavigationView bottomNav;
    private int itemId;
    private boolean hasPreSelectedNav = false;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main, container, false);

        Bundle extras = this.getArguments();
        if (extras != null) {
            this.userDisplayName = extras.getString(Constants.USER_DISPLAY_NAME);
            //The key argument here must match that used in the other activity
        }

        bottomNav = view.findViewById(R.id.bottom_navigation);
        if (hasPreSelectedNav) {
            bottomNav.setSelectedItemId(itemId);
        }

        bottomNav.setOnNavigationItemSelectedListener(navListener);

        Fragment bookFragment = new BookSpaFragment();
        userBundle = extras;
        bookFragment.setArguments(userBundle);

        //I added this if statement to keep the selected fragment when rotating the device
        if (savedInstanceState == null) {
            ((NavigationHost) getActivity()).switchNav(R.id.fragment_container, bookFragment); // Switch to the selected Fragment
        }
        return view;
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;
                    switch (item.getItemId()) {
                        case R.id.action_book_spa:
                            selectedFragment = new BookSpaFragment();
                            selectedFragment.setArguments(userBundle);
                            System.out.println("BookSpa Selected");
                            break;
                        case R.id.action_check_history:
                            selectedFragment = new CheckHistoryFragment();
                            selectedFragment.setArguments(userBundle);
                            System.out.println("CheckHistoryFragment Selected");
//                            ((NavigationHost) getActivity()).switchNav(R.id.fragment_container, selectedFragment);
                            break;
                       /* case R.id.action_notification:
                            selectedFragment = new NotificationFragment();
                            selectedFragment.setArguments(userBundle);
                            System.out.println("NotificationFragment Selected");
                            break;*/
                        case R.id.action_view_profile:
                            selectedFragment = new ViewProfileFragment();
                            selectedFragment.setArguments(userBundle);
                            System.out.println("ViewProfileFragment Selected");
                            break;
                    }

                    ((NavigationHost) getActivity()).switchNav(R.id.fragment_container, selectedFragment); // Switch to the selected Fragment
                    return true;
                }
            };

    public void setSelectedItem(int itemId) {
        this.itemId = itemId;
    }

    public void setHasPreSelectedNav(boolean hasPreSelectedNav) {
        this.hasPreSelectedNav = hasPreSelectedNav;
    }

}

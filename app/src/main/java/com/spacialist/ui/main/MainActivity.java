package com.spacialist.ui.main;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.paypal.android.sdk.payments.PayPalService;
import com.spacialist.data.Constants;
import com.spacialist.ui.login.LoginFragment;

import com.spacialist.R;

public class MainActivity extends AppCompatActivity implements NavigationHost {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shr_main_activity);
//        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container, new LoginFragment())
                    .commit();
        }
    }

    /**
     * Navigate to the given fragment.
     *
     * @param fragment       Fragment to navigate to.
     * @param addToBackstack Whether or not the current fragment should be added to the backstack.
     */
    @Override
    public void navigateTo(Fragment fragment, boolean addToBackstack) {
        FragmentTransaction transaction =
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container, fragment);

        if (addToBackstack) {
            transaction.addToBackStack(null);
        }

        transaction.setCustomAnimations(R.animator.fragment_animation_fade_in,
                R.animator.fragment_animation_fade_out);
        transaction.commit();
    }

    @Override
    public void switchNav(int containerViewId, Fragment selectedFragment) {
        FragmentTransaction transaction =
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(containerViewId,
                                selectedFragment);
        transaction.setCustomAnimations(R.animator.fragment_animation_fade_in,
                R.animator.fragment_animation_fade_out);
        transaction.commit();
    }

    @Override
    public void switchServiceType(int containerViewId, Fragment selectedFragment) {
        FragmentTransaction transaction =
                getSupportFragmentManager()
                        .beginTransaction();
//        getSupportFragmentManager().popBackStackImmediate();
        transaction.replace(containerViewId,
                selectedFragment);
        transaction.setCustomAnimations(R.animator.fragment_animation_fade_in,
                R.animator.fragment_animation_fade_out);

        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public boolean onSupportNavigateUp() {
        int count = getSupportFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            onBackPressed();
            //additional code
        } else {
            getSupportFragmentManager().popBackStack();
        }
//        onBackPressed();
        return true;
    }

    @Override
    public void startServiceActivity(Intent intent) {
        startService(intent);
    }

    @Override
    public void onBackPressed() {
        int count = getSupportFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            super.onBackPressed();
            //additional code
        } else {
            getSupportFragmentManager().popBackStack();
        }

    }

    /*@Override
    public void onBackPressed() {
        //Here we are clearing back stack fragment entries
        int backStackEntry = getSupportFragmentManager().getBackStackEntryCount();
        if (backStackEntry > 0) {
            for (int i = 0; i < backStackEntry; i++) {
                getSupportFragmentManager().popBackStackImmediate();
            }
        }

        //Here we are removing all the fragment that are shown here
        if (getSupportFragmentManager().getFragments() != null && getSupportFragmentManager().getFragments().size() > 0) {
            for (int i = 0; i < getSupportFragmentManager().getFragments().size(); i++) {
                Fragment mFragment = getSupportFragmentManager().getFragments().get(getSupportFragmentManager().getFragments().size() - 1);
                if (mFragment != null) {
                    getSupportFragmentManager().beginTransaction().detach(mFragment).commit();
                }
            }
        }
    }*/

    @Override
    public void refreshPage(Fragment fragment) {
        FragmentTransaction transaction =
                getSupportFragmentManager()
                        .beginTransaction()
                        .detach(fragment)
                        .attach(fragment);
        transaction.setCustomAnimations(R.animator.fragment_animation_fade_in, R.animator.fragment_animation_fade_out);
        transaction.commit();
//        getActivity().getFragmentManager().beginTransaction().detach(this).attach(this).commit();
    }

   /* public void logout(){
        FragmentManager fm = getSupportFragmentManager();
        for(int i = 0; i < fm.getBackStackEntryCount(); ++i) {
            fm.popBackStack();
        }
    }*/
}





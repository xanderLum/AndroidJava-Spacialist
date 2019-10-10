package com.spacialist.ui.main;

import android.content.Intent;
import android.icu.text.DateFormat;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

/**
 * A host (typically an {@code Activity}} that can display fragments and knows how to respond to
 * navigation events.
 */
public interface NavigationHost {
    /**
     * Trigger a navigation to the specified fragment, optionally adding a transaction to the back
     * stack to make this navigation reversible.
     */
    void navigateTo(Fragment fragment, boolean addToBackstack);

    void switchNav(int containerViewId, Fragment selectedFragment);

    void switchServiceType(int containerViewId, Fragment selectedFragment);

    void startServiceActivity(Intent i);

    void refreshPage(Fragment fragment);
}

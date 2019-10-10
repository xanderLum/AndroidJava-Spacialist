package com.spacialist.ui.main;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.spacialist.R;
import com.spacialist.data.Constants;
import com.spacialist.data.dto.SpaServiceEntry;
import com.spacialist.data.dto.StaffDTO;
import com.spacialist.service.ViewPagerAdapter;

import java.util.LinkedList;
import java.util.List;

public class ServicesTopNavigationFragment extends Fragment {

    Bundle extras = null;
    private BottomNavigationView bottomNav;
    private int itemId;
    private boolean hasPreSelectedNav = false;
    private List<String> serviceMenu;

    private List<SpaServiceEntry> serviceList;
    private List<StaffDTO> staffList;

    TabLayout tabLayout;
    ViewPager viewPager;
    ViewPagerAdapter pageAdapter;
    LinearLayout layoutTabNav;

    ServicesTopNavigationFragment() {
    }

    ServicesTopNavigationFragment(List<SpaServiceEntry> serviceList, List<StaffDTO> staffList) {
        this.serviceList = serviceList;
        this.staffList = staffList;
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.services_top_navigation, container, false);

        extras = this.getArguments();
        serviceMenu = extras.getStringArrayList(Constants.SERVICES_MENU);
        System.out.println("Service Menu = " + serviceMenu.toString());

        setUpToolbar(view);
        layoutTabNav = view.findViewById(R.id.layout_tab_nav);
        tabLayout = view.findViewById(R.id.top_navigation);
        viewPager = view.findViewById(R.id.viewPager);
        setupViewPager(viewPager);

        tabLayout.setupWithViewPager(viewPager, true);
        tabLayout.selectTab(tabLayout.getTabAt(0));
        tabLayout.setSelected(true);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition(), true);
                viewPager.setFocusable(true);
                tabLayout.setBackgroundColor(ContextCompat.getColor(getActivity(),
                        android.R.color.white));
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));


       /* OnBackPressedCallback callback = new OnBackPressedCallback() {
            @Override
            public boolean handleOnBackPressed() {
//                layoutTabNav.setVisibility(View.GONE);
                return false;
            }
        };
        requireActivity().addOnBackPressedCallback(this, callback);*/

        return view;
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());
        List<SpaServiceEntry> serviceList = null;
        Fragment fm = null;

        for (String s : serviceMenu) {
            serviceList = getServicesByServiceType(s);
            Log.i("ViewPagerAdapter", s + " type found");
            fm = new ServicesRecyclerViewFragment(serviceList, staffList);
            fm.setArguments(extras);
            adapter.addFragment(fm, s);
        }

        viewPager.setAdapter(adapter);
    }


    private List<SpaServiceEntry> getServicesByServiceType(String serviceType) {
        List<SpaServiceEntry> services = new LinkedList<>();
        for (SpaServiceEntry s : serviceList) {
            if (serviceType.equalsIgnoreCase(s.serviceType)) {
                services.add(s);
            }
        }

        return services;
    }

    private void setUpToolbar(View view) {
        Toolbar toolbar = view.findViewById(R.id.app_bar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity != null) {
            activity.setSupportActionBar(toolbar);
        }
    }
}

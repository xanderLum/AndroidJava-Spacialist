package com.spacialist.service;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.spacialist.data.dto.SpaServiceEntry;
import com.spacialist.data.dto.StaffDTO;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private int numOfTabs;
    private List<SpaServiceEntry> serviceList;
    private List<StaffDTO> staffList;
    private Bundle bundle;
    private Context context;
    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();

    public ViewPagerAdapter(FragmentManager manager) {
        super(manager);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
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

    /*@Override
    public int getCount() {
        return numOfTabs;
    }*/

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    public void addFragment(Fragment fragment, String title) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }
}

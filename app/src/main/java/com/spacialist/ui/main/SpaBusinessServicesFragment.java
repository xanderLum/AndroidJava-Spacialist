package com.spacialist.ui.main;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.spacialist.R;
import com.spacialist.data.Constants;
import com.spacialist.data.dto.SpaServiceEntry;
import com.spacialist.data.dto.StaffDTO;
import com.spacialist.ui.login.ViewModelFactory;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class SpaBusinessServicesFragment extends Fragment implements LifeCycleListener {

    private Bundle bundle;
    private SpaBusinessServicesCardRecyclerViewAdapter adapter;
    private ServiceViewModel serviceViewModel;
    private List<SpaServiceEntry> serviceEntries;
    private List<StaffDTO> staffList;
    private ProgressDialog processDialog;
    private String busId;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment with the ProductGrid theme
        final View view = inflater.inflate(R.layout.spa_business_services_grid_fragment, container, false);
        // Set up the tool bar
        bundle = this.getArguments();

        final SearchFilter searchFilter = new SearchFilter();
        searchFilter.setBusId(bundle.getString(Constants.SELECTED_BUSINESS_ID));

        searchFilter.setTimeString(bundle.getString(Constants.SELECTED_TIME_STRING));
        searchFilter.setDateString(bundle.getString(Constants.SELECTED_DATE_STRING));

        serviceViewModel = ViewModelProviders.of(this, new ViewModelFactory())
                .get(ServiceViewModel.class);

        System.out.println("Invoking search services...");
        serviceViewModel.search(searchFilter);
        System.out.println("Searching services...");

        serviceViewModel.getSearchResult().observe(this, new Observer<SearchResult>() {
            @Override
            public void onChanged(@Nullable SearchResult searchResult) {
                if (searchResult == null && searchResult.getServiceList() == null
                        && searchResult.getServiceIdsAvailableByBookingTime() == null) {
                    return;
                }
                if (searchResult.getError() != null) {
                    showSearchFailed(searchResult.getError());
                }
                if (searchResult.getServiceList() != null && searchResult.getServiceIdsAvailableByBookingTime() != null) {
                    System.out.println("SERVICE ENTRIES POPULATED!");
                    serviceEntries = searchResult.getServiceList();
                    staffList = searchResult.getServiceIdsAvailableByBookingTime();
                    onCreatedView(view);
                }
            }
        });

        serviceViewModel.getIsLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if (aBoolean) {
                    processDialog = new ProgressDialog(getActivity());
                    processDialog.setMessage("Please  Wait ...");
                    processDialog.setCancelable(false);
                    processDialog.show();
                } else {
                    if (processDialog != null && processDialog.isShowing()) {
                        processDialog.dismiss();
                    }
                }
            }
        });

        setUpToolbar(view);
        return view;
    }

    private void setUpToolbar(View view) {
        Toolbar toolbar = view.findViewById(R.id.app_bar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity != null) {
            activity.setSupportActionBar(toolbar);
        }
    }

    private void showSearchFailed(@StringRes Integer errorString) {
        Toast.makeText(getActivity(), errorString, Toast.LENGTH_SHORT).show();
    }

    private List<String> getServiceTypes() {
        List<String> serviceMenu = new LinkedList<>();
        for (SpaServiceEntry s : serviceEntries) {
            if (!isExistingInServiceMenu(serviceMenu, s.serviceType)) {
                serviceMenu.add(s.serviceType);
            }
        }

        return serviceMenu;
    }

    private boolean isExistingInServiceMenu(List<String> serviceMenu, String serviceType) {
        boolean exists = false;
        for (String s : serviceMenu) {
            if (serviceType.equalsIgnoreCase(s)) {
                exists = true;
                break;
            }
        }

        return exists;
    }

    @Override
    public void onCreatedView(View view) {
        List<String> serviceMenu = getServiceTypes();

        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.addAll(serviceMenu);
        bundle.putStringArrayList(Constants.SERVICES_MENU, arrayList);
        Fragment servicesTopNavFragment = new ServicesTopNavigationFragment(serviceEntries, staffList);
        servicesTopNavFragment.setArguments(bundle);
        ((NavigationHost) getActivity()).navigateTo(servicesTopNavFragment, false);

        //OLD
        // Set up the RecyclerView
        /*RecyclerView recyclerView = view.findViewById(R.id.services_recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager gridLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);

        recyclerView.setLayoutManager(gridLayoutManager);
        if (serviceEntries != null) {
            System.out.println("SERVICE ENTRIES SIZE: " + serviceEntries.size());
        } else {
            System.out.println("SERVICE ENTRIES NULL!!");
        }

        adapter = new SpaBusinessServicesCardRecyclerViewAdapter(serviceEntries, staffList);
        adapter.setBundle(bundle);

        recyclerView.setAdapter(adapter);
        int largePadding = getResources().getDimensionPixelSize(R.dimen.shr_staggered_product_grid_spacing_large);
        int smallPadding = getResources().getDimensionPixelSize(R.dimen.shr_staggered_product_grid_spacing_small);
        recyclerView.addItemDecoration(new SpaBusinessGridItemDecoration(largePadding, smallPadding));*/
    }
}

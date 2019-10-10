package com.spacialist.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.spacialist.R;
import com.spacialist.data.dto.SpaServiceEntry;
import com.spacialist.data.dto.StaffDTO;

import java.util.List;

public class ServicesRecyclerViewFragment extends Fragment implements LifeCycleListener {

    private Bundle bundle;
    private SpaBusinessServicesCardRecyclerViewAdapter adapter;
    private List<SpaServiceEntry> serviceEntries;
    private List<StaffDTO> staffList;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment with the ProductGrid theme
        final View view = inflater.inflate(R.layout.services_recylcer_view, container, false);
        // Set up the tool bar
        bundle = this.getArguments();
        onCreatedView(view);

        return view;
    }

    ServicesRecyclerViewFragment() {
    }

    public ServicesRecyclerViewFragment(List<SpaServiceEntry> serviceList, List<StaffDTO> staffList) {
        this.serviceEntries = serviceList;
        this.staffList = staffList;
    }

    @Override
    public void onCreatedView(View view) {
        // Set up the RecyclerView
        RecyclerView recyclerView = view.findViewById(R.id.services_recycler_view);
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
        recyclerView.addItemDecoration(new SpaBusinessGridItemDecoration(largePadding, smallPadding));
    }
}

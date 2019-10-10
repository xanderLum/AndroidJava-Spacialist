package com.spacialist.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.model.LatLng;
import com.spacialist.R;
import com.spacialist.data.Constants;
import com.spacialist.data.dto.SpaBusinessEntry;

import java.util.ArrayList;

public class SpaBusinessGridFragment extends Fragment {

    private Bundle extras;
    private String selectedDateTime;
    private double wayLatitude = 0.0, wayLongitude = 0.0;
    private LatLng userCurrentLocation;

    private SpaBusinessCardRecyclerViewAdapter adapter;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment with the ProductGrid theme
        final View view = inflater.inflate(R.layout.spa_business_grid_fragment, container, false);
        extras = this.getArguments();

        selectedDateTime = extras.getString(Constants.SELECTED_DATE_TIME);
        wayLatitude = extras.getDouble(Constants.SELECTED_LOCATION_LATITUDE);
        wayLongitude = extras.getDouble(Constants.SELECTED_LOCATION_LONGITUDE);

        System.out.println("LATITUDE: " + wayLatitude);
        System.out.println("LONGITUDE: " + wayLongitude);

        // Set up the tool bar
        setUpToolbar(view);

        final RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager gridLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);

        ArrayList<SpaBusinessEntry> spaList = extras.getParcelableArrayList(Constants.SEARCH_BUSINESS_LIST_RESULTS);
        adapter = new SpaBusinessCardRecyclerViewAdapter(spaList);
        adapter.setBundle(extras);

        userCurrentLocation = new LatLng(wayLatitude, wayLongitude);

        recyclerView.setAdapter(adapter);
        int largePadding = getResources().getDimensionPixelSize(R.dimen.shr_staggered_product_grid_spacing_large);
        int smallPadding = getResources().getDimensionPixelSize(R.dimen.shr_staggered_product_grid_spacing_small);
        recyclerView.addItemDecoration(new SpaBusinessGridItemDecoration(largePadding, smallPadding));

        return view;
    }

    private void setUpToolbar(View view) {
        Toolbar toolbar = view.findViewById(R.id.app_bar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity != null) {
            activity.setSupportActionBar(toolbar);
        }
    }



}

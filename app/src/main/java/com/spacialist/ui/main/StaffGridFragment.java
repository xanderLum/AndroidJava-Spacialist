package com.spacialist.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.NetworkImageView;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.card.MaterialCardView;
import com.spacialist.R;
import com.spacialist.data.Constants;
import com.spacialist.data.dto.SpaBusinessEntry;
import com.spacialist.data.dto.StaffDTO;
import com.spacialist.data.dto.StaffParcelable;
import com.spacialist.service.Utility;

import java.util.ArrayList;
import java.util.LinkedList;

public class StaffGridFragment extends Fragment {

    private Bundle extras;

    private StaffCardRecyclerViewAdapter adapter;
    public MaterialCardView serviceCard;
    public NetworkImageView serviceImage;
    public TextView serviceName;
    public TextView serviceDescription;
    public TextView price;
    public TextView serviceDuration;
    public TextView serviceId;
    public TextView intHrDuration;
    public TextView intMinDuration;
    public TextView autoAssignedStaffId;
    public TextView autoAssignedStaffName;
    public TextView autoAssignedStaffServiceId;
    public TextView booking_fee;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment with the ProductGrid theme
        final View view = inflater.inflate(R.layout.select_staff_grid_fragment, container, false);
        extras = this.getArguments();

        // Set up the tool bar
        setUpToolbar(view);
        initServiceViews(view);
        setupServiceCard(view);

        final RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager gridLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);

        ArrayList<StaffParcelable> staffParcel = extras.getParcelableArrayList(Constants.STAFF_PARCEL);
        adapter = new StaffCardRecyclerViewAdapter(staffParcel);
        adapter.setBundle(extras);

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

    private void setupServiceCard(View view) {
        serviceId.setText(extras.getString(Constants.SELECTED_SERVICE_ID));
        serviceName.setText(extras.getString(Constants.SELECTED_SERVICE_NAME));
        serviceDescription.setText(extras.getString(Constants.SELECTED_SERVICE_DESCRIPTION));
        price.setText(extras.getString(Constants.SELECTED_SERVICE_PRICE));
        serviceDuration.setText(extras.getString(Constants.SELECTED_SERVICE_DURATION));
    }

    private void initServiceViews(View view) {
        serviceId = view.findViewById(R.id.service_id);
        serviceCard = view.findViewById(R.id.serviceCard);
        serviceName = view.findViewById(R.id.service_name);
//        serviceImage = itemView.findViewById(R.id.service_image);
        serviceDescription = view.findViewById(R.id.service_description);
        price = view.findViewById(R.id.service_price);
        booking_fee = view.findViewById(R.id.booking_fee);
        serviceDuration = view.findViewById(R.id.service_duration);
        intHrDuration = view.findViewById(R.id.intHrDuration);
        intMinDuration = view.findViewById(R.id.intMinDuration);
        autoAssignedStaffId = view.findViewById(R.id.autoAssignedStaffId);
        autoAssignedStaffName = view.findViewById(R.id.autoAssignedStaffName);
        autoAssignedStaffServiceId = view.findViewById(R.id.autoAssignedStaffServiceId);
    }

}

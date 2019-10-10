package com.spacialist.ui.main;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.NetworkImageView;
import com.google.android.material.card.MaterialCardView;
import com.spacialist.R;
import com.spacialist.data.dto.StaffParcelable;

import java.util.LinkedList;
import java.util.List;

public class SpaBusinessServicesCardViewHolder extends RecyclerView.ViewHolder {

    public MaterialCardView serviceCard;
    public NetworkImageView serviceImage;
    public TextView serviceName;
    public TextView serviceDescription;
    public TextView serviceType;
    public TextView price;
    public TextView serviceDuration;
    public TextView serviceId;
    public TextView intHrDuration;
    public TextView intMinDuration;
    public TextView autoAssignedStaffId;
    public TextView autoAssignedStaffName;
    public TextView autoAssignedStaffServiceId;
    public TextView booking_fee;

    public List<StaffParcelable> staffParcelList;
    public TextView totalFees;

    public SpaBusinessServicesCardViewHolder(@NonNull View itemView) {
        super(itemView);
        serviceId = itemView.findViewById(R.id.service_id);
        serviceCard = itemView.findViewById(R.id.serviceCard);
        serviceName = itemView.findViewById(R.id.service_name);
//        serviceImage = itemView.findViewById(R.id.service_image);
        serviceType = itemView.findViewById(R.id.service_type);
        serviceDescription = itemView.findViewById(R.id.service_description);
        price = itemView.findViewById(R.id.service_price);
        booking_fee = itemView.findViewById(R.id.booking_fee);
        serviceDuration = itemView.findViewById(R.id.service_duration);
        intHrDuration = itemView.findViewById(R.id.intHrDuration);
        intMinDuration = itemView.findViewById(R.id.intMinDuration);
        autoAssignedStaffId = itemView.findViewById(R.id.autoAssignedStaffId);
        autoAssignedStaffName = itemView.findViewById(R.id.autoAssignedStaffName);
        autoAssignedStaffServiceId = itemView.findViewById(R.id.autoAssignedStaffServiceId);
//        totalFees = itemView.findViewById(R.id.total_fees);
//        staffParcelList = new LinkedList<>();
    }

    public List<StaffParcelable> getStaffParcelList() {
        return staffParcelList;
    }

    public void setStaffParcelList(List<StaffParcelable> staffParcelList) {
        this.staffParcelList = staffParcelList;
    }
}

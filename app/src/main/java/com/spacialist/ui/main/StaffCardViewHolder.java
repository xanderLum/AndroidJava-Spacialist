package com.spacialist.ui.main;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.NetworkImageView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.spacialist.R;
import com.spacialist.data.dto.StaffParcelable;

import java.util.LinkedList;
import java.util.List;

public class StaffCardViewHolder extends RecyclerView.ViewHolder {

    public MaterialCardView staffCard;
    public NetworkImageView staffLogo;
//    public ImageView staffLogo;
    public TextView staffServiceId;
    public TextView staffId;
    public TextView firstName;
    public TextView serviceId;
    public MaterialButton selectBtn;

    public StaffCardViewHolder(@NonNull View itemView) {
        super(itemView);
        serviceId = itemView.findViewById(R.id.service_id);
        staffLogo = itemView.findViewById(R.id.staff_image);
        staffCard = itemView.findViewById(R.id.staff_card);
//        serviceImage = itemView.findViewById(R.id.service_image);
        staffServiceId = itemView.findViewById(R.id.staff_service_id);
        staffId = itemView.findViewById(R.id.staff_id);
        firstName = itemView.findViewById(R.id.staff_name);
//        selectBtn = itemView.findViewById(R.id.select_staff_button);
    }

}
